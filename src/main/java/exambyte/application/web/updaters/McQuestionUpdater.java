package exambyte.application.web.updaters;

import java.util.ArrayList;
import java.util.List;

public record McQuestionUpdater(
    String testName,
    String titel,
    String fragestellung,
    List<String> antwortMoeglichkeiten,
    List<String> korrekteLoesung,
    double maxPunkte
) {

   @Override
   public List<String> antwortMoeglichkeiten() {
      return new ArrayList<>(antwortMoeglichkeiten);
   }

   @Override
   public List<String> korrekteLoesung() {
      return new ArrayList<>(korrekteLoesung);
   }

   public McQuestionUpdater(String testName, String titel, String fragestellung, List<String> antwortMoeglichkeiten, List<String> korrekteLoesung, double maxPunkte) {
      this.testName = testName;
      this.titel = titel;
      this.fragestellung = fragestellung;
      this.antwortMoeglichkeiten = new ArrayList<>(antwortMoeglichkeiten);
      this.korrekteLoesung = new ArrayList<>(korrekteLoesung);
      this.maxPunkte = maxPunkte;
   }
}
