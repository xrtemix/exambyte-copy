package exambyte.application.domainModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Test {
   private final UUID uuid;
   private String testName;
   private LocalDate testStart;
   private LocalDate testEnde;
   private List<Frage> fragen;

   public Test(String testName) {
      this.uuid = UUID.randomUUID();
      this.testName = testName;
      this.fragen = new ArrayList<>();
   }

   public Test(String testName, LocalDate testStart, LocalDate testEnde, List<Frage> fragen) {
      this.uuid = UUID.randomUUID();
      this.testName = testName;
      this.testStart = testStart;
      this.testEnde = testEnde;
      this.fragen = new ArrayList<>(fragen);
   }

   public Test(UUID uuid, String testName, LocalDate testStart, LocalDate testEnde,
               List<Frage> fragen) {
      this.uuid = uuid;
      this.testName = testName;
      this.testStart = testStart;
      this.testEnde = testEnde;
      this.fragen = new ArrayList<>(fragen);
   }


   public String getTestName() {
      return testName;
   }

   public void setTestName(String testName) {
      this.testName = testName;
   }

   public List<Frage> getFragen() {
      return new ArrayList<>(fragen);
   }

   public void setFragen(List<Frage> fragen) {
      this.fragen = new ArrayList<>(fragen);
   }

   public Frage getFrageByIndex(int index) {
      return fragen.get(index);
   }

   public Frage getFrageByName(String frageName) {
      for (Frage frage : fragen) {
         if (frage.getTitel().equals(frageName)) {
            return frage;
         }
      }
      return null;
   }

   public void addQuestion(Frage frage) {
      this.fragen.add(frage);
   }

   public LocalDate getTestStart() {
      return testStart;
   }

   public void setTestStart(LocalDate testStart) {
      this.testStart = testStart;
   }

   public LocalDate getTestEnde() {
      return testEnde;
   }

   public void setTestEnde(LocalDate testEnde) {
      this.testEnde = testEnde;
   }

   public UUID getUuid() {
      return uuid;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
       if (this == obj) {
           return true;
       }
       if (obj == null) {
           return false;
       }
       if (getClass() != obj.getClass()) {
           return false;
       }
      Test other = (Test) obj;
       if (uuid == null) {
           return other.uuid == null;
       } else {
           return uuid.equals(other.uuid);
       }
   }

   @Override
   public String toString() {
      return "Test [uuid=" + uuid + ", testName=" + testName + ", testStart=" + testStart +
          ", testEnde=" + testEnde
          + ", fragen=" + fragen + "]";
   }

}
