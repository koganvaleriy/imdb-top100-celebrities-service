package com.apos.imdb.service;

import com.apos.imdb.exception.ImdbParsingException;
import com.apos.imdb.model.Celebrity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImdbParsingService implements ImdbService {

  public static final String IMDB_PAGE_URL = "https://www.imdb.com/list/ls052283250/";
  public static final String IMDB_MAIN_PAGE_URL = "https://www.imdb.com/";

  //TODO implement Spring Caching instead
  private static ConcurrentMap<String, Celebrity> celebrityMapCache = new ConcurrentHashMap<>();

  public List<Celebrity> getTop100Celebrities() {
    List<Celebrity> celebrities = new ArrayList<>();
    Document top100ListDoc;
    try {
      top100ListDoc = Jsoup
          .connect(IMDB_PAGE_URL).get();

      Elements celebritiesParsedItems = top100ListDoc.getElementsByClass("lister-item-image");
      for (Element element: celebritiesParsedItems) {
        Celebrity celebrity = getCelebrity(element);
        log.info(celebrity.toString());
        celebrities.add(celebrity);
      }
    } catch (Exception e) {
      log.error("", e);
      throw new ImdbParsingException();
    }
    return celebrities;
  }

  //TODO find gender attribute in IMDB
  private Celebrity getCelebrity(Element element) throws IOException {
    Celebrity celebrity = new Celebrity();
    celebrity.setImageUri(element
        .select("img").attr("src"));
    final String celebritySource = element
        .select("a")
        .attr("href");
    if (celebrityMapCache.containsKey(celebritySource)) {
      return celebrityMapCache.get(celebritySource);
    }
    Document celebrityDoc = Jsoup
        .connect(IMDB_MAIN_PAGE_URL + celebritySource)
        .header("Cache-Control","cache")
        .get();

    final Element pageTitle = celebrityDoc
        .getElementsByAttributeValue("data-testid", "hero__pageTitle")
        .first();
    if (pageTitle != null) {
      final String nameParsedWithAdditionalData = pageTitle.text();
      //Substring is needed to exclude additional data that starts from from the '(' symbol for some persons, e.g. Chris Evans(V)
      final int indexOfFirstNotNeededSymbol = nameParsedWithAdditionalData.indexOf("(");
      if (indexOfFirstNotNeededSymbol != -1) {
        celebrity.setName(nameParsedWithAdditionalData.substring(0, indexOfFirstNotNeededSymbol));
      } else {
        celebrity.setName(nameParsedWithAdditionalData);
      }
    }

    final Element birthData = celebrityDoc
        .getElementsByAttributeValue("data-testid", "birth-and-death-birthdate")
        .first();
    if (birthData != null) {
      //Substring is needed to exclude word 'Born' from the parsed data
      celebrity.setDob(birthData.text().substring(4));
    }

    final Element jobsData = celebrityDoc
        .getElementsByClass(
            "ipc-inline-list ipc-inline-list--show-dividers sc-856aec89-4 ihBMCA baseAlt").first();

    if (jobsData != null) {
      celebrity.setJob(jobsData.getElementsByClass("ipc-inline-list__item")
          .stream().map(Element::text).collect(Collectors.toSet()));
    }
    celebrityMapCache.put(celebritySource, celebrity);
    return celebrity;
  }
}
