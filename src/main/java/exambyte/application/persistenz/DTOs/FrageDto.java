package exambyte.application.persistenz.DTOs;
import org.springframework.data.annotation.Id;
import java.util.*;
import org.springframework.data.relational.core.mapping.Table;

@Table("frage")
public record FrageDto(
        @Id Integer id,
        UUID uuid,
        Integer testId,  // 🔹 Verknüpfung zur Test-Tabelle
        String fragestellung,
        String titel,
        Float maxPunkte,
        String loesungsvorschlag,
        String type
) {
}
