package com.apos.imdb.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.apos.imdb.exception.ImdbParsingException;
import com.apos.imdb.exception.RestExceptionHandler;
import com.apos.imdb.model.Celebrity;
import com.apos.imdb.service.CelebrityService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class ImdbCelebritiesControllerTest {

  private MockMvc mvc;

  @Mock
  private CelebrityService celebrityService;

  @InjectMocks
  private ImdbCelebritiesController controller;

  @Before
  public void setUp() {
    initMocks(this);
    mvc = MockMvcBuilders
        .standaloneSetup(controller)
        .setControllerAdvice(new RestExceptionHandler())
        .build();
  }

  @Test
  public void resetShouldBeSuccessTest() {
    final ResponseEntity response = this.controller.reset();
    Assert.assertEquals(ImdbCelebritiesController.RESET_SUCCESS_MESSAGE, response
        .getBody());
    Assert.assertEquals(HttpStatus.OK, response
        .getStatusCode());
  }

  @Test
  public void resetShouldBeInternalServerErrorIfException() throws Exception {
    doThrow(new ImdbParsingException()).when(celebrityService).reset();

    final MockHttpServletResponse response = mvc
        .perform(post("/celebrities/reset").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andReturn()
        .getResponse();

    Assert.assertEquals(RestExceptionHandler.IMDB_PARSING_ERROR, response.getContentAsString());
  }

  @Test
  public void getAllCelebsByJobSuccessTest() {
    final List<Celebrity> toBeReturned = List.of(
        Celebrity.builder().name("A").build(),
        Celebrity.builder().name("B").build());
    doReturn(toBeReturned).when(celebrityService)
        .getAllCelebsByJob(any());

    final ResponseEntity response = this.controller.getAllCelebsByJob(any(String.class));

    Assert.assertEquals(HttpStatus.OK, response
        .getStatusCode());
    Assert.assertEquals(toBeReturned, response
        .getBody());
  }
}
