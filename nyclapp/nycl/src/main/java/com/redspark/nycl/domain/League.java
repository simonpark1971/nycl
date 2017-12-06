package com.redspark.nycl.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class League {

  private static final Logger LOGGER = LoggerFactory.getLogger(League.class);

    private SeasonConfiguration.LeagueConfiguration leagueConfig;
    private List<Division> divisions = new ArrayList<>();

    public League(SeasonConfiguration.LeagueConfiguration leagueConfig) {
        this.leagueConfig = leagueConfig;
        for (SeasonConfiguration.LeagueConfiguration.DivisionConfiguration divisionConfiguration : leagueConfig.getDivisions()) {
            divisions.add(new Division(divisionConfiguration));
        }
    }

    public League() { }

    public boolean validateLeague() {
        for (Division division : divisions) {
            boolean isValid = division.validateDivision();
            if(!isValid) {
                LOGGER.info(division.toString() + " is not valid.");
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Division division : divisions) {
            sb.append(division);
        }

        return sb.toString();
    }

    public AgeGroup getAgeGroup() {
        return this.leagueConfig.getAgeGroup();
    }
    public List<Division> getDivisions() {
        return divisions;
    }

    public void generateFixtures() {
      for (Division division : divisions) {
            division.generateFixtures();
        }
    }

    public void setFixtureDates(List<Week> weeks) {
        for (Division division : divisions) {
            division.setFixtureDates(weeks);
        }
    }

    public Set<Fixture> getClubFixtures(Club club) {
        return this.getDivisions().stream()
                .flatMap(division -> division.divisionFixtureList.stream())
                .filter(f -> f.isForHomeOrAway(club))
                .collect(Collectors.toSet());
    }

    public class Division {

        private SeasonConfiguration.LeagueConfiguration.DivisionConfiguration config;
        private Set<Fixture> divisionFixtureList = new HashSet<>();

        public boolean validateDivision() {
            return validateNumberOfGames() && validateHomeAndAway();
        }

        private boolean validateHomeAndAway() {
            for (Team clubTeam : config.getDivisionTeams()) {
                if(config.isHomeAndAway()) {
                    if(clubTeam.getHomeFixtureCount(this.divisionFixtureList) != clubTeam.getAwayFixtureCount(this.divisionFixtureList)) {
                        return false;
                    }
                } else {
                    int games = this.config.getDivisionTeams().size() - 1;
                    int minHomeAndAway = games / 2;
                    if(clubTeam.getHomeFixtureCount(this.divisionFixtureList) >= minHomeAndAway && clubTeam.getAwayFixtureCount(this.divisionFixtureList) >= minHomeAndAway) {

                    } else {
                        LOGGER.info(clubTeam + " not equal home and away games");
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean validateNumberOfGames() {
            if (config.isHomeAndAway()) {
                return divisionFixtureList.size() == (config.getDivisionTeams().size() - 1) * config.getDivisionTeams().size();
            } else {
                return divisionFixtureList.size() == (int)calcTriangularNumber(config.getDivisionTeams().size());
            }
        }

        private double calcTriangularNumber(double size) {
            return (size-1) * (size/2);
        }

        public Division(SeasonConfiguration.LeagueConfiguration.DivisionConfiguration cfg) {
            this.config = cfg;
        }

        public void generateFixtures() {

            List<Team> randomOrderFirstTeams = new ArrayList(config.getDivisionTeams());
            List<Team> randomOrderSecondTeams = new ArrayList(config.getDivisionTeams());
            Collections.shuffle(randomOrderFirstTeams, new Random(System.nanoTime()));
            Collections.shuffle(randomOrderSecondTeams, new Random(System.nanoTime()));

            for (Team clubTeam : randomOrderFirstTeams) {
                for (Team randomOrderSecondTeam : randomOrderSecondTeams) {
                    if (!clubTeam.equals(randomOrderSecondTeam)) {
                        if (config.isHomeAndAway()) {

                            this.divisionFixtureList.add(new Fixture(clubTeam,
                                    randomOrderSecondTeam));

                            //add reverse fixture
                            this.divisionFixtureList.add(new Fixture(randomOrderSecondTeam,
                                   clubTeam));
                        } else {
                            if(!existsIgnoreHomeAndAway(clubTeam, randomOrderSecondTeam)) {
                                createAndAddFixture(randomOrderSecondTeam, clubTeam);
                            }
                        }
                    }
                }
            }
        }

        private boolean existsIgnoreHomeAndAway(Team team, Team team1) {
            Fixture f1 = new Fixture(team, team1);
            Fixture f2 = new Fixture(team1, team);
            return this.divisionFixtureList.contains(f1) || this.divisionFixtureList.contains(f2);
        }

        private void createAndAddFixture(Team team, Team team1) {
            int homeGames = team.getHomeFixtureCount(this.divisionFixtureList);
            int awayGames = team.getAwayFixtureCount(this.divisionFixtureList);
            int homeGames2 = team1.getHomeFixtureCount(this.divisionFixtureList);
            int awayGames2 = team1.getAwayFixtureCount(this.divisionFixtureList);

            if (homeGames == awayGames) {
                if (homeGames2 == awayGames2) {
                    this.addUniqueFixture(new Fixture(team, team1));
                } else if (homeGames2 < awayGames2) {
                    this.addUniqueFixture(new Fixture(team1, team));
                } else if (homeGames2 > awayGames2) {
                    this.addUniqueFixture(new Fixture(team, team1));
                }
            } else if (homeGames < awayGames) {
                if (homeGames2 == awayGames2) {
                    this.addUniqueFixture(new Fixture(team, team1));
                } else if (homeGames2 < awayGames2) {

                    // team1 has less home games, but team 2 has more away games
                    this.addUniqueFixture(new Fixture(team, team1));

                } else if (homeGames2 > awayGames2) {
                    this.addUniqueFixture(new Fixture(team, team1));
                }
            } else {
                if (homeGames2 == awayGames2) {
                    // add as away fixture
                    this.addUniqueFixture(new Fixture(team1, team));
                } else if (homeGames2 < awayGames2) {
                    // add as away fixture
                    this.addUniqueFixture(new Fixture(team1, team));
                } else if (homeGames2 > awayGames2) {

                    // team1 has more home games but team 2 has less away games
                    this.addUniqueFixture(new Fixture(team1, team));
                }
            }
        }

        private void addUniqueFixture(Fixture f) {

            if(!this.divisionFixtureList.contains(f)) {
                this.divisionFixtureList.add(f);
            } else {
                LOGGER.info("Fixture already exists: " + f);
            }
        }

        public Set<Fixture> getFixtures() {
            return this.divisionFixtureList;
        }

        public void setFixtureDates(List<Week> weeks) {
            for (Fixture fixture : divisionFixtureList) {
                if (fixture.getFixtureDate() == null) {
                    if (fixture.isValid()) {
                        for (Week week : weeks) {
                            if(bothTeamsAreFreeInWeek(week,fixture)) {
                                Set<Date> dates = fixture.findDates(week);
                                if(dates != null && dates.size() > 0) {
                                    fixture.setFixtureDate((Date)dates.toArray()[0]);
                                }
                            }
                        }
                    }
                }
            }
        }

        private boolean bothTeamsAreFreeInWeek(Week week, Fixture fixture) {
            return !(fixture.getHomeTeam().hasTeamGotAFixtureThisWeek(divisionFixtureList, week) ||
                    fixture.getAwayTeam().hasTeamGotAFixtureThisWeek(divisionFixtureList, week));
        }

    }


}
