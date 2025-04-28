package exambyte.application.applicationService;

import exambyte.application.persistenz.DTOs.AntwortMoeglichkeitenDto;
import exambyte.application.persistenz.Repositorys.AntwortMoeglichkeitenRepository;
import exambyte.application.persistenz.Repositorys.FrageRepository;
import exambyte.application.persistenz.DTOs.FrageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AntwortMoeglichkeitenService {

    private final AntwortMoeglichkeitenRepository antwortMoeglichkeitenRepository;

    @Autowired
    public AntwortMoeglichkeitenService(AntwortMoeglichkeitenRepository antwortMoeglichkeitenRepository) {
        this.antwortMoeglichkeitenRepository = antwortMoeglichkeitenRepository;
    }

    public void save(AntwortMoeglichkeitenDto antwortMoeglichkeitenDto){
        antwortMoeglichkeitenRepository.save(antwortMoeglichkeitenDto);
    }

    public List<AntwortMoeglichkeitenDto> getAntwortMoeglichkeitenByFrageId(Integer frageId) {
        return antwortMoeglichkeitenRepository.findByFrageId(frageId);
    }

    public AntwortMoeglichkeitenDto getAntwortMoeglihckeitenByUuid(UUID uuid) {
        return antwortMoeglichkeitenRepository.findByUuid(uuid).orElseThrow(() -> new IllegalArgumentException("Frage nicht gefunden"));
    }
}
