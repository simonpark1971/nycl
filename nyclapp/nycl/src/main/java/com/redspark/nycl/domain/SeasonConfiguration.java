package com.redspark.nycl.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by simonpark on 09/02/2017.
 */
public class SeasonConfiguration {

  private Map<AgeGroup, LeagueConfiguration> ageGroupConfigurations = new HashMap();

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  private String tag;

  @Id
  private String id;

  public void add(LeagueConfiguration leagueConfig) {
    this.ageGroupConfigurations.put(leagueConfig.getAgeGroup(), leagueConfig);
  }

  public League getLeague(AgeGroup ageGroup) {
    return new League(ageGroupConfigurations.get(ageGroup));
  }

  public SeasonConfiguration(String tag) {
    this.tag = tag;
  }

  public List<League> getAllLeagues() {

    return this.ageGroupConfigurations.values().stream().map(cfg->cfg.createLeague()).collect(Collectors.toList());
  }


  public static class LeagueConfiguration {

    private int numberOfDivisions = 1;
    private AgeGroup ageGroup;
    private List<DivisionConfiguration> divisions = new ArrayList<>();

    public LeagueConfiguration(AgeGroup ageGroup, DivisionConfiguration... divisionConfig) {
      this.ageGroup = ageGroup;
      divisions.addAll(Arrays.asList(divisionConfig));
      this.numberOfDivisions = divisionConfig.length;
    }

    public League createLeague() {
      return new League(this);
    }

    public int getNumberOfDivisions() {
      return numberOfDivisions;
    }

    public AgeGroup getAgeGroup() {
      return ageGroup;
    }

    public List<DivisionConfiguration> getDivisions() {
      return divisions;
    }

    public boolean isHomeAndAway(int division) {
      for (DivisionConfiguration divisionConfiguration : divisions) {
        if (divisionConfiguration.divisionNumber == division) {
          return divisionConfiguration.homeAndAway;
        }
      }
      return false;
    }

    public List<Team> getDivisionTeams(int j) {
      for (DivisionConfiguration division : divisions) {
        if (division.divisionNumber == j) {
          return division.divisionTeams;
        }
      }
      return null;
    }

    public static class DivisionConfiguration {

      private int divisionNumber;
      private boolean homeAndAway;
      private AgeGroup ageGroup;
      private List<Team> divisionTeams = new ArrayList<>();

      public DivisionConfiguration(int divNum, boolean homeAndAway, AgeGroup ageGroup, List<Team> teams) {

        this.divisionTeams.addAll(teams);
        this.divisionNumber = divNum;
        this.homeAndAway = homeAndAway;
        this.ageGroup = ageGroup;
      }

      public DivisionConfiguration(int divNum, boolean homeAndAway, AgeGroup ageGroup) {
        this.divisionNumber = divNum;
        this.homeAndAway = homeAndAway;
        this.ageGroup = ageGroup;
      }

      public List<Team> getDivisionTeams() {
        return divisionTeams;
      }

      public int getDivisionNumber() {
        return divisionNumber;
      }

      public boolean isHomeAndAway() {
        return homeAndAway;
      }
    }
  }
}
