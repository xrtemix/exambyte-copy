package exambyte.application.persistenz.DTOs;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("test")
public record TestDto(
        @Id Integer id,
        UUID uuid,
        String testName,
        LocalDate testStart,
        LocalDate testEnde
) {


    @PersistenceCreator
    public TestDto(Integer id, UUID uuid, String testName, LocalDate testStart, LocalDate testEnde) {
        this.id = id;
        this.uuid = uuid;
        this.testName = testName;
        this.testStart = testStart;
        this.testEnde = testEnde;
    }

}
