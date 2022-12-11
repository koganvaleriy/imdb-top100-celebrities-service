package com.apos.imdb.service;

import com.apos.imdb.model.Celebrity;

import java.util.List;

public interface ImdbService {

  List<Celebrity> getTop100Celebrities();

}
