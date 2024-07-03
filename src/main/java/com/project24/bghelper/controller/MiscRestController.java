package com.project24.bghelper.controller;

import com.project24.bghelper.model.Alignment;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MiscRestController {

  @GetMapping("/alignments")
  public List<Alignment> getAllAlignments() {
    return Arrays.stream(Alignment.values()).toList();
  }
}
