package exambyte.application.anwendung;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import exambyte.application.ExamByteApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@AnalyzeClasses(packagesOf = ExamByteApplication.class)
public class ArchitectureTest {


   /* Hinweis zur Architektur:
   Web/Database -> ApplicationService -> DomainService -> DomainModel
   */
   @Disabled
   @Test
   @DisplayName("Es wird die Onion-Architektur eingehalten")
   public void onion() throws Exception {
      ArchRule rule = onionArchitecture()
          .domainModels("exambyte.application.domainModel..")
          .domainServices("exambyte.application.domainService..")
          .applicationServices("exambyte.application.applicationService..")
          .adapter("web", "exambyte.application.web")
          .adapter("persistenz", "exambyte.application.persistenz..");
      rule.check(new ClassFileImporter().importPackagesOf(ExamByteApplication.class));
   }
}
