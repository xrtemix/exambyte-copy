package exambyte.application.persistenz.Repositorys;

import exambyte.application.persistenz.DTOs.StudentDto;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentDataRepository extends CrudRepository<StudentDto, Integer> {

   StudentDto save(StudentDto student);

   StudentDto getStudentByName(String name);

   StudentDto getStudentByGithubId(Integer id);

   StudentDto getStudentByMatrikelnummer(int matrikelnummer);

   @Query("select hat_zulassung from student where github_id = :id")
   boolean getHatZulassung(Integer id);

   @Modifying
   @Query("update student set hatZulassung = :hatZulassung where githubId = :githubId")
   boolean setHatZulassung(Integer githubId, boolean hatZulassung);
}
