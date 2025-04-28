package exambyte.application.applicationService;

import exambyte.application.persistenz.Repositorys.FrageRepository;
import exambyte.application.persistenz.DTOs.FrageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FrageService {

    private final FrageRepository frageRepository;

    public void save(FrageDto frageDto){
        frageRepository.save(frageDto);
    }


    @Autowired
    public FrageService(FrageRepository frageRepository) {
        this.frageRepository = frageRepository;
    }

    public List<FrageDto> getQuestionsByTestId(Integer testId) {
        return frageRepository.findByTestId(testId);
    }

    public FrageDto getQuestionByUuid(UUID uuid) {
        return frageRepository.findByUuid(uuid).orElseThrow(() -> new IllegalArgumentException("Frage nicht gefunden"));
    }
}
