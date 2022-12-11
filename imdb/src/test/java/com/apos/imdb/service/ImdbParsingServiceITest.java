package com.apos.imdb.service;

import static org.mockito.MockitoAnnotations.initMocks;

import com.apos.imdb.ImdbTop100CelebritiesServiceApplication;
import com.apos.imdb.InitialLoader;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImdbTop100CelebritiesServiceApplication.class)
public class ImdbParsingServiceITest {

  //mocking InitialLoader in order to skip it during the test
  @MockBean
  private InitialLoader initialLoader;

  @Autowired
  private ImdbParsingService service;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void getTop100CelebritiesTest() {
    List<Celebrity> celebrities = service.getTop100Celebrities();
    Assert.assertNotNull(celebrities);
    Assert.assertEquals(100, celebrities.size());
  }
}
