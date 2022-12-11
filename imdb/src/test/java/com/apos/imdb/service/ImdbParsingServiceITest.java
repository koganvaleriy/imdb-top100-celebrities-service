package com.apos.imdb.service;

import com.apos.imdb.ImdbTop100CelebritiesServiceApplication;
import com.apos.imdb.model.Celebrity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImdbTop100CelebritiesServiceApplication.class)
public class ImdbParsingServiceITest {

  @Autowired
  private ImdbParsingService service;

  @Test
  public void getTop100CelebritiesTest() {
    List<Celebrity> celebrities = service.getTop100Celebrities();
    Assert.assertNotNull(celebrities);
    Assert.assertEquals(100, celebrities.size());
  }
}
