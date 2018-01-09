package com.redspark.nycl.service.impl.postgresql;

import com.redspark.nycl.domain.*;
import com.redspark.nycl.persistence.PostgresqlSeasonConfigurationRepository;
import com.redspark.nycl.service.SeasonConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostgresqlSeasonConfigurationService implements SeasonConfigurationService {

  @Autowired
  private PostgresqlSeasonConfigurationRepository postgresqlSeasonConfigurationRepository;

  @Override
  public League getLeague(String season, AgeGroup ageGroup) {
    League league = new League(new SeasonConfiguration.LeagueConfiguration(ageGroup,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(0, false, ageGroup,
        this.postgresqlSeasonConfigurationRepository.findTeamsByAgeGroupEquals(ageGroup))));
    return league;
  }

  @Override
  public List<League> getLeagues(String season) {
    return null;
  }

  @Override
  public SeasonConfiguration createSeason(String seasonTag) {
    return null;
  }

  @Override
  public void removeAllCurrentSeasons() {

  }
}
