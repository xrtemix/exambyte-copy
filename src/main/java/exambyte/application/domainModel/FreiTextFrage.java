package exambyte.application.domainModel;

import java.util.UUID;

public class FreiTextFrage implements Frage {
    private final UUID uuid;
    private String titel;
    private String fragestellung;
    private double maxPunkte;
    private String loesungsvorschlag;

    public FreiTextFrage(UUID uuid, String fragestellung, String titel,
                         double maxPunkte, String loesungsvorschlag) {
        this.uuid = uuid;
        this.fragestellung = fragestellung;
        this.titel = titel;
        this.maxPunkte = maxPunkte;
        this.loesungsvorschlag = loesungsvorschlag;
    }
    public FreiTextFrage(String fragestellung, String titel,
                         double maxPunkte, String loesungsvorschlag) {
        this.uuid = UUID.randomUUID();
        this.fragestellung = fragestellung;
        this.titel = titel;
        this.maxPunkte = maxPunkte;
        this.loesungsvorschlag = loesungsvorschlag;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public String getLoesungsvorschlag() {
        return loesungsvorschlag;
    }

    public void setLoesungsvorschlag(String loesungsvorschlag) {
        this.loesungsvorschlag = loesungsvorschlag;
    }


    @Override
    public String getTitel() {
        return titel;
    }

    @Override
    public void setTitel(String titel) {
        this.titel = titel;
    }

    @Override
    public String getFragestellung() {
        return fragestellung;
    }

    @Override
    public void setFragestellung(String fragestellung) {
        this.fragestellung = fragestellung;
    }

    @Override
    public double getMaxPunkte() {
        return maxPunkte;
    }

    @Override
    public void setMaxPunkte(double maxPunkte) {
        this.maxPunkte = maxPunkte;
    }


    public String getType() {
        return "freetext";
    }
}
