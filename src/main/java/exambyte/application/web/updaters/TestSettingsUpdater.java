package exambyte.application.web.updaters;

public class TestSettingsUpdater {
   private boolean successFlag;
   private String testTitelOld;
   private String testTitel;
   private String testStart;
   private String testEnde;


   public boolean isSuccessFlag() {
      return successFlag;
   }

   public void setSuccessFlag(boolean successFlag) {
      this.successFlag = successFlag;
   }

   public String getTestTitelOld() {
      return testTitelOld;
   }

   public void setTestTitelOld(String testTitelOld) {
      this.testTitelOld = testTitelOld;
   }

   public String getTestTitel() {
      return testTitel;
   }

   public void setTestTitel(String testTitel) {
      this.testTitel = testTitel;
   }

   public String getTestStart() {
      return testStart;
   }

   public void setTestStart(String testStart) {
      this.testStart = testStart;
   }

   public String getTestEnde() {
      return testEnde;
   }

   public void setTestEnde(String testEnde) {
      this.testEnde = testEnde;
   }

   public boolean nameShouldBeChanged() {
      return !testTitel.isBlank();
   }

   public boolean startDateShouldBeChanged() {
      return !testStart.isBlank();
   }

   public boolean endeDateShouldBeChanged() {
      return !testEnde.isBlank();
   }

}
