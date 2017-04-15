package com.example.tejas.tinohacks;


import java.net.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


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

        System.out.println(access("https://en.wikipedia.org/wiki/India%E2%80%93European_Union_relations"));

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

    private static String access(String url) throws IOException {
        String allInfo = "";
        URL website = new URL(url);
        BufferedReader html = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;

        while ((inputLine = html.readLine()) != null) {
            Document doc = Jsoup.parse(inputLine);
            String curLine = doc.getElementsByTag("p").text();
            if (curLine != null) {
                System.out.println(curLine);
                allInfo += " ";
                allInfo += curLine;
            }
        }

        html.close();
        return allInfo;
    }
}