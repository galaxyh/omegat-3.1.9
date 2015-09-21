package org.omegat.core.machinetranslators;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omegat.util.Language;
import org.omegat.util.OStrings;
import org.omegat.util.Preferences;
import org.omegat.util.WikiGet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yu-chun Huang on 9/19/15.
 */
public class TrueTranslate extends BaseTranslate {
    protected static final String TT_URL = "http://hi1.daiwan.cc:4001/tran";

    @Override
    protected String getPreferenceName() {
        return Preferences.ALLOW_TRUE_TRANSLATE;
    }

    public String getName() {
        return OStrings.getString("MT_ENGINE_TRUE");
    }

    @Override
    protected String translate(Language sLang, Language tLang, String text) throws Exception {
        //String id = System.getProperty("truetranslate.api.client_id");
        //String password = System.getProperty("truetranslate.api.client_secret");

        boolean totw;
        String ver;

        if (sLang.getLanguageCode().compareToIgnoreCase("en") == 0
                && tLang.getLanguageCode().compareToIgnoreCase("zh") == 0) {
            ver = System.getProperty("truetranslate.api.ench");
        } else if (sLang.getLanguageCode().compareToIgnoreCase("zh") == 0
                && tLang.getLanguageCode().compareToIgnoreCase("en") == 0) {
            ver = System.getProperty("truetranslate.api.chen");
        } else {
            throw new IllegalArgumentException("Language not supported.");
        }

        if (ver == null) {
            return null;
        }

        totw = isCht(sLang) || isCht(tLang);

        Map<String, String> params = new HashMap<String, String>();
        params.put("ver", ver);
        params.put("totw", totw ? "1" : "0");
        params.put("txt", text);

        // Get the results from TrueTranslate
        String response = "";
        try {
            response = WikiGet.post(TT_URL, params);
        } catch (IOException e) {
            throw e;
        }

        JSONObject jResponseObj = new JSONObject(response);

        String rootStatus = jResponseObj.getString("stat");
        if (rootStatus == null || !"success".equals(rootStatus.trim())) { // Fail
            return null;
        }

        JSONObject jRootMsgObj = jResponseObj.getJSONObject("msg");
        int failCount = jRootMsgObj.getInt("fail");
        // int okCount = jRootMsgObj.getInt("ok");
        int sentenceCount = jRootMsgObj.getInt("sen");

        if (failCount == sentenceCount) { // All fail
            return null;
        }

        String tr = "";

        JSONArray jSentences = jResponseObj.getJSONArray("sen");
        for (int i = 0; i < jSentences.length(); i++) {
            JSONObject jSentenceObj = jSentences.getJSONObject(i);
            // String msg = jSentenceObj.getString("msg");
            // String ori = jSentenceObj.getString("ori");
            String res = jSentenceObj.getString("res");
            String stat = jSentenceObj.getString("stat");

            if ("success".equals(stat)) {
                tr += res + "";
            }
        }

        return tr.trim();
    }

    private boolean isCht(Language lang) {
        if ((lang.getLanguageCode().compareToIgnoreCase("zh") == 0)
                && (lang.getLanguage().compareToIgnoreCase("zh-cn") != 0)) {
            return true;
        }
        return false;
    }
}
