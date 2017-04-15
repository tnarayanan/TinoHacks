package com.example.tejas.tinohacks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * Created by TinoHacks on 4/15/17.
 */

public class CompileInfo {
    public static void main(String[] args) {
        // order of reliability.

        // wall street journal
        // The Economist
        // BBC
        // Google News
        // ABC news
        // NPR
        // PBS
        // CNN

        String instanceNews = "<p>An <a href='https://www.theguardian.com/us-news/2017/apr/14/resistance-now-tax-march-trump-returns-democrats-seat-sanders-talk-show'><b>example</b></a> link.</p>";
        Document doc = Jsoup.parse(instanceNews);
        Element link = doc.select("a").first();

        String text = doc.body().text(); // "An example link"
        String linkHref = link.attr("href"); // "http://example.com/"
        String linkText = link.text(); // "example""
        String linkOuterH = link.outerHtml();
        // "<a href="http://example.com"><b>example</b></a>"
        String linkInnerH = link.html(); // "<b>example</b>"

        System.out.println(text);
        System.out.println(linkHref);
        System.out.println(linkText);
        System.out.println(linkOuterH);
        System.out.println(linkInnerH);
    }
}
