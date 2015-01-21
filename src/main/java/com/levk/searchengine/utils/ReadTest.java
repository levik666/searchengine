package com.levk.searchengine.utils;

import java.util.List;

import com.levk.searchengine.model.Item;
import com.levk.searchengine.service.RssFeedParserService;
import com.levk.searchengine.service.impl.RssFeedParserServiceImpl;

public class ReadTest {
    public static void main(String[] args) {
        RssFeedParserService parser = new RssFeedParserServiceImpl();
        final List<Item> items = parser.readFeedFrom("https://natasha-polukova.rhcloud.com/rss/");
        for (Item message : items) {
            System.out.println(message);

        }

    }
}

