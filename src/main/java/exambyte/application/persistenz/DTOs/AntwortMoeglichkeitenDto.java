package exambyte.application.persistenz.DTOs;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("antwort_moeglichkeiten")
public record AntwortMoeglichkeitenDto(
        @Id Integer id,
        UUID uuid,
        Integer frageId,
        String antwortMoeglichkeit) {
}
