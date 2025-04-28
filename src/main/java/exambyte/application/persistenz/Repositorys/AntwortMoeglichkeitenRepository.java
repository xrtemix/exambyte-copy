package exambyte.application.persistenz.Repositorys;

import exambyte.application.persistenz.DTOs.AntwortMoeglichkeitenDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AntwortMoeglichkeitenRepository extends CrudRepository<AntwortMoeglichkeitenDto, Integer> {

    @Query("SELECT * FROM antwort_moeglichkeiten WHERE frage_id = :frageId")
    List<AntwortMoeglichkeitenDto> findByFrageId(Integer frageId);

    @Query("SELECT * FROM antwort_moeglichkeiten WHERE uuid = :uuid")
    Optional<AntwortMoeglichkeitenDto> findByUuid(UUID uuid);
}
