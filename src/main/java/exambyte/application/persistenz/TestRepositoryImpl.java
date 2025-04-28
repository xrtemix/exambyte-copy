package exambyte.application.persistenz;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import exambyte.application.applicationService.AntwortMoeglichkeitenService;
import exambyte.application.applicationService.FrageService;
import exambyte.application.applicationService.KorrekteLoesungenService;
import exambyte.application.domainModel.Frage;
import exambyte.application.domainModel.FreiTextFrage;
import exambyte.application.domainModel.MultipleChoiceFrage;
import exambyte.application.domainModel.Test;
import exambyte.application.domainService.TestRepository;

import java.util.*;
import java.util.stream.Collectors;

import exambyte.application.persistenz.DTOs.AntwortMoeglichkeitenDto;
import exambyte.application.persistenz.DTOs.FrageDto;
import exambyte.application.persistenz.DTOs.KorrekteLoesungenDto;
import exambyte.application.persistenz.DTOs.TestDto;
import exambyte.application.persistenz.Repositorys.TestDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@SuppressFBWarnings("EI_EXPOSE_REP2")
@Repository
public class TestRepositoryImpl implements TestRepository {

    //getAllServices
    private final TestDataRepository testDataRepository;
    private final FrageService frageService;
    private final AntwortMoeglichkeitenService antwortMoeglichkeitenService;
    private final KorrekteLoesungenService korrekteLoesungenService;


    private final List<Test> tests;

    @Autowired
    public TestRepositoryImpl(TestDataRepository testDataRepository,
                              FrageService frageService,
                              AntwortMoeglichkeitenService antwortMoeglichkeitenService,
                              KorrekteLoesungenService korrekteLoesungenService) {
        this.testDataRepository = testDataRepository;
        this.frageService = frageService;
        this.antwortMoeglichkeitenService = antwortMoeglichkeitenService;
        this.korrekteLoesungenService = korrekteLoesungenService;
        this.tests = new ArrayList<>();
    }

    // DB-Operationen
    @Override
    public Test save(Test test) {
        Optional<TestDto> testByUuid = testDataRepository.getTestByUuid(test.getUuid());

        if (testByUuid.isPresent()) {
            //If present we add the Test itself first
            testDataRepository.save(toTestDto(testByUuid.get().id(), test));
            //now add all frageDTOs

            //first get all existing questions in DB, compare to ur local, and save only new ones.
            List<Frage> allFragen = test.getFragen();
            List<FrageDto> existingFragen = frageService.getQuestionsByTestId(testByUuid.get().id());
            Set<UUID> existingUuids = existingFragen.stream()
                    .map(FrageDto::uuid)
                    .collect(Collectors.toSet());

            List<Frage> newFragen = allFragen.stream()
                    .filter(frage -> !existingUuids.contains(frage.getUuid()))
                    .toList();

            //Now Add the Fragen
            List<FrageDto> newFrageDtos = toFragenDto(newFragen, testByUuid.get().id());
            newFrageDtos.forEach(frageService::save);

            //Add for All New MC Questions the AntwortMoeglichkeiten and KorrekteLoesungen
            List<MultipleChoiceFrage> mcFragen = newFragen.stream()
                    .filter(x -> x.getType().equals("mc"))
                    .map(frage -> (MultipleChoiceFrage) frage)
                    .toList();


            mcFragen.forEach(frage -> saveMcFrage((MultipleChoiceFrage)frage));
            return test;
        } else {
            TestDto testDto = toTestDto(test);
            TestDto saved = testDataRepository.save(testDto);
            return toTest(saved);
        }
    }

    @Override
    public Test getTestByName(String testName) {
        Optional<TestDto> saved = testDataRepository.getTestByTestName(testName);
        return toTest(saved.orElseThrow(() -> new NoSuchElementException("Test.ID(" + testName + ") - not found")));
    }

    @Override
    public Test getTestByUuid(UUID id) {
        Optional<TestDto> saved = testDataRepository.getTestByUuid(id);
        return toTest(saved.orElseThrow(() -> new NoSuchElementException("Test.ID(" + id + ") - not found")));
    }

    @Override
    public List<Test> findAll() {
        List<TestDto> saved = testDataRepository.findAll();
        return toTests(saved);
    }

    @Override
    public boolean removeTestByTestName(String testName) {
        return testDataRepository.removeTestByTestName(testName);
    }

    @Override
    public boolean removeTestByIUuid(UUID id) {
        return testDataRepository.removeTestByUuid(id);
    }


    //----------------------------------------------------------------------------------------------------------------------------
    // Mapping von Test zu TestDto und umgekehrt


