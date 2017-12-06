package com.redspark.nycl.service.impl.mogodb;

import com.redspark.nycl.domain.AgeGroup;
import com.redspark.nycl.domain.League;
import com.redspark.nycl.domain.SeasonConfiguration;
import com.redspark.nycl.persistence.SeasonConfigurationRepository;
import com.redspark.nycl.service.SeasonConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoDBSeasonConfigurationService implements SeasonConfigurationService {

  @Autowired
  private SeasonConfigurationRepository seasonConfigurationRepo;

  @Override
  public League getLeague(String season, AgeGroup ageGroup) {

    SeasonConfiguration config = seasonConfigurationRepo.findByTag(season);
    return config.getLeague(ageGroup);
  }

  @Override
  public List<League> getLeagues(String season) {
    SeasonConfiguration config = seasonConfigurationRepo.findByTag(season);
    return config.getAllLeagues();
  }

  @Override
  public SeasonConfiguration createSeason(String seasonTag) {
    return seasonConfigurationRepo.save(new SeasonConfiguration(seasonTag));
  }

  @Override
  public void removeAllCurrentSeasons() {
    seasonConfigurationRepo.deleteAll();
  }
}
