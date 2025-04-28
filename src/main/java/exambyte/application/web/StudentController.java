package exambyte.application.web;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import exambyte.application.applicationService.StudentService;
import exambyte.application.applicationService.TestService;
import exambyte.application.domainModel.Frage;
import exambyte.application.domainModel.Test;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@SuppressFBWarnings({"EI_EXPOSE_REP2", "EI_EXPOSE_REP2"})
@Controller
@RequestMapping("/studierende")
public class StudentController {

   private final TestService testService;
   private final StudentService studentService;

   public StudentController(TestService testService, StudentService studentService) {
      this.testService = testService;
      this.studentService = studentService;
   }


   @GetMapping
   public String index(Model model, @AuthenticationPrincipal OAuth2User user) {
      model.addAttribute("username", user.getAttribute("login"));
      List<Test> tests = testService.getTestsByUser(user);
      model.addAttribute("tests", tests);
      model.addAttribute("admissionStatus", "green");
      model.addAttribute("hatZulassung",
          studentService.getHatZulassung(user.getAttribute("id")));
      return "studierende/student-index";
   }

   @GetMapping("/test/{testName}/{frageName}")
   public String test(@PathVariable String testName, @PathVariable String frageName, Model model) {
      Test currentTest = testService.getTestByName(testName);
      Frage frage = currentTest.getFrageByName(frageName);
      model.addAttribute("frage", frage);
      model.addAttribute("test", currentTest);
      return "studierende/test-detail";
   }

//    @PostMapping("/test/{testName}/{frageName}")
//    public String test(@PathVariable String testName, @PathVariable String frageName, Model model, @AuthenticationPrincipal OAuth2User user) {
//        Test currentTest = testService.getTestByName(testName);
//        model.addAttribute("test", currentTest);
//        studentService.saveAnswer(user, testName, frageName);
//        return "studierende/test-detail";
//    }

}
