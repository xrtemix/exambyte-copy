package exambyte.application.applicationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import exambyte.application.domainModel.Student;
import exambyte.application.domainService.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceTest {
   private final StudentRepository studentRepository = mock(StudentRepository.class);
   private final StudentService studentService = new StudentService(studentRepository);

   List<Student> studenten = List.of(
       new Student(5, 123, "Max"),
       new Student(1, 456, "Moritz"),
       new Student(2, 789, "Hans"));

   @Test
   @DisplayName("getHatZulassung() funktioniert")
   void test_1() {
      // Arrange
      when(studentRepository.getHatZulassung(1)).thenReturn(true);
      studentService.setHatZulassung(1, false);
      // Act
      boolean hatZulassung = studentService.getHatZulassung(1);
      // Assert
      assertThat(hatZulassung).isTrue();
   }

   @Test
   @DisplayName("save() funktioniert")
   void test_2() {
      // Arrange
      when(studentRepository.save(studenten.getFirst())).thenReturn(studenten.getFirst());
      // Act
      Student saved = studentService.save(studenten.getFirst());
      // Assert
      assertThat(saved).isEqualTo(studenten.getFirst());
   }

   @Test
   @DisplayName("getStudentByName() funktioniert")
   void test_3() {
      // Arrange
      when(studentRepository.getStudentByName("Moritz")).thenReturn(studenten.get(1));
      // Act
      Student saved = studentService.getStudentByName("Moritz");
      // Assert
      assertThat(saved).isEqualTo(studenten.get(1));
   }

   @Test
   @DisplayName("getStudentByGithubId() funktioniert")
   void test_4() {
      // Arrange
      when(studentRepository.getStudentByGithubId(3)).thenReturn(studenten.get(2));
      // Act
      Student saved = studentService.getStudentByGithubId(3);
      // Assert
      assertThat(saved).isEqualTo(studenten.get(2));
   }

   @Test
   @DisplayName("getStudentByMatrikelnummer() funktioniert")
   void test_5() {
      // Arrange
      when(studentRepository.getStudentByMatrikelnummer(789)).thenReturn(studenten.get(2));
      // Act
      Student saved = studentService.getStudentByMatrikelnummer(789);
      // Assert
      assertThat(saved).isEqualTo(studenten.get(2));
   }


}
