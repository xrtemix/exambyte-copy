package exambyte.application.web;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import exambyte.application.applicationService.TestService;
import exambyte.application.domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exambyte.application.web.updaters.FreeTextQuestionUpdater;
import exambyte.application.web.updaters.McQuestionUpdater;
import exambyte.application.web.updaters.TestSettingsUpdater;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SuppressFBWarnings("EI_EXPOSE_REP2")
@Controller
@RequestMapping("/organisator")
@Secured("ROLE_ORGANISATOR")
public class OrganisatorController {

    private final TestService testService;

    public OrganisatorController(TestService testService) {
        this.testService = testService;
    }


    @GetMapping("/uebersicht")
    public String index(Model model) {
        List<Test> tests = testService.findAll();
        model.addAttribute("testsDatabase", tests);
        return "organisatoren/index";
    }

    @GetMapping("/createNewTest")
    public String goToEnteringYourTestNameAndSavingIt(Model model) {
        return "organisatoren/new-test";
    }

    // TODO: Templates und Methodennamen abgleichen
    // TODO: Request auf createNewTest ändern

    @PostMapping("/newTest")
    public String saveTestWithGivenNameButton(Model model,
                                              @RequestParam("testName") String testName) {
        Test test = new Test(testName);
        testService.save(test);
        model.addAttribute("testObject", test);
        return "organisatoren/test-settings";
    }

    @GetMapping("/{testName}")
    public String goToExistingTest(Model model, @PathVariable String testName) {
        Test test = testService.getTestByName(testName);
        model.addAttribute("testObject", test);
        return "organisatoren/test-settings";
    }

    @PostMapping("/settingsTest")
    public String changeTestSettings(Model model,
                                     @ModelAttribute TestSettingsUpdater testSettingsUpdater) {
        Test test = testService.getTestByName(testSettingsUpdater.getTestTitelOld());
        if (testSettingsUpdater.nameShouldBeChanged()) {
            test.setTestName(testSettingsUpdater.getTestTitel());
        }
        if (testSettingsUpdater.startDateShouldBeChanged()) {
            test.setTestStart(LocalDate.parse(testSettingsUpdater.getTestStart()));
        }
        if (testSettingsUpdater.endeDateShouldBeChanged()) {
            test.setTestEnde(LocalDate.parse(testSettingsUpdater.getTestEnde()));
        }
        testService.save(test);

        model.addAttribute("successFlag", testSettingsUpdater.isSuccessFlag());
        model.addAttribute("testObject", test);
        return "organisatoren/test-settings";
    }

    @GetMapping("/fragenTest")
    public String getFragen(Model model,
                            @RequestParam("testName") String testName) {
        Test test = testService.getTestByName(testName);
        model.addAttribute("testObject", test);
        return "organisatoren/test-fragen";
    }

    @GetMapping("/settingsTest")
    public String getTestSettings(Model model,
                                  @RequestParam("testName") String testName) {
        Test test = testService.getTestByName(testName);
        model.addAttribute("testObject", test);
        return "organisatoren/test-settings";
    }

    @PostMapping("/createNewQuestion")
    public String createNewQuestion(Model model,
                                    @RequestParam("questionType") String testType,
                                    @RequestParam("testName") String testName) {
        System.out.println("createNewQuestion:start");
        Test test = testService.getTestByName(testName);
        model.addAttribute("testObject", test);
        if (testType.equals("mc")) {
            return "organisatoren/test-new-mc";
        }
        return "organisatoren/test-new-freetext";
    }

    @PostMapping("/saveTheFreetextQuestion")
    public String saveTheFreetextQuestion(Model model,
                                          @ModelAttribute
                                          FreeTextQuestionUpdater freeTextQuestionUpdater) {

        FreiTextFrage frage = new FreiTextFrage(
                freeTextQuestionUpdater.fragestellung(),
                freeTextQuestionUpdater.titel(),
                freeTextQuestionUpdater.maxPunkte(),
                freeTextQuestionUpdater.korrekteLoesung());

        Test test = testService.getTestByName(freeTextQuestionUpdater.testName());

        // Create a mutable copy of the existing questions
        List<Frage> updatedFragen = new ArrayList<>(test.getFragen());
        updatedFragen.add(frage); // Add the new Frage

        // Create a new Test instance with the updated list of questions
        Test testCopy =
                new Test(test.getUuid(),
                        test.getTestName(),
                        test.getTestStart(),
                        test.getTestEnde(),
                        updatedFragen);

        testService.save(testCopy);

        model.addAttribute("testObject", testCopy);
        return "organisatoren/test-fragen";
    }


    @PostMapping("/saveTheMcQuestion")
    public String saveTheMcQuestion(Model model,
                                          @ModelAttribute
                                          McQuestionUpdater mcQuestionUpdater) {

        MultipleChoiceFrage frage = new MultipleChoiceFrage(
                mcQuestionUpdater.fragestellung(),
                mcQuestionUpdater.titel(),
                mcQuestionUpdater.antwortMoeglichkeiten(),
                mcQuestionUpdater.korrekteLoesung(),
                mcQuestionUpdater.maxPunkte()
        );

        Test test = testService.getTestByName(mcQuestionUpdater.testName());

        // Create a mutable copy of the existing questions
        List<Frage> updatedFragen = new ArrayList<>(test.getFragen());
        updatedFragen.add(frage); // Add the new Frage

        // Create a new Test instance with the updated list of questions
        Test testCopy =
                new Test(test.getUuid(),
                        test.getTestName(),
                        test.getTestStart(),
                        test.getTestEnde(),
                        updatedFragen);

        testService.save(testCopy);

        model.addAttribute("testObject", testCopy);
        return "organisatoren/test-fragen";
    }
}
