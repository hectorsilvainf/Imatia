package com.imatia.webapp.web.util;

import java.util.HashMap;
import java.util.Map;

public class SupportedLanguages {

    private static Map<String, String> options;

    private static String codes = "";

    private SupportedLanguages() {}

    public static void initialize() {

        String options_en = "en=English, es=Spanish";
        String options_es = "es=Español, en=Inglés";
       

        options = new HashMap<String, String>();
        options.put("en", options_en);
        options.put("es", options_es);
        

        codes = "en,es";

    }

    public static String getCodes() {
        return codes;
    }

    public static String getOptions(String languageCode) {

        String languages = options.get(languageCode);

        if (languages != null) {
            return languages;
        } else {
            return options.get("en");
        }

    }

}
