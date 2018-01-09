package com.redspark.nycl;

import com.redspark.nycl.domain.AgeGroup;
import com.redspark.nycl.service.SeasonConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simonpark on 26/09/2016.
 */
@RestController
public class AdminController {

  @Autowired
  private SeasonConfigurationService seasonService;

  @GetMapping(value = "/getleague")
  @CrossOrigin(origins = "*")
  public ResponseEntity getLeagueTeams(@RequestParam(name = "league") String league) {
    return new ResponseEntity(seasonService.getLeague("2018", AgeGroup.valueOf(league)), HttpStatus.OK);
  }
}
