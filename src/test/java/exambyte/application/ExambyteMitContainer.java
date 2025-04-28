package exambyte.application;
import exambyte.config.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;


public class ExambyteMitContainer {

   public static void main(String[] args) {
      SpringApplication.from(ExamByteApplication::main)
          .with(TestcontainersConfiguration.class)
          .run(args);
   }
}
