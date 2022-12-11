package com.apos.imdb.mapper;

import static org.mockito.MockitoAnnotations.initMocks;

import com.apos.imdb.ImdbTop100CelebritiesServiceApplication;
import com.apos.imdb.InitialLoader;
import com.apos.imdb.dto.CelebrityDto;
import com.apos.imdb.model.Celebrity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImdbTop100CelebritiesServiceApplication.class)
public class CelebrityMapperTest {

  @Autowired
  private CelebrityMapper mapper;

  //mocking InitialLoader in order to skip it during the test
  @MockBean
  private InitialLoader initialLoader;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void mapCelebrityToCelebrityDtoTest() {
    Celebrity celebrity = Celebrity.builder()
        .name("Some name")
        .dob("July 25, 1965")
        .gender("male")
        .job(Set.of("Actor", "Producer"))
        .imageUri("some uri")
        .build();
    CelebrityDto celebrityDtoExpected = CelebrityDto.builder()
        .name("Some name")
        .dob("July 25, 1965")
        .gender("male")
        .job(Set.of("Actor", "Producer"))
        .imageUri("some uri")
        .build();
    CelebrityDto celebrityDtoActual = mapper.destinationToSource(celebrity);
    Assert.assertEquals(celebrityDtoExpected, celebrityDtoActual);
  }

  @Test
  public void mapCelebrityDtoToCelebrityTest() {
    Celebrity celebrityExpected = Celebrity.builder()
        .name("Some name")
        .dob("July 25, 1965")
        .gender("male")
        .job(Set.of("Actor", "Producer"))
        .imageUri("some uri")
        .build();
    CelebrityDto celebrityDto = CelebrityDto.builder()
        .name("Some name")
        .dob("July 25, 1965")
        .gender("male")
        .job(Set.of("Actor", "Producer"))
        .imageUri("some uri")
        .build();
    Celebrity celebrityActual = mapper.sourceToDestination(celebrityDto);
    Assert.assertEquals(celebrityExpected, celebrityActual);
  }

  @Test
  public void mapCelebrityDtoListToCelebrityListTest() {
    Celebrity celebrityExpected1 = Celebrity.builder()
        .name("Some name 1")
        .dob("July 25, 1965")
        .gender("male")
        .job(Set.of("Actor", "Producer"))
        .imageUri("some uri 1")
        .build();
    Celebrity celebrityExpected2 = Celebrity.builder()
        .name("Some name 2")
        .dob("July 25, 1966")
        .gender("female")
        .job(Set.of("Director", "Producer"))
        .imageUri("some uri 2")
        .build();
    CelebrityDto celebrityDto1 = CelebrityDto.builder()
        .name("Some name 1")
        .dob("July 25, 1965")
        .gender("male")
        .job(Set.of("Actor", "Producer"))
        .imageUri("some uri 1")
        .build();
    CelebrityDto celebrityDto2 = CelebrityDto.builder()
        .name("Some name 2")
        .dob("July 25, 1966")
        .gender("female")
        .job(Set.of("Director", "Producer"))
        .imageUri("some uri 2")
        .build();
    List<Celebrity> celebrityActual = mapper.sourceToDestination(List.of(celebrityDto1, celebrityDto2));
    Assert.assertEquals(List.of(celebrityExpected1, celebrityExpected2), celebrityActual);
  }

  @Test
  public void mapCelebrityListToCelebrityDtoListTest() {
    Celebrity celebrity1 = Celebrity.builder()
        .name("Some name 1")
        .dob("July 25, 1965")
        .gender("male")
        .job(Set.of("Actor", "Producer"))
        .imageUri("some uri 1")
        .build();
    Celebrity celebrity2 = Celebrity.builder()
        .name("Some name 2")
        .dob("July 25, 1966")
        .gender("female")
        .job(Set.of("Director", "Producer"))
        .imageUri("some uri 2")
        .build();
    CelebrityDto celebrityDtoExpected1 = CelebrityDto.builder()
        .name("Some name 1")
        .dob("July 25, 1965")
        .gender("male")
        .job(Set.of("Actor", "Producer"))
        .imageUri("some uri 1")
        .build();
    CelebrityDto celebrityDtoExpected2 = CelebrityDto.builder()
        .name("Some name 2")
        .dob("July 25, 1966")
        .gender("female")
        .job(Set.of("Director", "Producer"))
        .imageUri("some uri 2")
        .build();
    List<CelebrityDto> celebrityActual = mapper.destinationToSource(List.of(celebrity1, celebrity2));
    Assert.assertEquals(List.of(celebrityDtoExpected1, celebrityDtoExpected2), celebrityActual);
  }

}
