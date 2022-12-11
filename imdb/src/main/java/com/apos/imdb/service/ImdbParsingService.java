package com.apos.imdb.service;

import com.apos.imdb.exception.ImdbParsingException;
import com.apos.imdb.model.Celebrity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImdbParsingService implements ImdbService {

  private static Map<String, Celebrity> celebrityMapCache = new HashMap<>();

  public List<Celebrity> getTop100Celebrities() {
    List<Celebrity> celebrities = new ArrayList<>();
    Document top100ListDoc;
    try {
      top100ListDoc = Jsoup
          .connect("https://www.imdb.com/list/ls052283250/").get();

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
        .connect("https://www.imdb.com/" + celebritySource)
        .header("Cache-Control","cache")
        .get();

    //TODO get name only
    celebrity.setName(
        celebrityDoc.getElementsByAttributeValue("data-testid", "hero__pageTitle").first().text());
    //TODO don't use substring
    celebrity.setDob(
        celebrityDoc.getElementsByAttributeValue("data-testid", "birth-and-death-birthdate")
            .first().text().substring(4));
    celebrity.setJob(
        celebrityDoc.getElementsByClass("ipc-inline-list ipc-inline-list--show-dividers sc-856aec89-4 ihBMCA baseAlt")
            .first().getElementsByClass("ipc-inline-list__item")
            .stream().map(Element::text).collect(Collectors.toSet()));
    celebrityMapCache.put(celebritySource, celebrity);
    return celebrity;
  }
}
