package com.example.tejas.tinohacks;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TinoHacks on 4/15/17.
 */

public class UpdateArticlesTask extends AsyncTask<String, Void, Void> {

    public static ArrayList<String> titles = new ArrayList<>();
    public static ArrayList<String> articles = new ArrayList<>();

    public static boolean finishedUpdating = false;

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL website = new URL(params[0]);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(website.openStream()));
            String inputLine;

            int counter = 0;

            while ((inputLine = br.readLine()) != null) {

                Document doc = Jsoup.parse(inputLine);
                String storeString = doc.getElementsByTag("a").attr("href");


                if (storeString.length() > 8 && storeString.substring(0,6).equals("/wiki/")) {
                    String goodUrl = "https://en.wikinews.org" + storeString;

                    if (counter > 1 && counter < 12) {
                        String currArticle = accessStrings(goodUrl);
                        articles.add(currArticle);
                        System.out.println(currArticle);
                        String currTitle = accessTitles(goodUrl);
                        titles.add(currTitle);
                        System.out.println(currTitle);
                    }

                    counter++;

                }

                if (counter == 12) {
                    break;
                }


            }
            br.close();

            System.out.println(titles);
            System.out.println(articles);

            finishedUpdating = true;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String accessStrings(String url) throws IOException { // one long string of all info in paragraph
        String allInfo = "";
        URL website = new URL(url);
        BufferedReader html = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;

        while ((inputLine = html.readLine()) != null) {
            Document doc = Jsoup.parse(inputLine);
            String curLine = doc.getElementsByTag("p").text();
            if (curLine != null) {
                allInfo += curLine;
            }
        }

        html.close();
        return allInfo;
    }

    private static String accessTitles(String url) throws IOException {
        String allInfo = "";
        URL website = new URL(url);
        BufferedReader html = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;

        while ((inputLine = html.readLine()) != null) {
            Document doc = Jsoup.parse(inputLine);
            String curLine = doc.getElementsByTag("h1").text();
            if (curLine != null) {
                allInfo += curLine;
            }
        }

        return allInfo;
    }
}
