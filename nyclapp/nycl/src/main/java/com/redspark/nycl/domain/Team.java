package com.redspark.nycl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by simonpark on 10/12/2015.
 */
@Entity
@Table(name = "team")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

  private static final Calendar CALENDAR = new GregorianCalendar();
  public static final Logger LOGGER = LoggerFactory.getLogger(Team.class);

  @Id @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String id;

  @Embedded @AttributeOverrides({
    @AttributeOverride(name = "contactAddress", column = @Column(name = "primary_contact_address")),
    @AttributeOverride(name = "contactEmail", column = @Column(name = "primary_contact_email")),
    @AttributeOverride(name = "contactName", column = @Column(name = "primary_contact_name")),
    @AttributeOverride(name = "contactHomePhone", column = @Column(name = "primary_contact_home_phone")),
    @AttributeOverride(name = "contactMobilePhone", column = @Column(name = "primary_contact_mobile_phone")),
    @AttributeOverride(name = "contactPostcode", column = @Column(name = "primary_contact_postcode")),
    @AttributeOverride(name = "username", column = @Column(name = "primary_contact_username")),
    @AttributeOverride(name = "contactType", column = @Column(name = "primary_contact_type")),
    @AttributeOverride(name = "contactPosition", column = @Column(name = "primary_contact_position"))})
  private Contact primaryContact;

  @Embedded @AttributeOverrides({
    @AttributeOverride(name = "contactAddress", column = @Column(name = "secondary_contact_address")),
    @AttributeOverride(name = "contactEmail", column = @Column(name = "secondary_contact_email")),
    @AttributeOverride(name = "contactName", column = @Column(name = "secondary_contact_name")),
    @AttributeOverride(name = "contactHomePhone", column = @Column(name = "secondary_contact_home_phone")),
    @AttributeOverride(name = "contactMobilePhone", column = @Column(name = "secondary_contact_mobile_phone")),
    @AttributeOverride(name = "contactPostcode", column = @Column(name = "secondary_contact_postcode")),
    @AttributeOverride(name = "username", column = @Column(name = "secondary_contact_username")),
    @AttributeOverride(name = "contactType", column = @Column(name = "secondary_contact_type")),
    @AttributeOverride(name = "contactPosition", column = @Column(name = "secondary_contact_position"))})
  private Contact secondaryContact;

  private String teamName = "";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CLUB_ID", nullable = false)
  @JsonIgnore
  private Club club;
  public String clubName;
  private AgeGroup ageGroup;
  private boolean playHomeMondays = false;
  private boolean playHomeTuesdays = false;
  private boolean playHomeWednesdays = false;
  private boolean playHomeThursdays = false;
  private boolean playHomeFridays = false;
  private boolean playHomeSundays = false;

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public void setClubName(String clubName) {
    this.clubName = clubName;
  }

  public boolean isPlayHomeMondays() {
    return playHomeMondays;
  }

  public void setPlayHomeMondays(boolean playHomeMondays) {
    this.playHomeMondays = playHomeMondays;
  }

  public boolean isPlayHomeTuesdays() {
    return playHomeTuesdays;
  }

  public void setPlayHomeTuesdays(boolean playHomeTuesdays) {
    this.playHomeTuesdays = playHomeTuesdays;
  }

  public boolean isPlayHomeWednesdays() {
    return playHomeWednesdays;
  }

  public void setPlayHomeWednesdays(boolean playHomeWednesdays) {
    this.playHomeWednesdays = playHomeWednesdays;
  }

  public boolean isPlayHomeThursdays() {
    return playHomeThursdays;
  }

  public void setPlayHomeThursdays(boolean playHomeThursdays) {
    this.playHomeThursdays = playHomeThursdays;
  }

  public boolean isPlayHomeFridays() {
    return playHomeFridays;
  }

  public void setPlayHomeFridays(boolean playHomeFridays) {
    this.playHomeFridays = playHomeFridays;
  }

  public boolean isPlayHomeSundays() {
    return playHomeSundays;
  }

  public void setPlayHomeSundays(boolean playHomeSundays) {
    this.playHomeSundays = playHomeSundays;
  }

  @Transient
  private List<Club.FixtureRule> rules;

  private int rank;
  private boolean deleted = false;

  public Team() {}

  public static Team createTeam(Club club, AgeGroup ageGroup,
                                boolean playHomeMondays,
                                boolean playHomeTuesdays,
                                boolean playHomeWednesdays,
                                boolean playHomeThursdays,
                                boolean playHomeFridays,
                                boolean playHomeSundays) {
    return new Team(club, ageGroup, null, null, null, playHomeMondays, playHomeTuesdays, playHomeWednesdays, playHomeThursdays, playHomeFridays, playHomeSundays);
  }

  public static Team createTeam(NYCL_CLUB club, AgeGroup ageGroup) {
    return new Team(new Club(club.toString(), 1), ageGroup, null, null, null, false, false, false, false, false, false);
  }

  public static Team createTeam(NYCL_CLUB club, AgeGroup ageGroup, String teamName) {
    return new Team(new Club(club.toString(), 1), ageGroup, null, null, teamName, false, false, false, false, false, false);
  }

  public static Team createTeam(Club club,
                                AgeGroup ageGroup,
                                Contact primaryContact,
                                Contact secondaryContact,
                                String teamName,
                                boolean playHomeMondays,
                                boolean playHomeTuesdays,
                                boolean playHomeWednesdays,
                                boolean playHomeThursdays,
                                boolean playHomeFridays,
                                boolean playHomeSundays) {

    return new Team(club, ageGroup, primaryContact, secondaryContact, teamName, playHomeMondays, playHomeTuesdays, playHomeWednesdays, playHomeThursdays, playHomeFridays, playHomeSundays);
  }

  public Team(Club club,
              AgeGroup ageGroup,
              Contact primaryContact,
              Contact secondaryContact,
              String teamName,
              boolean playHomeMondays,
              boolean playHomeTuesdays,
              boolean playHomeWednesdays,
              boolean playHomeThursdays,
              boolean playHomeFridays,
              boolean playHomeSundays) {

    this.ageGroup = ageGroup;
    this.club = club == null ? new Club("Unknown", 1) : club;
    this.primaryContact = primaryContact;
    this.secondaryContact = secondaryContact;
    this.clubName = this.getClub().getClubName();
    this.teamName = teamName;
  }

  public Set<Date> getAllPossibleDates(List<Week> seasonWeeks) {

    Set<Date> availableDates = new HashSet<>();
    for (Week seasonWeek : seasonWeeks) {
      availableDates.addAll(
        seasonWeek.getDates().stream().filter(dt->isOnPreferredDay(dt)).collect(Collectors.toSet()));
    }
    return availableDates;
  }

  public Set<Date> getAllPossibleDates(Week week) {
    return week.getDates().stream().filter(dt->isOnPreferredDay(dt)).collect(Collectors.toSet());
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Team team = (Team) o;

    if (deleted != team.deleted) return false;
    if (teamName != null ? !teamName.equals(team.teamName) : team.teamName != null) return false;
    if (club != null ? !club.equals(team.club) : team.club != null) return false;
    if (clubName != null ? !clubName.equals(team.clubName) : team.clubName != null) return false;
    return ageGroup == team.ageGroup;
  }

  @Override
  public int hashCode() {
    int result = teamName != null ? teamName.hashCode() : 0;
    result = 31 * result + (club != null ? club.hashCode() : 0);
    result = 31 * result + (clubName != null ? clubName.hashCode() : 0);
    result = 31 * result + (ageGroup != null ? ageGroup.hashCode() : 0);
    result = 31 * result + (deleted ? 1 : 0);
    return result;
  }

  public String toString() {

    if(this.clubName == null && this.getClub() != null) {
      clubName = this.getClub().getClubName();
    }
    return clubName + " " + (this.teamName != null ? this.teamName : ageGroup.toString());
  }

  public boolean isValidDateForHomeTeam(FixtureManager fl, Date dt, boolean ignoreAdjacent) {
    boolean onPreferredDay = isOnPreferredDay(dt), pitchAvailable = isThereAPitchAvailableOn(fl, dt);
    if(!onPreferredDay) {
      return false;
    }

    if(!pitchAvailable) {
      LOGGER.warn("No pitch available.");
      return false;
    }

    return validateSuggestedFixture(fl, dt, ignoreAdjacent);
  }

  public boolean isTeamInvolvedInFixture(Fixture f) {
    return (f.getHomeTeam().equals(this) || f.getAwayTeam().equals(this));
  }

  private boolean isOnPreferredDay(Date fixtureDate) {

    CALENDAR.setTime(fixtureDate);
    int day = CALENDAR.get(Calendar.DAY_OF_WEEK);

    return (day == CALENDAR.MONDAY && this.playHomeMondays) ||
      (day == CALENDAR.TUESDAY && this.playHomeTuesdays) ||
      (day == CALENDAR.WEDNESDAY && this.playHomeWednesdays) ||
      (day == CALENDAR.THURSDAY && this.playHomeThursdays) ||
      (day == CALENDAR.FRIDAY && this.playHomeFridays) ||
      (day == CALENDAR.SUNDAY && this.playHomeSundays);
  }

  private boolean canPlayOn(Date fixtureDate) {
    CALENDAR.setTime(fixtureDate);
    int day = CALENDAR.get(Calendar.DAY_OF_WEEK);
    if(day == CALENDAR.SATURDAY)
      return false;

    if(rules == null)
      return true;

    for (Club.FixtureRule rule : rules) {
      switch(rule) {
        case NOT_FRIDAY:
          if (day == Calendar.FRIDAY) {
            return false;
          }
          break;
        case NOT_MONDAY:
          if (day == Calendar.MONDAY) {
            return false;
          }
          break;
        case NOT_TUESDAY:
          if(day == Calendar.TUESDAY) {
            return false;
          }
          break;
        case NOT_THURSDAY:
          if (day == Calendar.THURSDAY) {
            return false;
          }
          break;
      }
    }
    return true;
  }

  public void setRules(List<Club.FixtureRule> rules) {
    this.rules = rules;
  }

  private boolean adjacentTeamClashes(FixtureManager fl, Date dt) {
    switch (this.ageGroup) {
      case Under10:
        return fl.existingGameFor(dt, this.club, AgeGroup.Under11);
      case Under11:
        return fl.existingGameFor(dt, this.club, AgeGroup.Under10, AgeGroup.Under12);
      case Under12:
        return fl.existingGameFor(dt, this.club, AgeGroup.Under11, AgeGroup.Under13);
      case Under13:
        return fl.existingGameFor(dt, this.club, AgeGroup.Under12, AgeGroup.Under14, AgeGroup.Under15_8S);
      case Under14:
        return fl.existingGameFor(dt, this.club, AgeGroup.Under13, AgeGroup.Under15, AgeGroup.Under15_8S);
      case Under15:
        return fl.existingGameFor(dt, this.club, AgeGroup.Under14, AgeGroup.Under15_8S);
      case Under13_8S:
        return fl.existingGameFor(dt, this.club, AgeGroup.Under12, AgeGroup.Under13, AgeGroup.Under14, AgeGroup.Under15_8S);
      case Under15_8S:
        return fl.existingGameFor(dt, this.club, AgeGroup.Under14);
      default:
        return false;
    }
  }

  public boolean isTeamInvolvedInFixtureAtHome(Fixture f) {
    if(f.isValid() && f.getHomeTeam().club == null) {
      LOGGER.warn("Team has no club: " + f.getHomeTeam().toString());
      return false;
    }
    return (f.isValid() && f.getHomeTeam().getClub().equals(this.getClub()));
  }

  public boolean isValidDateForAwayTeam(FixtureManager fl, Date dt, boolean ignoreAdjacent) {
    return validateSuggestedFixture(fl, dt, ignoreAdjacent);
  }

  public boolean hasTeamGotAFixtureThisWeek(Set<Fixture> fl, Week week) {

    return getTeamFixtures(fl).stream().filter(f-> week.inThisWeek(f.getDate())).collect(Collectors.toList()).size() > 0;
  }

  public boolean hasTeamGotAFixtureWithinHalfAWeek(Set<Fixture> fl, Week week, Date compareTo) {
    return getTeamFixtures(fl, week)
      .stream()
      .filter(f->f.getFixtureDate() != null && !week.withinNDays(f.getFixtureDate(), compareTo, 2))
      .collect(Collectors.toList()).size() > 0;
  }

  public List<Fixture> getTeamFixtures(Set<Fixture> fixtureList) {
    return fixtureList.stream().filter(f->f.isForHomeOrAway(this)).collect(Collectors.toList());
  }

  public List<Fixture> getTeamFixtures(Set<Fixture> fixtureList, Week week) {
    return fixtureList.stream().filter(f->f.isValid() && (f.getHomeTeam().equals(this) || f.getAwayTeam().equals(this)) && null != f.getFixtureDate() && week.inThisWeek(f.getFixtureDate())).collect(Collectors.toList());
  }

  private boolean isThereAPitchAvailableOn(FixtureManager fl, Date dt) {
    return getRemainingHomeClubCapacity(fl, dt) >= 1;
  }

  private int getRemainingHomeClubCapacity(FixtureManager fl, Date dt) {
    int homeOnThisDay = fl.getHomeFixtureCountOnDateForClub(dt, this);
    return this.getClub().pitches - homeOnThisDay;
  }

  private boolean validateSuggestedFixture(FixtureManager fl, Date dt, boolean ignoreAdjacent) {

    boolean canPlayOn = canPlayOn(dt);
    boolean adjacentTeamClash = ignoreAdjacent || adjacentTeamClashes(fl, dt);
    List<Fixture> fixtures = fl.getTeamFixtures(this);
    for (Fixture fixture : fixtures) {
      if(fixture.getDate() != null) {
        if(Week.withinNDays(fixture.getFixtureDate(), dt, 2)) {
          return false;
        }
      }
    }

    return canPlayOn && !adjacentTeamClash;
  }

  public String getName() {
    return this.teamName;
  }

  public void setName(String name) {
    this.teamName = name;
  }

  public Contact getPrimaryContact() {
    return primaryContact;
  }

  public Contact getSecondaryContact() {
    return secondaryContact;
  }

  public void delete() {
    this.deleted = true;
  }

  public AgeGroup getAgeGroup() {
    return ageGroup;
  }

  public void setPreferredDays(Integer ... days) {
    for (Integer day : days) {
      switch (day) {
        case 2:
          this.playHomeMondays = true;
          break;
        case 3:
          this.playHomeTuesdays = true;
          break;
        case 4:
          this.playHomeWednesdays = true;
          break;
        case 5:
          this.playHomeThursdays = true;
          break;
        case 6:
          this.playHomeFridays = true;
          break;
        case 1:
          this.playHomeSundays = true;
          break;
        default:
          resetAllHomeDays();
      }
    }
  }

  private void resetAllHomeDays() {
    this.playHomeSundays = false;
    this.playHomeMondays = false;
    this.playHomeTuesdays = false;
    this.playHomeThursdays = false;
    this.playHomeFridays = false;
    this.playHomeWednesdays = false;
  }

  public Club getClub() {
    return club;
  }

  public String getClubName() {
    return this.clubName == null ? "Unknown club" : this.clubName;
  }

  public void setClub(Club club) {
    this.club = club;
    this.clubName = this.getClub().getClubName();
  }

  public boolean isDeleted() {
    return deleted;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public int getHomeFixtureCount(Set<Fixture> divisionFixtureList) {
    return divisionFixtureList.stream().filter(f->f.getHomeTeam().equals(this)).collect(Collectors.toSet()).size();
  }

  public int getAwayFixtureCount(Set<Fixture> divisionFixtureList) {
    return divisionFixtureList.stream().filter(f->f.getAwayTeam().equals(this)).collect(Collectors.toList()).size();
  }

  public Set<Fixture> getHomeFixtures(Set<Fixture> divisionFixtureList) {
    return divisionFixtureList.stream().filter(f->f.getHomeTeam().equals(this)).collect(Collectors.toSet());
  }

  public Set<Fixture> getAwayFixtures(Set<Fixture> divisionFixtureList) {
    return divisionFixtureList.stream().filter(f->f.getAwayTeam().equals(this)).collect(Collectors.toSet());
  }
}
