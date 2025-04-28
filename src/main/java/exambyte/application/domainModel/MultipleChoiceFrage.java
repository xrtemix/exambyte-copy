package exambyte.application.domainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MultipleChoiceFrage implements Frage {
   private final UUID uuid;
   private String titel;
   private String fragestellung;
   private final List<String> antwortMoeglichkeiten;
   private double maxPunkte;
   private final List<String> korrekteLoesungen;

   public MultipleChoiceFrage(UUID uuid, String fragestellung, String titel, List<String> antwortMoeglichkeiten, List<String> korrekteLoesungen, double maxPunkte) {
      this.korrekteLoesungen = new ArrayList<String>(korrekteLoesungen);
      this.uuid = uuid;
      this.fragestellung = fragestellung;
      this.titel = titel;
      this.antwortMoeglichkeiten = new ArrayList<>(antwortMoeglichkeiten);
      this.maxPunkte = maxPunkte;
   }
   public MultipleChoiceFrage(String fragestellung, String titel, List<String> antwortMoeglichkeiten, List<String> korrekteLoesungen, double maxPunkte) {
      this.korrekteLoesungen = new ArrayList<String>(korrekteLoesungen);
      this.uuid = UUID.randomUUID();
      this.fragestellung = fragestellung;
      this.titel = titel;
      this.antwortMoeglichkeiten = new ArrayList<>(antwortMoeglichkeiten);
      this.maxPunkte = maxPunkte;
   }

   public List<String> getAntwortMoeglichkeiten() {
      return new ArrayList<String>(antwortMoeglichkeiten);
   }

   public List<String> getKorrekteLoesungen() {
      return new ArrayList<String>(korrekteLoesungen);
   }

   public UUID getUuid() {
      return uuid;
   }

   @Override
   public String getFragestellung() {
      return fragestellung;
   }

   @Override
   public void setFragestellung(String fragestellung) {
      this.fragestellung = fragestellung;
   }

   @Override
   public String getTitel() {
      return titel;
   }

   @Override
   public void setTitel(String titel) {
      this.titel = titel;
   }

   @Override
   public double getMaxPunkte() {
      return maxPunkte;
   }

   @Override
   public void setMaxPunkte(double maxPunkte) {
      this.maxPunkte = maxPunkte;
   }

   @Override
   public String getType() {
      return "mc";
   }

}

