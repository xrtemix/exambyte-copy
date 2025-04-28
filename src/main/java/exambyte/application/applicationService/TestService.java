package exambyte.application.applicationService;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import exambyte.application.domainModel.Test;
import exambyte.application.domainService.TestRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@SuppressFBWarnings("EI_EXPOSE_REP2")
@Service
// Dient als Schnittstelle zwischen Web und DomainService (insbesondere OrganisatorController und TestRepositoryImpl)
public class TestService {
   private final TestRepository testRepository;

   public TestService(TestRepository testRepository) {
      this.testRepository = testRepository;
   }

   public void save(Test test) {
      testRepository.save(test);
   }

   public Test getTestByName(String testName) {
      return testRepository.getTestByName(testName);
   }

   public Test getTestById(UUID id) {
      return testRepository.getTestByUuid(id);
   }

   public List<Test> findAll() {
      return testRepository.findAll();
   }

   public boolean removeTestByTestName(String testName) {
      return testRepository.removeTestByTestName(testName);
   }

   public boolean removeTestById(UUID id) {
      return testRepository.removeTestByIUuid(id);
   }

   public List<Test> getTestsByUser(OAuth2User user) {
      return testRepository.findAll();
   }

   public List<Test> findTestsThatNeedCorrection() {
      // TODO: Methode muss noch implementiert werden 

      // Diese Methode soll eine Liste mit denjenigen Tests zurückgeben, zu denen noch Fragen korrigiert werden müssen 
      // Methode gibt aktuell Dummy-Daten zurück 

      return List.of(
         new Test("Test Woche 1"), new Test("Test Woche 2")
      );
   }

   public List<Integer> getNumbersOfStudentsWhoNeedCorrection() {
      // TODO: Methode muss noch implementiert werden 

      // Diese Methode soll eine List<Integer> zurückgeben, die für jeden Test eine Anzahl enthält, 
      // wie viele Studierende noch mindestens eine Korrektur benötigen 
      // Methode gibt aktuell Dummy-Daten zurück 

      return List.of(2, 7);
   }

   public Object getNumbersOfFragenStillToCorrect() {
      // TODO: Methode muss noch implementiert werden 

      // Diese Methode soll eine List<Integer> zurückgeben, die für jeden Test eine Anzahl enthält, 
      // wie viele Fragen ingesamt noch korrigiert werden müssen 
      // Methode gibt aktuell Dummy-Daten zurück 

      return List.of(3, 15);
   }


}
