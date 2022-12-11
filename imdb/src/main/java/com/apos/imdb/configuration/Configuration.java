package com.apos.imdb.configuration;

import com.apos.imdb.service.ImdbParsingService;
import com.apos.imdb.service.ImdbService;
import org.springframework.context.annotation.Bean;

public class Configuration {

  @Bean
  ImdbService imdbService() {
    return new ImdbParsingService();
  }

}
