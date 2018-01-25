package com.redspark.nycl;

import com.google.gson.Gson;
import com.redspark.nycl.domain.*;
import com.redspark.nycl.persistence.PostgresqlTeamRepository;
import com.redspark.nycl.service.ClubService;
import com.redspark.nycl.service.SeasonConfigurationService;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class FixtureGenerator implements CommandLineRunner {


  public static final Calendar CURRENT_CALENDAR = new GregorianCalendar();
  private static final Logger LOGGER = LoggerFactory.getLogger(FixtureGenerator.class);

  @Autowired
  private ClubService clubService;

  @Autowired
  private SeasonConfigurationService seasonConfigurationService;

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
  @Transactional
  public void run(String [] args) throws Exception {


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
