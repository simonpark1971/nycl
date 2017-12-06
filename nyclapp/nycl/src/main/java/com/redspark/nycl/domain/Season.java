package com.redspark.nycl.domain;

import com.redspark.nycl.FixtureGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Season {

  private static final Logger LOGGER = LoggerFactory.getLogger(Season.class);

    private Date startDate;
    private Date endDate;
    private List<Week> weeks;
    private List<League> leagues = new ArrayList<>();

  public SeasonConfiguration getConfig() {
    return config;
  }

  public void setConfig(SeasonConfiguration config) {
    this.config = config;
  }

  private SeasonConfiguration config;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String name;

    public Season(Date start, Date end) {

        this.startDate = start;
        this.endDate = end;

       try {
           this.weeks = WeekBuilder.buildWeeks(startDate, endDate);
       } catch(Exception e) {
           e.printStackTrace();
       }

       LOGGER.info("Created a season that starts " + startDate.toString() + " and ends " + endDate.toString());
    }

    public void addLeague(League league) {
        this.leagues.add(league);
    }

    public List<Fixture> getFixtures() {
        List<Fixture> allFixtures = new ArrayList<>();
        for (League league : leagues) {
            for (League.Division division : league.getDivisions()) {
                allFixtures.addAll(division.getFixtures());
            }
        }
        return allFixtures;
    }

    public void setFixtureDates() {
        for (League league : leagues) {
          league.generateFixtures();
          league.setFixtureDates(weeks);
          league.validateLeague();
        }
    }

    public void validateAllFixtures() {
        for (NYCL_CLUB nycl_club : FixtureGenerator.CLUB_MAP.keySet()) {
            Set<Fixture> duplicatesFixtures = FixtureGenerator.CLUB_MAP.get(nycl_club).validateFixtures(
                    this.leagues.stream()
                            .flatMap(league -> league.getClubFixtures(
                                    FixtureGenerator.CLUB_MAP.get(nycl_club)).stream())
                            .collect(Collectors.toSet()));

            LOGGER.info(nycl_club + " " + duplicatesFixtures.size());
            LOGGER.info(duplicatesFixtures.toString());

        }
    }

  public List<Fixture> getUnallocatedFixtures() {
    return getFixtures().stream().filter(f->f.getFixtureDate() == null).collect(Collectors.toList());
  }
}
