package com.levk.searchengine.service;

import java.util.List;

import com.levk.searchengine.model.Item;

public interface RssFeedParserService {

    List<Item> readFeedFrom(final String feedUrl);
}
