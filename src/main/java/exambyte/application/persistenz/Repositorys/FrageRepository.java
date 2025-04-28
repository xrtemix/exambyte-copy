package exambyte.application.persistenz.Repositorys;

import exambyte.application.persistenz.DTOs.FrageDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FrageRepository extends CrudRepository<FrageDto, Integer> {

    // Find questions by testId (1:n relationship with TestDto)
    @Query("SELECT * FROM frage WHERE test_id = :testId")
    List<FrageDto> findByTestId(Integer testId);

    // Find a specific question by its UUID
    Optional<FrageDto> findByUuid(UUID uuid);

}
