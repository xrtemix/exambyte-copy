package exambyte.application.persistenz;

import static org.assertj.core.api.Assertions.assertThat;
import exambyte.application.applicationService.AntwortMoeglichkeitenService;
import exambyte.application.applicationService.FrageService;
import exambyte.application.applicationService.KorrekteLoesungenService;
import exambyte.application.domainModel.FreiTextFrage;
import exambyte.application.domainModel.MultipleChoiceFrage;
import exambyte.application.persistenz.Repositorys.FrageRepository;
import exambyte.application.persistenz.Repositorys.TestDataRepository;
import exambyte.config.TestcontainersConfiguration;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;


@Disabled
@DataJdbcTest
@Import(TestcontainersConfiguration.class)
public class TestDataRepositoryTest {
   @Autowired
   TestDataRepository testDataRepository;
   TestRepositoryImpl testRepository;
   FrageService frageService;
   FrageRepository frageRepository;
   AntwortMoeglichkeitenService antwortMoeglichkeitenService;
   KorrekteLoesungenService korrekteLoesungenService;

   @BeforeEach
   void setUp() {
      testRepository = new TestRepositoryImpl(testDataRepository, frageService, antwortMoeglichkeitenService, korrekteLoesungenService);
      frageService = new FrageService(frageRepository);
   }

   @Test
   @Sql(scripts = {"/exambyte/service/clear.sql", "/exambyte/service/init.sql"})
   @DisplayName("Ein Test kann gespeichert werden")
   void test_1() {
      exambyte.application.domainModel.Test test = new exambyte.application.domainModel.Test(
          "Test 1", LocalDate.now(), LocalDate.now().plusDays(1),
          List.of(new FreiTextFrage("fragestellung", "title", 1, "correctAnswer")));
      exambyte.application.domainModel.Test saved = testRepository.save(test);
      assertThat(saved).isEqualTo(test);
   }

   @Disabled("MultipleChoiceFrage hat noch kein correctAnswer-Feld")
   @Test
   @Sql(scripts = {"/exambyte/service/clear.sql", "/exambyte/service/init.sql"})
   @DisplayName("Ein Test kann gespeichert werden")
   void test_2() {
      exambyte.application.domainModel.Test test = new exambyte.application.domainModel.Test(
          "Test 1", LocalDate.now(), LocalDate.now().plusDays(1),
          List.of(new MultipleChoiceFrage("fragestellung", "title", List.of("answer1", "answer2"), List.of("answer1", "answer2"), 4)));
      exambyte.application.domainModel.Test saved = testRepository.save(test);
      assertThat(saved).isEqualTo(test);
   }


}
