package exambyte.application.applicationService;
import exambyte.application.persistenz.DTOs.FrageDto;
import exambyte.application.persistenz.DTOs.KorrekteLoesungenDto;
import exambyte.application.persistenz.Repositorys.KorrekteLoesungenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KorrekteLoesungenService {
   private final KorrekteLoesungenRepository korrekteLoesungenRepository;

    @Autowired
    public KorrekteLoesungenService(KorrekteLoesungenRepository korrekteLoesungenRepository) {
        this.korrekteLoesungenRepository = korrekteLoesungenRepository;
    }

    public void save(KorrekteLoesungenDto korrekteLoesungenDto){
        korrekteLoesungenRepository.save(korrekteLoesungenDto);
    }

    public List<KorrekteLoesungenDto> getKorrekteLoesungenByFrageId(Integer frageId) {
        return korrekteLoesungenRepository.findByFrageId(frageId);
    }

    public KorrekteLoesungenDto getKorrekteLoesungenByUuid(UUID uuid) {
        return korrekteLoesungenRepository.findByUuid(uuid).orElseThrow(() -> new IllegalArgumentException("Frage nicht gefunden"));
    }
}
