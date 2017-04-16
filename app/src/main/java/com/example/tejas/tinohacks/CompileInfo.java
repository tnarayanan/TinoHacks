package com.example.tejas.tinohacks;


import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.ArrayList;


/**
 * Created by TinoHacks on 4/15/17.
 */

public class CompileInfo {
    public static void main(String[] args) throws IOException {
        // order of reliability.


        // do wikinews https://en.wikinews.org/wiki/Donald_Trump_elected_US_president


        // wall street journal https://www.wsj.com/articles/in-mike-pences-pacific-swing-security-will-trump-trade-1492254005
        // The Economist
        // BBC
        // Google News
        // ABC news
        // NPR
        // PBS
        // CNN

        //System.out.println(accessStrings("https://en.wikinews.org/wiki/Donald_Trump_elected_US_president"));
        //System.out.println(accessImages("https://en.wikinews.org/wiki/Donald_Trump_elected_US_president"));
        getPoliticalURL();
        /*Document doc = Jsoup.parse(html.toString());
        Element link = doc.select("a").first();*/

        /*String text = doc.body().text(); // "An example link"
        String linkHref = link.attr("href"); // "http://example.com/"
        String linkText = link.text(); // "example""
        String linkOuterH = link.outerHtml();
        // "<a href="http://example.com"><b>example</b></a>"
        String linkInnerH = link.html(); // "<b>example</b>"

        System.out.println(text);
        System.out.println(linkHref);
        System.out.println(linkText);
        System.out.println(linkOuterH);
        System.out.println(linkInnerH);*/
    }

    private static ArrayList<URL> getPoliticalURL() throws IOException {
        ArrayList<URL> news = new ArrayList<>();

        URL website = new URL("https://en.wikinews.org/wiki/Category:Politics_and_conflicts");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;

        int counter = 0;

        while ((inputLine = br.readLine()) != null) {

            Document doc = Jsoup.parse(inputLine);
            String storeString = doc.getElementsByTag("a").attr("href");



            if (storeString.length() > 8 && storeString.substring(0,6).equals("/wiki/") && counter < 10) {
                System.out.println("en.wikinews.org" + storeString);
                counter++;
            }

            if (counter == 10) {
                break;
            }

        }
        br.close();

        return news;
    }

    private static ArrayList<String> chooseSentences(String url) throws IOException { // returns date and stats
        String para = accessStrings(url);
        ArrayList<String> stats = new ArrayList<>();
        boolean isSkipping = false;
        int begSentence = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isDigit(para.charAt(i)) && !isSkipping) {
                isSkipping = true;
            }

            if (i > 0 && (Character.isLetter(para.charAt(i)) || para.charAt(i) == ' ') && para.charAt(i - 1) == '.') {

                if (isSkipping){
                    stats.add(para.substring(begSentence, i+1));
                    isSkipping = false;
                }

                begSentence = i + 1;
            }
        }

        return stats;
    }

    private static void chooseImages(String url) throws IOException {
        ArrayList<String> allImg = accessImages(url);

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

    private static ArrayList<String> accessImages(String url) throws IOException { // array of images
        ArrayList<String> listUrls = new ArrayList<>();
        URL website = new URL(url);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;


        while ((inputLine = br.readLine()) != null) {

            Document doc = Jsoup.parse(inputLine);
            String storeString = doc.getElementsByTag("img").attr("src");

            try {
                if (storeString.substring(0, 3).equals("//u")) {
                    storeString = storeString.replace("//", "");
                    listUrls.add(storeString);
                    //System.out.println(storeString);
                }
            } catch (Exception e) {
            }

        }
        br.close();

        return listUrls;
    }
}