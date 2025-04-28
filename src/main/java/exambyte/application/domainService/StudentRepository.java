package exambyte.application.domainService;

import exambyte.application.domainModel.Student;

public interface StudentRepository {

   Student save(Student student);

   // Finden
   Student getStudentByName(String name);

   Student getStudentByGithubId(Integer id);

   Student getStudentByMatrikelnummer(int matrikelNummer);

   boolean getHatZulassung(Integer id);

   boolean setHatZulassung(Integer githubId, boolean hatZulassung);
}
