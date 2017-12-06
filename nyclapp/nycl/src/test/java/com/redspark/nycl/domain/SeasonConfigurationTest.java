package com.redspark.nycl.domain;

import org.junit.Test;

import static com.redspark.nycl.domain.AgeGroup.*;

public class SeasonConfigurationTest {

  @Test
  public void addsConfigToSeasonConfig() {

    SeasonConfiguration seasonConfig = new SeasonConfiguration("TEST");
    seasonConfig.add(getU10LeagueConfig());
    seasonConfig.add(getU11LeagueConfig());
    seasonConfig.add(getU12LeagueConfig());
    seasonConfig.add(getU13LeagueConfig());
    seasonConfig.add(getU14LeagueConfig());
    seasonConfig.add(getU15LeagueConfig());
    seasonConfig.add(getU158sLeagueConfig());
    seasonConfig.add(getU138sLeagueConfig());
    seasonConfig.add(getGirlsLeagueConfig());
  }

  private SeasonConfiguration.LeagueConfiguration getU10LeagueConfig() {
    SeasonConfiguration.LeagueConfiguration u10Config = new SeasonConfiguration.LeagueConfiguration(Under10,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, true, Under10),
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(2, true, Under10));
    return u10Config;
  }

  private SeasonConfiguration.LeagueConfiguration getU11LeagueConfig() {
    SeasonConfiguration.LeagueConfiguration cfg = new SeasonConfiguration.LeagueConfiguration(Under11,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, true, Under11),
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(2, false, Under11));
    return cfg;
  }

  private SeasonConfiguration.LeagueConfiguration getU12LeagueConfig() {
    SeasonConfiguration.LeagueConfiguration config = new SeasonConfiguration.LeagueConfiguration(Under12,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1,true, Under12),
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(2, true, Under12));
    return config;
  }

  private SeasonConfiguration.LeagueConfiguration getU13LeagueConfig() {
    SeasonConfiguration.LeagueConfiguration config = new SeasonConfiguration.LeagueConfiguration(Under13,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, true, Under13),
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(2, false, Under13));
    return config;
  }

  private SeasonConfiguration.LeagueConfiguration getU14LeagueConfig() {
    SeasonConfiguration.LeagueConfiguration config = new SeasonConfiguration.LeagueConfiguration(AgeGroup.Under14,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, false, Under14));
    return config;
  }

  private SeasonConfiguration.LeagueConfiguration getU15LeagueConfig() {
    SeasonConfiguration.LeagueConfiguration config = new SeasonConfiguration.LeagueConfiguration(AgeGroup.Under15,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, false, Under15));
    return config;
  }

  private SeasonConfiguration.LeagueConfiguration getU138sLeagueConfig() {
    SeasonConfiguration.LeagueConfiguration config = new SeasonConfiguration.LeagueConfiguration(AgeGroup.Under13_8S,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, true, Under13_8S));
    return config;
  }

  private SeasonConfiguration.LeagueConfiguration getU158sLeagueConfig() {
    SeasonConfiguration.LeagueConfiguration config = new SeasonConfiguration.LeagueConfiguration(AgeGroup.Under15_8S,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, true, Under15_8S));
    return config;
  }

  private SeasonConfiguration.LeagueConfiguration getGirlsLeagueConfig() {
    return new SeasonConfiguration.LeagueConfiguration(AgeGroup.GIRLS,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, true, GIRLS));
  }

}
