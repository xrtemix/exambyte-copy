package exambyte.application.helper;

import java.time.LocalDate;
import java.util.List;
import exambyte.application.domainModel.Frage;
import exambyte.application.domainModel.FreiTextFrage;
import exambyte.application.domainModel.MultipleChoiceFrage;
import exambyte.application.domainModel.Test;


public class TestObjectGenerator {

    public static List<Test> generateDummyTests() {

        Frage frage1 = new MultipleChoiceFrage(
            "Sind Sie bereit für den Test?",
            "Motivation",
            List.of("Ja.", "Nein."), 
            List.of("Ja."), 
            1
        );
        Frage frage2 = new FreiTextFrage(
            "Wie startet man einen Docker-Container?",
            "Docker",
            2,
            "Die Antwort."
        );

        Test testWoche1 = new Test(
            "Test Woche 1",
            LocalDate.of(2025, 1, 31),
            LocalDate.of(2026, 1, 31),
            List.of(frage1, frage2)
        );

        Frage frage3 = new MultipleChoiceFrage(
            "Spring Boot ist cool. Wahr oder Falsch?", 
            "Spring Boot", 
            List.of("Wahr.", "Falsch."), 
            List.of("Wahr."), 
            2
        );
        Frage frage4 = new FreiTextFrage(
            "Was ist Thymeleaf?", 
            "Thymeleaf", 
            2, 
            "Die Antwort."
        );

        Test testWoche2 = new Test(
            "Test Woche 2", 
            LocalDate.of(2025, 2, 7), 
            LocalDate.of(2026, 2, 7), 
            List.of(frage3, frage4)
        );

        return List.of(testWoche1, testWoche2);

    }

}
