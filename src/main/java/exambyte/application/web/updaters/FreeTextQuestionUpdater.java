package exambyte.application.web.updaters;

public record FreeTextQuestionUpdater (
        String testName,
        String titel,
        String fragestellung,
        double maxPunkte,
        String korrekteLoesung){}
