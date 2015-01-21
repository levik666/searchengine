package com.levk.searchengine.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.levk.searchengine.model.Item;
import com.levk.searchengine.service.RssFeedParserService;
import com.levk.searchengine.service.SearchEngineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchEngineServiceImpl implements SearchEngineService {
    private static final String EMPTY = " ";

    private List<Item> items;

    @Autowired
    private RssFeedParserService rssFeedParserService;

    @Override
    public List<Item> search(final String q) {
        if (isSearchFirstTime()){
            ping();
        }

        if (q.isEmpty() || EMPTY.equals(q)) {
            return new ArrayList<>();
        }

        final List<Item> result = new CopyOnWriteArrayList<>();
        for (final Item item : items) {
            if (item.getTitle().contains(q)) {
                result.add(item);
            }
        }
        return result;
    }

    private boolean isSearchFirstTime(){
        return items == null || items.isEmpty();
    }

    @Override
    public void ping() {
        items = rssFeedParserService.readFeedFrom(RssFeedParserServiceImpl.DEFAULT_URL);
    }
}
