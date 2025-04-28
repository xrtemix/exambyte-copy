package exambyte.application.web;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import exambyte.application.applicationService.TestService;


@SuppressFBWarnings("EI_EXPOSE_REP2")
@Controller
@RequestMapping("/korrektor")
@Secured("ROLE_KORREKTOR")
public class KorrektorController {

    private final TestService testService;

    public KorrektorController(TestService testService) {
        this.testService = testService;
    }


    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("username", user.getAttribute("login"));
        model.addAttribute("tests", testService.findTestsThatNeedCorrection());
        model.addAttribute("studentNumbers", testService.getNumbersOfStudentsWhoNeedCorrection());
        model.addAttribute("frageNumbers", testService.getNumbersOfFragenStillToCorrect());
        return "korrektor/korrektor-index";
    }

    @GetMapping("/select-student")
    public String selectStudent() {
        return "korrektor/select-student";
    }

    @GetMapping("/correction-detail")
    public String correctionDetail() {
        return "korrektor/correction-detail";
    }

}
