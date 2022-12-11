package com.apos.imdb.repository;

import com.apos.imdb.model.Celebrity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CelebrityRepository extends MongoRepository<Celebrity, String> {

  List<Celebrity> findAllByJob(String job);
}
