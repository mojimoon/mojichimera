package mojichimera;

import basemod.*;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mojichimera.util.TextureLoader;
import mojichimera.augments.AugmentHelper;

@SpireInitializer
public class mojichimera implements 
    EditStringsSubscriber,
    PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(mojichimera.class.getName());
    private static String modID;

    private static final String MODNAME = "Mojimoon Chimera";
    private static final String AUTHOR = "Mojimoon";
    private static final String DESCRIPTION = "Chimera expansion by Mojimoon.";

    // Assets
    public static final String BADGE_IMAGE = "mojichimeraResources/images/Badge.png";

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    // initialize
    public mojichimera() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        modID = "mojichimera";
    }

    public static void initialize() {
        logger.info("Initializing Mojimoon Chimera");
        mojichimera mojichimera_instance = new mojichimera();
    }

    // Post-Initialize
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        ModPanel settingsPanel = new ModPanel();
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        if (Loader.isModLoaded("CardAugments")) {
            AugmentHelper.register();
        }
    }

    // Localization
    private String loadLocalizationIfAvailable(String fileName) {
        if (!Gdx.files.internal(getModID() + "Resources/localization/" + Settings.language.toString().toLowerCase()+ "/" + fileName).exists()) {
            logger.info("Language: " + Settings.language.toString().toLowerCase() + ", not currently supported for " +fileName+".");
            return "eng" + "/" + fileName;
        } else {
            logger.info("Loaded Language: "+ Settings.language.toString().toLowerCase() + ", for "+fileName+".");
            return Settings.language.toString().toLowerCase() + "/" + fileName;
        }
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Editing strings");

        // UI Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
            getModID() + "Resources/localization/"+loadLocalizationIfAvailable("mojichimera-UI-Strings.json"));

        // Augment Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
            getModID() + "Resources/localization/"+loadLocalizationIfAvailable("mojichimera-Augment-Strings.json"));

        // Power Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
            getModID() + "Resources/localization/"+loadLocalizationIfAvailable("mojichimera-Power-Strings.json"));
    }

    // Utility
    public static String getModID() {
        return modID;
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
