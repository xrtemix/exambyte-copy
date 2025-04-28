package exambyte.application.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import exambyte.application.applicationService.TestService;
import exambyte.application.config.SecurityConfig;
import exambyte.application.helper.TestObjectGenerator;
import exambyte.application.helper.WithMockOAuth2User;


@WebMvcTest(KorrektorController.class)
@Import(SecurityConfig.class)
public class KorrektorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    TestService testService;

    List<exambyte.application.domainModel.Test> dummyTests = TestObjectGenerator.generateDummyTests();


    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die Index-Seite für Korrektor*innen ist mit entsprechender Berechtigung erreichbar.")
    void test_01() throws Exception {
        mvc.perform(get("/korrektor"))
           .andExpect(status().isOk())
           .andExpect(view().name("korrektor/korrektor-index"));
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("Die Index-Seite für Korrektor*innen ist OHNE entsprechende Berechtigung NICHT erreichbar.")
    void test_02() throws Exception {
        mvc.perform(get("/korrektor"))
           .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Auf der Index-Seite für Korrektor*innen wird der Benutzername angezeigt.")
    void test_03() throws Exception {
        String response = mvc.perform(get("/korrektor"))
                             .andExpect(model().attribute("username", "username"))
                             .andReturn().getResponse().getContentAsString();
        Document page = Jsoup.parse(response);
        String h1 = page.select("h1").text();
        assertThat(h1).contains("username");
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Auf der Index-Seite für Korrektor*innen wird eine Liste mit noch offenen Tests angezeigt.")
    void test_04() throws Exception {
        when(testService.findTestsThatNeedCorrection()).thenReturn(dummyTests);
        when(testService.getNumbersOfStudentsWhoNeedCorrection()).thenReturn(List.of(3, 5));
        when(testService.getNumbersOfFragenStillToCorrect()).thenReturn(List.of(7, 12));
        List<String> expectedNames = List.of(
            dummyTests.get(0).getTestName(), dummyTests.get(1).getTestName()
        );

        String response = mvc.perform(get("/korrektor"))
                             .andExpect(model().attribute("tests", dummyTests))
                             .andReturn().getResponse().getContentAsString();

        Document page = Jsoup.parse(response);
        List<String> testNames = page.select("table tbody tr td a").eachText();

        verify(testService).findTestsThatNeedCorrection();
        assertThat(testNames).containsAll(expectedNames);
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Zu jedem Test wird angezeigt, von wie vielen Studierenden noch wie viele Fragen offen sind.")
    void test_09() throws Exception {
        List<Integer> expectedStudentNumbers = List.of(3, 5);
        List<Integer> expectedFrageNumbers = List.of(7, 12);
        when(testService.findTestsThatNeedCorrection()).thenReturn(dummyTests);
        when(testService.getNumbersOfStudentsWhoNeedCorrection()).thenReturn(expectedStudentNumbers);
        when(testService.getNumbersOfFragenStillToCorrect()).thenReturn(expectedFrageNumbers);

        String response = mvc.perform(get("/korrektor"))
                             .andExpect(model().attribute("studentNumbers", expectedStudentNumbers))
                             .andExpect(model().attribute("frageNumbers", expectedFrageNumbers))
                             .andReturn().getResponse().getContentAsString();

        Document page = Jsoup.parse(response);
        List<Integer> studentNumbers = page.select("table tbody tr td span#studentNumbers").eachText().stream()
                                           .mapToInt(Integer::parseInt).boxed().toList();
        List<Integer> frageNumbers = page.select("table tbody tr td span#frageNumbers").eachText().stream()
                                         .mapToInt(Integer::parseInt).boxed().toList();

        verify(testService).getNumbersOfStudentsWhoNeedCorrection();
        verify(testService).getNumbersOfFragenStillToCorrect();
        assertThat(studentNumbers).containsAll(expectedStudentNumbers);
        assertThat(frageNumbers).containsAll(expectedFrageNumbers);
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die Select-Student-Seite ist für Korrektor*innen erreichbar.")
    void test_05() throws Exception {
        mvc.perform(get("/korrektor/select-student"))
           .andExpect(status().isOk())
           .andExpect(view().name("korrektor/select-student"));
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("Die Select-Student-Seite ist OHNE entsprechende Berechtigung NICHT erreichbar.")
    void test_06() throws Exception {
        mvc.perform(get("/korrektor/select-student"))
           .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(roles = "KORREKTOR")
    @DisplayName("Die Correction-Detail-Seite ist für Korrektor*innen erreichbar.")
    void test_07() throws Exception {
        mvc.perform(get("/korrektor/correction-detail"))
           .andExpect(status().isOk())
           .andExpect(view().name("korrektor/correction-detail"));
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("Die Correction-Detail-Seite ist OHNE entsprechende Berechtigung NICHT erreichbar.")
    void test_08() throws Exception {
        mvc.perform(get("/korrektor/correction-detail"))
           .andExpect(status().isForbidden());
    }

}
