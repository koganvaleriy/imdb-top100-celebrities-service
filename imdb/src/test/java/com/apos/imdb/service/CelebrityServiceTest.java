package com.apos.imdb.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

import com.apos.imdb.dto.CelebrityDto;
import com.apos.imdb.mapper.CelebrityMapper;
import com.apos.imdb.model.Celebrity;
import com.apos.imdb.repository.CelebrityRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

public class CelebrityServiceTest {

  @Mock
  private CelebrityMapper celebrityMapper;

  @Mock
  private CelebrityRepository celebrityRepository;

  @Mock
  private ImdbService imdbService;

  @InjectMocks
  private CelebrityService celebrityService;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void resetTest() {

    final List<Celebrity> celebrities = List.of(
        Celebrity.builder().name("A").build(),
        Celebrity.builder().name("B").build());
    doReturn(celebrities).when(imdbService)
        .getTop100Celebrities();

    celebrityService.reset();

    verify(celebrityRepository, times(1)).deleteAll();
    verify(imdbService, times(1)).getTop100Celebrities();
    verify(celebrityRepository, times(1)).saveAll(celebrities);
  }

  @Test
  public void getAllCelebsByJobTest() {

    final List<Celebrity> celebrities = List.of(
        Celebrity.builder().name("A").build(),
        Celebrity.builder().name("B").build());

    final List<CelebrityDto> celebritiesDto = List.of(
        CelebrityDto.builder().name("A").build(),
        CelebrityDto.builder().name("B").build());

    doReturn(celebrities).when(celebrityRepository)
        .findAllByJob(any());
    doReturn(celebritiesDto).when(celebrityMapper)
        .destinationToSource(celebrities);

    final List<CelebrityDto> allCelebsByJob = celebrityService.getAllCelebsByJob(any(String.class));

    Assert.assertEquals(celebritiesDto, allCelebsByJob);
  }
}
