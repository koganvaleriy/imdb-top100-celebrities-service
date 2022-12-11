package com.apos.imdb;

import com.apos.imdb.service.CelebrityService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Loads top 100 celebrities list on application start up.
 * If data is present in the DB then only load operation is done without DB insertion
 * Load is needed to fill in cache
 */
@Order(0)
@Component
@AllArgsConstructor
public class InitialLoader implements ApplicationListener<ApplicationReadyEvent> {

  private CelebrityService celebrityService;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    celebrityService.fillDataIfEmpty();
  }
}
