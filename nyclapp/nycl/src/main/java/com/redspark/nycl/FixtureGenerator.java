package com.redspark.nycl;

import com.google.gson.Gson;
import com.redspark.nycl.domain.*;
import com.redspark.nycl.service.ClubService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class FixtureGenerator implements CommandLineRunner {

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MM yyyy");
  public static final Calendar CURRENT_CALENDAR = new GregorianCalendar();
  private static final Logger LOGGER = LoggerFactory.getLogger(FixtureGenerator.class);
  public static Map<NYCL_CLUB, Club> CLUB_MAP = new HashMap<>();

  public static Map<NYCL_CLUB, Team> missingTeams = new HashMap<>();

  @Autowired
  private ClubService clubService;

  public static void main(String[] args) {
    SpringApplication.run(FixtureGenerator.class, args);
  }

  private static void writeToCSV(Set<Fixture> fl, String fileName) throws IOException {
    FileWriter writer = new FileWriter(fileName+".csv");
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator('\n');
    CSVPrinter printer = new CSVPrinter(writer, csvFileFormat);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    for (Fixture fixture : fl) {
      List<Object> fields = new ArrayList<Object>();
      if(fixture.getFixtureDate() != null) {
        fields.add(formatter.format(fixture.getFixtureDate()));
      } else {
        fields.add("");
      }
      fields.add(fixture.getHomeTeam() == null ? "Unknown age group" : fixture.getHomeTeam().getAgeGroup());
      fields.add(fixture.getHomeTeam() == null ? "Unknown home team" : fixture.getHomeTeam().getClubName());
      fields.add(fixture.getHomeTeam());
      fields.add(fixture.getAwayTeam() == null ? "Away team unknown" : fixture.getAwayTeam().getClubName());
      fields.add(fixture.getAwayTeam());
      printer.printRecord(fields);
    }
    writer.flush();
    writer.close();
    printer.close();
  }

  @Override
  public void run(String [] args) throws Exception {

    Options options = new Options();
    options.addOption("s", "startDate", true, "Season start date");
    options.addOption("e", "endDate", true, "Season end date");

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse( options, args);


    Date startDate = null, endDate = null;
    if (cmd.hasOption("s") && cmd.hasOption("e")) {
      try {
        startDate = DATE_FORMAT.parse(cmd.getOptionValue("s"));
        endDate = DATE_FORMAT.parse(cmd.getOptionValue("e"));
      } catch (ParseException e) {
        e.printStackTrace();
      }

      Season currentSeason = new Season(startDate, endDate);


      List<Club> clubs = clubService.getClubList();


      Gson gson = new Gson();
      LOGGER.info(gson.toJson(currentSeason));

      currentSeason.setFixtureDates();

      currentSeason.validateAllFixtures();
//
      List<Fixture> unallocatedFixtures = currentSeason.getUnallocatedFixtures();
      LOGGER.warn("Still : " + unallocatedFixtures.size() + " unallocated");
    }
//
//        writeToCSV(fl.getListOfFixtures(), "2017-Master-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.Under10)).collect(Collectors.toSet()), "2017-U10-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.Under11)).collect(Collectors.toSet()), "2017-U11-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.Under12)).collect(Collectors.toSet()), "2017-U12-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.Under13)).collect(Collectors.toSet()), "2017-U13-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.Under14)).collect(Collectors.toSet()), "2017-U14-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.Under15)).collect(Collectors.toSet()), "2017-U15-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.GIRLS)).collect(Collectors.toSet()), "2017-GIRLS-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.Under13_8S)).collect(Collectors.toSet()), "2017-U12-8S-Fixtures");
//        writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getAwayTeam().getAgeGroup().equals(AgeGroup.Under15_8S)).collect(Collectors.toSet()), "2017-U14-8S-Fixtures");
//
//        for (Club club : clubs) {
//            writeToCSV(fl.getListOfFixtures().stream().filter(f->f.getHomeTeam().getClub().clubName.equals(club.clubName)
//                 || f.getAwayTeam().getClub().clubName.equals(club.clubName)).collect(Collectors.toSet()), "2017-"+club.clubName+"-Fixtures");
//        }
  }

}
