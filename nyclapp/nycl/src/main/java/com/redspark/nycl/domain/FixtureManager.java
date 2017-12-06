package com.redspark.nycl.domain;

import java.util.*;
import java.util.stream.Collectors;

public class FixtureManager {

    private static FixtureManager instance;
    private Season currentSeason;

    private FixtureManager(Date startDate, Date endDate) {
        ;
    }

    public static final FixtureManager getInstance(Date startDate, Date endDate) {
        if (instance == null) {
            instance = new FixtureManager(startDate, endDate);
        }
        return instance;
    }

    public void setFixtureDates() {
        this.currentSeason.setFixtureDates();
    }



    public void addFixtures(SeasonConfiguration.LeagueConfiguration cfg) {

        League league = new League(cfg);
        league.generateFixtures();
        currentSeason.addLeague(league);
    }

    public List<Fixture> getTeamFixtures(Team team) {

        List<Fixture> teamFixtures = new ArrayList<Fixture>();
        if(team == null)
            return teamFixtures;
        for (Fixture fixture : currentSeason.getFixtures()) {
            if(fixture.isValid()) {
                if (team.equals(fixture.getHomeTeam())) {
                    teamFixtures.add(fixture);
                } else if (team.equals(fixture.getAwayTeam())) {
                    teamFixtures.add(fixture);
                }
            }
        }
        return teamFixtures;
    }

    public int getHomeFixtureCountOnDateForClub(Date dt, Team team) {
        return this.currentSeason.getFixtures().stream()
                .filter(f->team.isTeamInvolvedInFixtureAtHome(f) && (f.isFixtureOnDate(dt)))
                .collect(Collectors.toList()).size();
    }

    public boolean existingGameFor(Date dt, Club club, AgeGroup ...ageGroups) {
        return this.currentSeason.getFixtures().stream()
                .filter(f->f.isValid() && (f.isClubAgeGroupInvolvedInFixture(club, ageGroups)) && (f.isFixtureOnDate(dt)))
                .collect(Collectors.toList()).size() > 0;
    }

    public void validateFixtures() {
        this.currentSeason.validateAllFixtures();
    }
}
