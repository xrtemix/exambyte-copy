package exambyte.application.persistenz.Repositorys;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import exambyte.application.persistenz.DTOs.TestDto;
import org.springframework.data.repository.CrudRepository;

public interface TestDataRepository extends CrudRepository<TestDto, Integer> {

   TestDto save(TestDto test);

   // Finden
   Optional<TestDto> getTestByTestName(String testName);

   Optional<TestDto> getTestByUuid(UUID id);

   List<TestDto> findAll();

   // Löschen
   boolean removeTestByTestName(String testName);

   boolean removeTestByUuid(UUID id);
}
