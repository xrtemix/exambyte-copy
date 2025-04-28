package exambyte.application.persistenz;

import static org.assertj.core.api.Assertions.assertThat;

import exambyte.application.domainModel.Student;
import exambyte.application.persistenz.Repositorys.StudentDataRepository;
import exambyte.config.TestcontainersConfiguration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;

@Disabled
@DataJdbcTest
@Import(TestcontainersConfiguration.class)
public class StudentDataRepositoryTest {
   @Autowired
   StudentDataRepository studentDataRepository;
   StudentRepositoryImpl studentRepository;
   List<Student> studenten = List.of(
       new Student(0, 123, "Max"),
       new Student(1, 456, "Moritz"),
       new Student(2, 789, "Hans"));

   @BeforeEach
   void setUp() {
      studentRepository = new StudentRepositoryImpl(studentDataRepository);
   }

   @Test
   @DisplayName("save funktioniert")
   void test_1() {
      Student saved = studentRepository.save(studenten.getFirst());
      assertThat(saved).isEqualTo(studenten.getFirst());
   }

   @Test
   @DisplayName("getStudentByName funktioniert")
   void test_2() {
      studentRepository.save(studenten.get(0));
      studentRepository.save(studenten.get(1));
      Student student = studentRepository.getStudentByName("Max");
      assertThat(student).isEqualTo(studenten.getFirst());
   }

   @Test
   @DisplayName("getStudentByGithubId funktioniert")
   void test_3() {
      studentRepository.save(studenten.get(0));
      studentRepository.save(studenten.get(1));
      Student student = studentRepository.getStudentByGithubId(1);
      assertThat(student).isEqualTo(studenten.get(1));
   }

   @Test
   @DisplayName("getStudentByMatrikelnummer funktioniert")
   void test_4() {
      studentRepository.save(studenten.get(0));
      studentRepository.save(studenten.get(1));
      Student student = studentRepository.getStudentByMatrikelnummer(789);
      assertThat(student).isEqualTo(studenten.get(2));
   }

   @Test
   @DisplayName("getHatZulassung und setHatZulassung funktionieren")
   void test_5() {
      studentRepository.save(studenten.get(0));
      studenten.get(1).setHatZulassung(true);
      studentRepository.save(studenten.get(1));
      boolean hatZulassung = studentRepository.getHatZulassung(1);
      assertThat(hatZulassung).isTrue();
   }

}
