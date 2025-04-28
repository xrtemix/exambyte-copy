package exambyte.application.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import exambyte.application.ExamByteApplication;
import exambyte.application.applicationService.StudentService;
import exambyte.application.applicationService.TestService;
import exambyte.application.config.SecurityConfig;
import exambyte.application.helper.TestObjectGenerator;
import exambyte.application.helper.WithMockOAuth2User;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {ExamByteApplication.class, SecurityConfig.class})
public class StudentControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    TestService testService;
    @MockitoBean
    StudentService studentService;

    List<exambyte.application.domainModel.Test> dummyTests = TestObjectGenerator.generateDummyTests();


    // studierende
    @Test
    @WithMockOAuth2User
    @DisplayName("Die Index-Seite für Studierende ist erreichbar.")
    void test_01() throws Exception {
        mvc.perform(get("/studierende"))
           .andExpect(status().isOk())
           .andExpect(view().name("studierende/student-index"));
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("Auf der Index-Seite für Studierende wird der Name des Benutzers angezeigt.")
    void test_02() throws Exception {
        String response = mvc.perform(get("/studierende"))
                             .andExpect(model().attribute("username", "username"))
                             .andReturn().getResponse().getContentAsString();
        Document page = Jsoup.parse(response);
        String h1 = page.select("h1").text();
        assertThat(h1).contains("username");
    }

    // @ParameterizedTest
    // @WithMockOAuth2User
    // @DisplayName("Der Zulassungsstatus wird auf der Index-Seite korrekt angezeigt.")
    // @CsvSource({
    //     "green, .alert.alert-success i.bi.bi-check-circle, .alert.alert-success span",
    //     "yellow, .alert.alert-warning i.bi.bi-slash-circle, .alert.alert-warning span",
    //     "red, .alert.alert-danger i.bi.bi-exclamation-circle, .alert.alert-danger span",
    //     "no admission, .alert.alert-danger i.bi.bi-x-circle, .alert.alert-danger span"
    // })
    // void test_03(boolean status, String symbolSelector, String textSelector) throws Exception {
    //     when(studentService.getHatZulassung(any())).thenReturn(status);

    //     String response = mvc.perform(get("/studierende"))
    //                         .andExpect(model().attribute("admissionStatus", status))
    //                         .andReturn().getResponse().getContentAsString();

    //     Document page = Jsoup.parse(response);
    //     Elements admissionSymbol = page.select(symbolSelector);
    //     String admissionText = page.select(textSelector).text();

    //     verify(studentService).getHatZulassung(any());
    //     assertThat(admissionSymbol).isNotEmpty();
    //     assertThat(admissionText).isNotBlank();
    // }

    @Test
    @WithMockOAuth2User
    @DisplayName("Auf der Index-Seite für Studierende wird eine Liste mit verfügbaren Tests angezeigt.")
    void test_04() throws Exception {
        when(testService.getTestsByUser(any())).thenReturn(dummyTests);
        List<String> expectedContent = List.of(
            dummyTests.getFirst().getTestName(), dummyTests.get(1).getTestName(),
            dummyTests.getFirst().getTestEnde().toString(), dummyTests.get(1).getTestEnde().toString()
        );

        String response = mvc.perform(get("/studierende"))
                             .andExpect(model().attribute("tests", dummyTests))
                             .andReturn().getResponse().getContentAsString();

        Document page = Jsoup.parse(response);
        String table = page.select("table tbody").text();

        verify(testService).getTestsByUser(any());
        assertThat(table).contains(expectedContent);
    }


    // studierende/test/{testName}
    @Test
    @WithMockOAuth2User
    @DisplayName("Die Test-Detail-Seite für Studierende ist erreichbar.")
    void test_05() throws Exception {
        when(testService.getTestByName("Woche 1")).thenReturn(dummyTests.getFirst());
        mvc.perform(get("/studierende/test/{testName}/{frageName}", "Woche 1", "Motivation"))
           .andExpect(status().isOk())
           .andExpect(view().name("studierende/test-detail"));
    }

    @Test
    @WithMockOAuth2User
    @DisplayName("Auf der Test-Detail-Seite funktionieren die Links zu den Fragen korrekt.")
    void test_06() throws Exception {
        List<String> expectedHrefs = List.of(
            "/studierende/test/Test%20Woche%201/Motivation", 
            "/studierende/test/Test%20Woche%201/Docker"
        );
        when(testService.getTestByName(dummyTests.getFirst().getTestName())).thenReturn(dummyTests.getFirst());

        String response = mvc.perform(get(
                    "/studierende/test/{testName}/{frageName}", 
                    dummyTests.getFirst().getTestName(), dummyTests.getFirst().getFragen().getFirst().getTitel()
                ))
                .andReturn().getResponse().getContentAsString();

        Document page = Jsoup.parse(response);
        List<String> hrefs = page.select(".list-group a").eachAttr("href");

        verify(testService).getTestByName(dummyTests.getFirst().getTestName());
        assertThat(hrefs).isEqualTo(expectedHrefs);
    }

}
