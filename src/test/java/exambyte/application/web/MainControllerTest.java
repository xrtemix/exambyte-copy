package exambyte.application.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import exambyte.application.ExamByteApplication;
import exambyte.application.config.SecurityConfig;
import exambyte.application.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(MainController.class)
@ContextConfiguration(classes = {ExamByteApplication.class, SecurityConfig.class})
public class MainControllerTest {

   @Autowired
   MockMvc mvc;

   @Test
   @DisplayName("Der Zugriff auf '/' ist erlaubt")
   void test_1() throws Exception {
      mvc.perform(get("/")).andExpect(status().isOk());
   }

   @Test
   @DisplayName("Der Zugriff auf '/login' ist erlaubt")
   void test_2() throws Exception {
      mvc.perform(get("/login")).andExpect(status().isOk());
   }

   @Test
   @DisplayName("Der Zugriff auf '/secured' leitet nicht-angemeldete Nutzer zu /login weiter")
   void test_3() throws Exception {
      mvc.perform(get("/secured"))
          .andExpect(status().is3xxRedirection())
          .andExpect(redirectedUrl("http://localhost/login"));
   }

   @Test
   @WithMockOAuth2User
   @DisplayName("Der Zugriff auf '/secured' ist für angemeldete Nutzer erlaubt")
   void test_4() throws Exception {
      mvc.perform(get("/secured")).andExpect(status().is3xxRedirection())
          .andExpect(redirectedUrl("/"));
   }


}
