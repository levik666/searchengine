package com.levk.searchengine.service;

import java.util.List;

import com.levk.searchengine.model.Item;

public interface SearchEngineService {

    public List<Item> search(final String q);

    public void ping();
}
