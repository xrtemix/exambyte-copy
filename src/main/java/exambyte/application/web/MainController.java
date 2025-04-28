package exambyte.application.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

   @GetMapping("/")
   public String index() {
      return "index";
   }

   @GetMapping("/login")
   public String login() {
      return "login";
   }

   @GetMapping("/secured")
   public String secured() {
      return "redirect:/";
   }

}
