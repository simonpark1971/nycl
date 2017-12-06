package com.redspark.nycl.service.impl;

import com.redspark.nycl.domain.League;
import com.redspark.nycl.domain.SeasonConfiguration;
import com.redspark.nycl.service.impl.mogodb.MongoDBSeasonConfigurationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.redspark.nycl.domain.AgeGroup.Under10;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBSeasonConfigurationServiceTest {

  @Autowired
  private MongoDBSeasonConfigurationService service;

  private SeasonConfiguration seasonUnderTest;

  @Before
  public void setup() {
    service.removeAllCurrentSeasons();
    seasonUnderTest = service.createSeason("Test Season");
    seasonUnderTest.add(new SeasonConfiguration.LeagueConfiguration(Under10,
      new SeasonConfiguration.LeagueConfiguration.DivisionConfiguration(1, true, Under10)));
  }

  @Test
  public void getLeague() throws Exception {
    League lge = seasonUnderTest.getLeague(Under10);
    assertThat(lge.getAgeGroup(), is(Under10));
  }

  @Test
  public void getLeagues() throws Exception {
    List<League> lges = service.getLeagues("Test Season");
    assertThat(lges.size(), is(1));
  }

  @Test
  public void createSeason() throws Exception {
    service.createSeason("Test 2 Season");
  }

}
