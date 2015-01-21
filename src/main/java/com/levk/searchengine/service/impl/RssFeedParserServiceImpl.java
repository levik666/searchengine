package com.levk.searchengine.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import com.levk.searchengine.model.Item;
import com.levk.searchengine.service.RssFeedParserService;

import org.springframework.stereotype.Service;

@Service
public class RssFeedParserServiceImpl implements RssFeedParserService{

    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String ITEM = "item";

    public static final String DEFAULT_URL = "https://natasha-polukova.rhcloud.com/rss/";

    {
        readFeedFrom(DEFAULT_URL);
    }


    @Override
    public List<Item> readFeedFrom(final String feedUrl) {
        final URL url = getUrl(feedUrl);

        final List<Item> items = new CopyOnWriteArrayList<>();
        String title = "";
        String link = "";
        try {
            final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream stream = null;
            try {
                stream = read(url);
                final XMLEventReader eventReader = inputFactory.createXMLEventReader(stream);
                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();
                    if (event.isStartElement()) {
                        String localPart = event.asStartElement().getName().getLocalPart();
                        switch (localPart) {
                            case ITEM:
                                event = eventReader.nextEvent();
                                break;
                            case TITLE:
                                title = getCharacterData(event, eventReader);
                                break;
                            case LINK:
                                link = getCharacterData(event, eventReader);
                                break;
                        }
                    } else if (event.isEndElement()) {
                        final String localPart = event.asEndElement().getName().getLocalPart();
                        if (ITEM.equals(localPart)) {
                            Item item = new Item();
                            item.setLink(link);
                            item.setTitle(title);
                            items.add(item);
                            event = eventReader.nextEvent();
                        }
                    }

                }
            }finally {
                closeResource(stream);
            }

        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return items;

    }

    private URL getUrl(final String feedUrl){
        try {
            return new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read(final URL url) {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeResource(final InputStream stream){
        try {
            if (stream != null){
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
