package com.apos.imdb.service;

import com.apos.imdb.dto.CelebrityDto;
import com.apos.imdb.mapper.CelebrityMapper;
import com.apos.imdb.model.Celebrity;
import com.apos.imdb.repository.CelebrityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CelebrityService {

  private CelebrityRepository celebrityRepository;
  private ImdbService imdbService;
  CelebrityMapper celebrityMapper;

  public void reset() {
    celebrityRepository.deleteAll();
    List<Celebrity> celebrities = imdbService.getTop100Celebrities();
    celebrityRepository.saveAll(celebrities);
  }

  public List<CelebrityDto> getAllCelebsByJob(String job) {
    final List<Celebrity> celebritiesByJob = celebrityRepository.findAllByJob(job);
    return celebrityMapper.destinationToSource(celebritiesByJob);
  }

  public void fillDataIfEmpty() {
    long celebtiriesAmountInTheDb = celebrityRepository.count();
    List<Celebrity> celebrities = imdbService.getTop100Celebrities();

    if(celebtiriesAmountInTheDb == 0) {
      celebrityRepository.saveAll(celebrities);
    }
  }
}