    private Test toTest(TestDto saved) {
        List<FrageDto> frageDtos = frageService.getQuestionsByTestId(saved.id());

        List<Frage> fragen = new ArrayList<>();
        for (FrageDto frageDto : frageDtos) {
            if (frageDto.type().equals("mc")) {
                //First Get  AntwortMoeglichkeitenDtos and KorrekteLoesungenDtos for a given Frage
                List<String> antwMoeglichkeiten = antwortMoeglichkeitenService.getAntwortMoeglichkeitenByFrageId(frageDto.id())
                        .stream()
                        .map(x -> x.antwortMoeglichkeit())
                        .toList();
                List<String> korrLoesungen = korrekteLoesungenService.getKorrekteLoesungenByFrageId(frageDto.id())
                        .stream()
                        .map(x -> x.korrekteLoesung())
                        .toList();
                //create a new MC Frage using those Lists
                fragen.add(new MultipleChoiceFrage(
                        frageDto.uuid(),
                        frageDto.fragestellung(),
                        frageDto.titel(),
                        antwMoeglichkeiten,
                        korrLoesungen,
                        (float)frageDto.maxPunkte()));
            } else {
                fragen.add(new FreiTextFrage(
                        frageDto.uuid(),
                        frageDto.fragestellung(),
                        frageDto.titel(),
                        frageDto.maxPunkte(),
                        frageDto.loesungsvorschlag()));
            }
        }

        return new Test(
                saved.uuid(),
                saved.testName(),
                saved.testStart(),
                saved.testEnde(),
                fragen);
    }

    private TestDto toTestDto(Test test) {
        //Create a new Entry in a Test
        return new TestDto(
                null,
                test.getUuid(),
                test.getTestName(),
                test.getTestStart(),
                test.getTestEnde());
    }

    /**
     * Erstellen eines TestDto aus einem bereits gespeicherten Tests
     *
     * @param id Primärschlüssel des Tests aus der Datenbank
     */

    private TestDto toTestDto(Integer id, Test test) {
        return new TestDto(id, test.getUuid(), test.getTestName(), test.getTestStart(),
                test.getTestEnde());
    }

    private Frage toFrage(FrageDto frageDto) {
        if (frageDto.type().equals("mc")) {
            //if its mc Question then get All KorrekteLoesungen and Antw.Moeglichkeiten and save them in the frage
            //Get both of the Lists in form of a DTO
            List<AntwortMoeglichkeitenDto> antworten = antwortMoeglichkeitenService.getAntwortMoeglichkeitenByFrageId(frageDto.id());
            List<KorrekteLoesungenDto> korrekteAntworten = korrekteLoesungenService.getKorrekteLoesungenByFrageId(frageDto.id());

            //Convert both of them to a List of Strings
            List<String> antwortenAsStrings = antworten.stream()
                    .map(x -> x.antwortMoeglichkeit())
                    .toList();

            List<String> korrekteAntwortenAsStrings = korrekteAntworten.stream()
                    .map(x -> x.korrekteLoesung())
                    .toList();

            //Now create a new MultipleChoiceQuestion using those Lists
            return new MultipleChoiceFrage(
                    frageDto.fragestellung(),
                    frageDto.titel(),
                    antwortenAsStrings,
                    antwortenAsStrings,
                    frageDto.maxPunkte());
        } else {
            return new FreiTextFrage(
                    frageDto.fragestellung(),
                    frageDto.titel(),
                    frageDto.maxPunkte(),
                    frageDto.loesungsvorschlag());
        }
    }

    private FrageDto toFrageDto(Frage frage, Integer testId) {
        if (frage instanceof FreiTextFrage) {
            return toFrageDto((FreiTextFrage) frage, testId);
        } else if (frage instanceof MultipleChoiceFrage) {
            return toFrageDto((MultipleChoiceFrage) frage, testId);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private FrageDto toFrageDto(FreiTextFrage frage, Integer testId) {
        return new FrageDto(null,
                frage.getUuid(),
                testId,
                frage.getFragestellung(),
                frage.getTitel(),
                (float) frage.getMaxPunkte(),
                frage.getLoesungsvorschlag(),
                frage.getType());
    }

    private FrageDto toFrageDto(MultipleChoiceFrage frage, Integer testId) {
        return new FrageDto(
                null,
                frage.getUuid(),
                testId,
                frage.getFragestellung(),
                frage.getTitel(),
                (float) frage.getMaxPunkte(),
                null,
                frage.getType());
    }

    private List<Test> toTests(List<TestDto> testDtos) {
        return testDtos.stream().map(this::toTest).toList();
    }

    private List<TestDto> toTestDtos(List<Test> tests) {
        return tests.stream().map(this::toTestDto).toList();
    }

    private List<Frage> toFragen(List<FrageDto> fragen) {
        return fragen.stream()
                .map(this::toFrage).toList();
    }

    private List<FrageDto> toFragenDto(List<Frage> fragen, Integer testId) {
        return fragen.stream()
                .map(frage -> {
                    if (frage instanceof FreiTextFrage) {
                        return toFrageDto((FreiTextFrage) frage, testId);
                    } else if (frage instanceof MultipleChoiceFrage) {
                        return toFrageDto((MultipleChoiceFrage) frage, testId);
                    } else {
                        throw new IllegalArgumentException(
                                "Frage-Typ nicht bekannt" + frage.getClass().getName());
                    }
                }).toList();
    }

    //saves only the AntwortMoeglichkeiten and the KorrekteLoesungen,
    // if the Frage itself is already saved!!!
    private void saveMcFrage(MultipleChoiceFrage frage){
       Integer frageId = frageService.getQuestionByUuid(frage.getUuid()).id();
       frage.getAntwortMoeglichkeiten()
                       .forEach(x -> antwortMoeglichkeitenService
                               .save(new AntwortMoeglichkeitenDto(null, UUID.randomUUID(), frageId, x)));
       frage.getKorrekteLoesungen()
               .forEach(x -> korrekteLoesungenService
                       .save(new KorrekteLoesungenDto(null, UUID.randomUUID(), frageId, x)));
    }
}


