package exambyte.application.persistenz.Repositorys;

import exambyte.application.persistenz.DTOs.KorrekteLoesungenDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KorrekteLoesungenRepository extends CrudRepository<KorrekteLoesungenDto, Integer> {

    @Query("SELECT * FROM korrekte_loesungen WHERE frage_id = :frageId")
    List<KorrekteLoesungenDto> findByFrageId(Integer frageId);

    @Query("SELECT * FROM korrekte_loesungen WHERE uuid = :uuid")
    Optional<KorrekteLoesungenDto> findByUuid(UUID uuid);
}
