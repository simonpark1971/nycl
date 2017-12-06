package com.redspark.nycl.persistence;

import com.redspark.nycl.domain.League;
import com.redspark.nycl.domain.SeasonConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBSeasonConfigurationRepoTest {

  @Autowired
  private SeasonConfigurationRepository seasonConfigurationRepository;

  @Test
  public void deleteAllLeagues() {
    seasonConfigurationRepository.deleteAll();
  }

  @Test
  public void getLeagues() throws Exception {
    SeasonConfiguration config = seasonConfigurationRepository.findByTag("Test");
    List<League> leagues = config.getAllLeagues();
  }

  @Test
  public void testCreatesNewSeason() {
    seasonConfigurationRepository.save(new SeasonConfiguration("Test"));
    SeasonConfiguration cfg = seasonConfigurationRepository.findByTag("Test");
  }

}
