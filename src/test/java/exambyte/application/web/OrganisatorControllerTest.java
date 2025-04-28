package exambyte.application.web;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import exambyte.application.applicationService.TestService;
import exambyte.application.config.MethodSecurityConfig;
import exambyte.application.config.SecurityConfig;
import exambyte.application.domainModel.Frage;
import exambyte.application.domainModel.FreiTextFrage;
import exambyte.application.domainModel.Test;
import exambyte.application.helper.WithMockOAuth2User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

@WebMvcTest(OrganisatorController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class OrganisatorControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockitoBean
  private TestService testService;

  @org.junit.jupiter.api.Test
  public void test_21() {
    TestService testService1 = mock(TestService.class);
    OrganisatorController oc = new OrganisatorController(testService1);
    Model model = mock(Model.class);
    Test test = mock(Test.class);
    doNothing().when(testService1).save(any());
    oc.saveTestWithGivenNameButton(model, "name");
    verify(testService1).save(any());
  }

  @org.junit.jupiter.api.Test
  @WithAnonymousUser
  public void test_00() throws Exception {
    List<String> GetEndpoints = new ArrayList<>(List.of(
        "/uebersicht", "/createNewTest",
        "/fragenTest", "/settingsTest"));
    List<String> PostEndpoints = new ArrayList<>(List.of(
        "/newTest", "/settingsTest",
        "/createNewQuestion", "/saveTheFreetextQuestion",
        "/saveTheMcQuestion"));

    GetEndpoints.forEach(endpoint ->
    {
      try {
        mvc.perform(get("/organisator" + endpoint))
            .andExpect(status().is3xxRedirection());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    PostEndpoints.forEach(endpoint ->
    {
      try {
        mvc.perform(post("/organisator" + endpoint).with(csrf()))
            .andExpect(status().is3xxRedirection());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }


  @org.junit.jupiter.api.Test
  @WithMockOAuth2User()
  @DisplayName("GET auf '/uebersicht' geht nicht ohne Rolle")
  public void test_01() throws Exception {
    List<exambyte.application.domainModel.Test> tests = List.of(
        new Test("test1"),
        new Test("test2")
    );
    when(testService.findAll()).thenReturn(tests);
    mvc.perform(get("/organisator/uebersicht"))
        .andExpect(status().isForbidden());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("GET auf '/uebersicht' geht mit Rolle")
  public void test_01b() throws Exception {
    List<exambyte.application.domainModel.Test> tests = List.of(
        new Test("test1"),
        new Test("test2")
    );
    when(testService.findAll()).thenReturn(tests);
    mvc.perform(get("/organisator/uebersicht"))
        .andExpect(status().isOk())
        .andExpect(view().name("organisatoren/index"))
        .andExpect(model().attribute("testsDatabase", tests));
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User
  @DisplayName("GET auf '/createNewTest' geht nicht ohne Rolle")
  public void test_02() throws Exception {
    mvc.perform(get("/organisator/createNewTest"))
        .andExpect(status().isForbidden());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("GET auf '/createNewTest' geht mit Rolle")
  public void test_02b() throws Exception {
    mvc.perform(get("/organisator/createNewTest"))
        .andExpect(status().isOk())
        .andExpect(view().name("organisatoren/new-test"));
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User
  @DisplayName("GET auf 'newTest' geht nicht ohne Rolle")
  public void test_03() throws Exception {
    Test test = new Test("test_01");
    mvc.perform(post("/organisator/newTest")
            .param("testName", "test_01")
            .with(csrf()))
        .andExpect(status().isForbidden());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("GET auf 'newTest' geht mit Rolle")
  public void test_03b() throws Exception {
    Test test = new Test("test_01");
    mvc.perform(post("/organisator/newTest")
            .param("testName", "test_01")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("testObject"))
        .andExpect(model().attribute("testObject",
            hasProperty("testName", equalTo("test_01"))))
        .andExpect(view().name("organisatoren/test-settings"));
    verify(testService).save(any());
  }


  @org.junit.jupiter.api.Test
  @WithMockOAuth2User
  @DisplayName("POST auf 'settingsTest' geht nicht ohne Rolle")
  public void test_04() throws Exception {
    Frage frage = mock(FreiTextFrage.class);
    Test test = new Test("test_old", LocalDate.parse("2004-04-05"),
        LocalDate.parse("2005-05-05"),
        List.of(frage));
    when(testService.getTestByName("test_old")).thenReturn(test);

    mvc.perform(post("/organisator/settingsTest")
            .param("successFlag", "true")
            .param("testTitelOld", "test_old")
            .param("testTitel", "test_new")
            .param("testStart", "2002-02-03")
            .param("testEnde", "2003-03-04")
            .with(csrf()))
        .andExpect(status().isForbidden());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("POST auf 'settingsTest' geht mit Rolle")
  public void test_04b() throws Exception {
    Frage frage = mock(FreiTextFrage.class);
    Test test = new Test("test_old", LocalDate.parse("2004-04-05"),
        LocalDate.parse("2005-05-05"),
        List.of(frage));
    when(testService.getTestByName("test_old")).thenReturn(test);

    mvc.perform(post("/organisator/settingsTest")
            .param("successFlag", "true")
            .param("testTitelOld", "test_old")
            .param("testTitel", "test_new")
            .param("testStart", "2002-02-03")
            .param("testEnde", "2003-03-04")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("testObject"))
        .andExpect(view().name("organisatoren/test-settings"))
        .andExpect(model().attribute("successFlag", true))

        .andExpect(model().attribute("testObject",
            hasProperty("testName", equalTo("test_new"))))

        .andExpect(model().attribute("testObject",
            hasProperty("testStart", equalTo(LocalDate.parse("2002-02-03")))))

        .andExpect(model().attribute("testObject",
            hasProperty("testEnde", equalTo(LocalDate.parse("2003-03-04")))));
    verify(testService).save(any());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User
  @DisplayName("GET auf 'fragenTest' geht nicht ohne Rolle")
  public void test_05() throws Exception {
    mvc.perform(get("/organisator/fragenTest").param("testName", "testName"))
        .andExpect(status().isForbidden());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("GET auf 'fragenTest' geht mit Rolle")
  public void test_05b() throws Exception {
    String testName = "test_01";
    Test test = mock(Test.class);
    when(testService.getTestByName(any())).thenReturn(test);

    mvc.perform(get("/organisator/fragenTest").param("testName", testName))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("testObject"))
        .andExpect(model().attribute("testObject", test))
        .andExpect(view().name("organisatoren/test-fragen"));
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User
  @DisplayName("POST auf 'createNewQuestion' geht nicht ohne Rolle")
  public void test_06() throws Exception {
    mvc.perform(post("/organisator/createNewQuestion")
            .param("questionType", "mc")
            .param("testName", "testName")
            .with(csrf()))
        .andExpect(status().isForbidden());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("POST auf 'createNewQuestion' mit einer MC-Frage geht mit Rolle")
  public void test_06b() throws Exception {
    Test test = mock(Test.class);
    when(testService.getTestByName("testName")).thenReturn(test);

    mvc.perform(post("/organisator/createNewQuestion")
            .param("questionType", "mc")
            .param("testName", "testName")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(model().attribute("testObject", test))
//        .andExpect(model().attribute("testObject",
//            hasProperty("testName", equalTo("testName"))))
        .andExpect(view().name("organisatoren/test-new-mc"));

    verify(testService).getTestByName(any());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("POST auf 'createNewQuestion' mit einer FT-Frage geht mit Rolle")
  public void test_06c() throws Exception {
    Test test = mock(Test.class);
    when(testService.getTestByName("testName")).thenReturn(test);

    mvc.perform(post("/organisator/createNewQuestion")
            .param("questionType", "ahahah")
            .param("testName", "testName")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(model().attribute("testObject", test))
//              .andExpect(model().attribute("testObject", hasProperty("testName", equalTo("testName"))))
        .andExpect(view().name("organisatoren/test-new-freetext"));

    verify(testService).getTestByName(any());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User
  @DisplayName("POST auf 'saveTheFreetextQuestion' geht nicht ohne Rolle")
  public void test_08() throws Exception {
    mvc.perform(post("/organisator/saveTheFreetextQuestion")
            .param("testName", "testName")
            .param("titel", "titel")
            .param("fragestellung", "fragestellung")
            .param("maxPunkte", "23")
            .param("korrekteLoesung", "korrekteLoesung")
            .with(csrf()))
        .andExpect(status().isForbidden());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("POST auf 'saveTheFreetextQuestion' geht mit Rolle")
  public void test_08b() throws Exception {
    Test test = new Test("testName",
        LocalDate.parse("2002-02-02"),
        LocalDate.parse("2003-03-03"),
        List.of(
            new FreiTextFrage("fragestellung1", "titel1", 20.0, "Loesung1")));
    when(testService.getTestByName(any())).thenReturn(test);

    mvc.perform(post("/organisator/saveTheFreetextQuestion")
            .param("testName", "testName")
            .param("titel", "titel")
            .param("fragestellung", "fragestellung")
            .param("maxPunkte", "23")
            .param("korrekteLoesung", "korrekteLoesung")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("organisatoren/test-fragen"))
        .andExpect(model().attribute("testObject",
            hasProperty("testName", equalTo("testName"))))
        .andExpect(
            model().attribute("testObject", hasProperty("fragen", hasSize(2))));

    verify(testService).save(any());
  }

  @org.junit.jupiter.api.Test
  @WithMockOAuth2User(roles = "ORGANISATOR")
  @DisplayName("POST auf 'saveTheFreetextQuestion' mit Antwortmöglichkeiten geht mit Rolle")
  public void test_08c() throws Exception {
    Test test = new Test("testName",
        LocalDate.parse("2002-02-02"),
        LocalDate.parse("2003-03-03"),
        List.of(
            new FreiTextFrage("fragestellung1", "titel1", 20.0, "Loesung1")));
    when(testService.getTestByName(any())).thenReturn(test);


    mvc.perform(post("/organisator/saveTheFreetextQuestion")
            .param("testName", "testName")
            .param("titel", "titel")
            .param("fragestellung", "fragestellung")
            .param("antwortMoeglichkeiten", "FirstAnt")
            .param("antwortMoeglichkeiten", "SecondAnt")
            .param("antwortMoeglichkeiten", "ThirdAnt")
            .param("korrekteLoesung", "ThirdAnt")
            .param("maxPunkte", "23")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("organisatoren/test-fragen"))
        .andExpect(model().attribute("testObject",
            hasProperty("testName", equalTo("testName"))))
        .andExpect(
            model().attribute("testObject", hasProperty("fragen", hasSize(2))));

    verify(testService).save(any());
  }

  //TODO: gleicher Test wie test_08c?
//  @org.junit.jupiter.api.Test
//  public void test_09() throws Exception {
//    Test test = new Test("testName",
//        LocalDate.parse("2002-02-02"),
//        LocalDate.parse("2003-03-03"),
//        List.of(
//            new FreiTextFrage("fragestellung1", "titel1", 20.0, "Loesung1")));
//    when(testService.getTestByName(any())).thenReturn(test);
//
//
//    mvc.perform(post("/organisator/saveTheFreetextQuestion")
//            .param("testName", "testName")
//            .param("titel", "titel")
//            .param("fragestellung", "fragestellung")
//            .param("antwortMoeglichkeiten", "FirstAnt")
//            .param("antwortMoeglichkeiten", "SecondAnt")
//            .param("antwortMoeglichkeiten", "ThirdAnt")
//            .param("korrekteLoesung", "ThirdAnt")
//            .param("maxPunkte", "23")
//            .with(csrf()))
//        .andExpect(status().isOk())
//        .andExpect(view().name("organisatoren/test-fragen"))
//        .andExpect(model().attribute("testObject",
//            hasProperty("testName", equalTo("testName"))))
//        .andExpect(
//            model().attribute("testObject", hasProperty("fragen", hasSize(2))));
//
//    verify(testService).save(any());
//  }

}
