package com.apos.imdb.controller;

import com.apos.imdb.dto.CelebrityDto;
import com.apos.imdb.model.Celebrity;
import com.apos.imdb.service.CelebrityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ImdbCelebritiesController {

  public static final String RESET_SUCCESS_MESSAGE = "Reset operation was successful";

  private CelebrityService celebrityService;

  @PostMapping(value = "/celebrities/reset")
  public ResponseEntity reset() {
    celebrityService.reset();
    return ResponseEntity.ok().body(RESET_SUCCESS_MESSAGE);
  }

  @GetMapping(value = "/celebrities/{job}")
  public ResponseEntity getAllCelebsByJob(@PathVariable String job) {
    final List<CelebrityDto> allCelebsByJob = celebrityService.getAllCelebsByJob(job);
    return ResponseEntity.ok(allCelebsByJob);
  }
}
