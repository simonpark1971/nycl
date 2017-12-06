package com.redspark.nycl.service;

import com.redspark.nycl.domain.AgeGroup;
import com.redspark.nycl.domain.League;
import com.redspark.nycl.domain.SeasonConfiguration;

import java.util.List;

public interface SeasonConfigurationService {

  League getLeague(String season, AgeGroup ageGroup);

  List<League> getLeagues(String season);

  SeasonConfiguration createSeason(String seasonTag);

  void removeAllCurrentSeasons();
}
