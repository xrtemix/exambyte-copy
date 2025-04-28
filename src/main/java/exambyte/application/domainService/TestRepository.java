package exambyte.application.domainService;

import exambyte.application.domainModel.Test;
import java.util.List;
import java.util.UUID;

public interface TestRepository {

   Test save(Test test);

   // Finden
   Test getTestByName(String testName);

   List<Test> findAll();

   Test getTestByUuid(UUID id);

   // Löschen
   boolean removeTestByTestName(String testName);

   boolean removeTestByIUuid(UUID id);
}
