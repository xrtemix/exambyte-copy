package exambyte.application.anwendung;

import exambyte.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class ExamByteApplicationTests {

	@Test
	void contextLoads() {
	}

}
