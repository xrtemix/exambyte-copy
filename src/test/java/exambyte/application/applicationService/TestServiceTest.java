package exambyte.application.applicationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import exambyte.application.domainService.TestRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class TestServiceTest {

   private final TestRepository testRepository = mock(TestRepository.class);
   private final TestService testService = new TestService(testRepository);
   List<exambyte.application.domainModel.Test> tests = List.of(
       new exambyte.application.domainModel.Test("Test 1"),
       new exambyte.application.domainModel.Test("Test 2"),
       new exambyte.application.domainModel.Test("Test 3")
   );

   @Test
   @DisplayName("save funktioniert")
   void test_1() {
      // Arrange 
      when(testRepository.save(tests.getFirst())).thenReturn(tests.getFirst());
      // Act
      testService.save(tests.getFirst());
      // Assert 
      verify(testRepository).save(tests.getFirst());
   }

   @Test
   @DisplayName("getTestByName funktioniert")
   void test_2() {
      // Arrange
      when(testRepository.getTestByName(tests.get(1).getTestName())).thenReturn(tests.get(1));
      // Act
      exambyte.application.domainModel.Test result =
          testService.getTestByName(tests.get(1).getTestName());
      // Assert
      verify(testRepository).getTestByName(tests.get(1).getTestName());
      assertThat(result).isEqualTo(tests.get(1));
   }

   @Test
   @DisplayName("findAll funktioniert")
   void test_3() {
      // Arrange
      when(testRepository.findAll()).thenReturn(List.of(tests.get(0), tests.get(2)));
      // Act
      List<exambyte.application.domainModel.Test> result = testService.findAll();
      // Assert
      verify(testRepository).findAll();
      assertThat(result).contains(tests.get(0), tests.get(2));
   }

   @Test
   @DisplayName("removeTest funktioniert")
   void test_4() {
      // Arrange
      when(testRepository.removeTestByTestName(tests.get(2).getTestName())).thenReturn(true);
      // Act
      boolean result = testService.removeTestByTestName(tests.get(2).getTestName());
      // Assert
      verify(testRepository).removeTestByTestName(tests.get(2).getTestName());
      assertThat(result).isTrue();
   }

   @Test
   @DisplayName("findAll funktioniert")
   void test_5() {
      // Arrange
      when(testRepository.save(tests.getFirst())).thenReturn(tests.getFirst());
      testRepository.save(tests.getFirst());
      when(testService.findAll()).thenReturn(List.of(tests.getFirst()));
      // Act
      List<exambyte.application.domainModel.Test> result = testService.findAll();
      // Assert
      assertThat(result).contains(tests.getFirst());
   }


}
