package com.google.cloud.examples.translate.snippets;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class Translate  {

    public static class Oxford {
        private static final String API_URL = "https://od-api.oxforddictionaries.com:443/api/v2/entries";
        private static final String APP_ID = "f3c96b74";
        private static final String APP_KEY = "dc37055259616eca00f0470cf3026389";
        private static final String language = "en-gb";
        private static final String fields = "examples,definitions,pronunciations";
        private static final String strictMatch = "false";

        private static JSONObject getOxford(String word) {
            JSONObject result = null;
            String restUrl = API_URL +"/" + language + "/" + word.toLowerCase() + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
            try {
            URL url = new URL(restUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", APP_ID);
            urlConnection.setRequestProperty("app_key", APP_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            result = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
            e.printStackTrace();
            }
            return result;
        }

        public static OxfordData getOxfordData(String word) {
            OxfordData oxfordData = new OxfordData();
            JSONObject jsonObject = getOxford(word);
            JSONArray results = new JSONArray();
            results = jsonObject.getJSONArray("results");
            ArrayList<JSONArray> lexicalEntries = new ArrayList<JSONArray>();
            for (int i = 0; i < results.length(); i++) {
                if(!results.getJSONObject(i).isNull("lexicalEntries")) {
                    lexicalEntries.add(results.getJSONObject(i).getJSONArray("lexicalEntries"));
                }
            }
            ArrayList<JSONArray> entries = new ArrayList<JSONArray>();
            for(int i = 0; i <lexicalEntries.size(); i++) {
                for(int j = 0; j < lexicalEntries.get(i).length(); j++) {
                    if(!lexicalEntries.get(i).getJSONObject(j).isNull("entries")) {
                        entries.add(lexicalEntries.get(i).getJSONObject(j).getJSONArray("entries"));
                    }
                }
            }
            ArrayList<JSONArray> pronunciations = new ArrayList<JSONArray>();
            ArrayList<JSONArray> senses = new ArrayList<JSONArray>();
            for(int i = 0; i <entries.size(); i++) {
                for(int j = 0; j < entries.get(i).length(); j++) {
                    if(!entries.get(i).getJSONObject(j).isNull("pronunciations")) {
                        pronunciations.add(entries.get(i).getJSONObject(j).getJSONArray("pronunciations"));
                    }
                   if(!entries.get(i).getJSONObject(j).isNull("senses")) {
                       senses.add(entries.get(i).getJSONObject(j).getJSONArray("senses"));
                   }
                }
            }

            for (int i = 0; i < pronunciations.size(); i++) {
                for (int j = 0; j < pronunciations.get(i).length(); j++) {
                    if (!pronunciations.get(i).getJSONObject(j).isNull("phoneticSpelling")) {
                        boolean checkIPAExists = false;
                        String ipa = pronunciations.get(i).getJSONObject(j).getString("phoneticSpelling").trim();
                        for (int k = 0; k < oxfordData.ipa.size(); k++) {
                            if (ipa.equals(oxfordData.ipa.get(k))) {
                                checkIPAExists = true;
                                break;
                            }
                        }
                        if (!checkIPAExists) {
                            oxfordData.ipa.add(ipa);
                        }

                    }
                    if (!pronunciations.get(i).getJSONObject(j).isNull("audioFile")) {
                        boolean checkAudioExists = false;
                        String audioFile = pronunciations.get(i).getJSONObject(j).getString("audioFile").trim();
                        for (int k = 0; k < oxfordData.audio.size(); k++) {
                            if (audioFile.equals(oxfordData.audio.get(k))) {
                                checkAudioExists = true;
                                break;
                            }
                        }
                        if (!checkAudioExists) {
                            oxfordData.audio.add(audioFile);
                        }
                    }

                }
            }

            for (int i = 0; i < senses.size(); i++) {
                for (int j = 0; j < senses.get(i).length(); j++) {
                    DefineExample temp = new DefineExample();
                    if(!senses.get(i).getJSONObject(j).isNull("definitions")) {
                       temp.define = senses.get(i).getJSONObject(j).getJSONArray("definitions").getString(0).trim();
                    }
                    JSONArray jsonArrayExample = new JSONArray();
                    if(!senses.get(i).getJSONObject(j).isNull("examples")) {
                        jsonArrayExample = senses.get(i).getJSONObject(j).getJSONArray("examples");
                    }
                    for(int k = 0; k < jsonArrayExample.length(); k++) {
                        temp.example.add(jsonArrayExample.getJSONObject(k).getString("text").trim());
                    }
                    oxfordData.defineExamples.add(temp);
                }
            }
            return oxfordData;
        }
    }

    public static class GoogleTranslate {
        private String languageStar = "en";
        private  String getLanguageEnd = "vi";
        private String APIGoogleTranslate = "https://script.google.com/macros/s/AKfycbw2qKkvobro8WLNZUKi2kGwGwEO4W8cBavcKqcuCIGhGBBtVts/exec";

        public String getGoogleData(String text) throws IOException {
            String urlStr = this.APIGoogleTranslate
                    + "?q=" + URLEncoder.encode(text, "UTF-8")
                    + "&target=" + this.getLanguageEnd
                    +  "&source=" + this.languageStar;
            URL url = new URL(urlStr);
            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
    }


}

