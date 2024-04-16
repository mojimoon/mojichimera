package mojichimera;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mojichimera.util.TextureLoader;
import mojichimera.augments.AugmentHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

@SpireInitializer
public class mojichimera implements 
    EditStringsSubscriber,
    PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(mojichimera.class.getName());
    private static String modID;

    private static final String MODNAME = "Mojimoon Chimera";
    private static final String AUTHOR = "Mojimoon";
    private static final String DESCRIPTION = "Chimera expansion by Mojimoon.";

    public static UIStrings uiStrings;
    public static String[] TEXT;
    public static String[] EXTRA_TEXT;

    // Mod Badge
    public static final String BADGE_IMAGE = "mojichimeraResources/images/Badge.png";

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
    }

    // Utility
    public static String getModID() {
        return modID;
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}