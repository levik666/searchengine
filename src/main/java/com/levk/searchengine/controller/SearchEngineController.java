package com.levk.searchengine.controller;

import java.util.List;

import com.levk.searchengine.model.Item;
import com.levk.searchengine.service.SearchEngineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchEngineController {

    @Autowired
    private SearchEngineService searchEngineService;


    @RequestMapping("/search")
    public List<Item> search(@RequestParam(value="q", defaultValue = " ") String q) {
        return searchEngineService.search(q);
    }

    @RequestMapping(value = "/ping", method = RequestMethod.POST)
    public void ping() {
        searchEngineService.ping();
    }
}
