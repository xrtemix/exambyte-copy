package exambyte.application.persistenz.DTOs;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("korrekte_loesungen")
public record KorrekteLoesungenDto(
        @Id Integer id,
        UUID uuid,
        Integer frageID,
        String korrekteLoesung
) {
}
