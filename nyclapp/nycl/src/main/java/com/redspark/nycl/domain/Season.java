package com.redspark.nycl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "season")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Season {

  private static final Logger LOGGER = LoggerFactory.getLogger(Season.class);

  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String id;
  private Date startDate;
  private Date endDate;

  @Transient
  private List<Week> weeks;
  @Transient
  private List<League> leagues = new ArrayList<>();

  @OneToOne
  @MapsId
  private SeasonConfiguration config;
  private String name;

  public Season(Date start, Date end, SeasonConfiguration config) {

    this.startDate = start;
    this.endDate = end;
    this.setConfig(config);

    try {
      this.weeks = WeekBuilder.buildWeeks(startDate, endDate);
    } catch(Exception e) {
      e.printStackTrace();
    }

    LOGGER.info("Created a season that starts " + startDate.toString() + " and ends " + endDate.toString());
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

  public void createFixtures(Map<AgeGroup, List<Team>> map) {
    for (AgeGroup ageGroup : config.getAgeGroupConfigurations().keySet()) {
      leagues.add(new League(config.getAgeGroupConfigurations().get(ageGroup), map.get(ageGroup)));
    }
  }

  public void validateAllFixtures() {
//        for (NYCL_CLUB nycl_club : FixtureGenerator.CLUB_MAP.keySet()) {
//            Set<Fixture> duplicatesFixtures = FixtureGenerator.CLUB_MAP.get(nycl_club).validateFixtures(
//                    this.leagues.stream()
//                            .flatMap(league -> league.getClubFixtures(
//                                    FixtureGenerator.CLUB_MAP.get(nycl_club)).stream())
//                            .collect(Collectors.toSet()));
//
//            LOGGER.info(nycl_club + " " + duplicatesFixtures.size());
//            LOGGER.info(duplicatesFixtures.toString());
//
//        }
  }

  public List<Fixture> getUnallocatedFixtures() {
    return getFixtures().stream().filter(f->f.getFixtureDate() == null).collect(Collectors.toList());
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public SeasonConfiguration getConfig() {
    return config;
  }
  public void setConfig(SeasonConfiguration config) {
    this.config = config;
  }

}
