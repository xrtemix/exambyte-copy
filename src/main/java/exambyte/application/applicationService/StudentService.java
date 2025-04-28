package exambyte.application.applicationService;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import exambyte.application.domainModel.Student;
import exambyte.application.domainService.StudentRepository;
import org.springframework.stereotype.Service;


@SuppressFBWarnings("EI_EXPOSE_REP2")
@Service
public class StudentService {
   private final StudentRepository studentRepository;

   public StudentService(StudentRepository studentRepository) {
      this.studentRepository = studentRepository;
   }


   public boolean getHatZulassung(Integer githubId) {
      return studentRepository.getHatZulassung(githubId);
   }

   public boolean setHatZulassung(Integer githubId, boolean hatZulassung) {
      return studentRepository.setHatZulassung(githubId, hatZulassung);
   }

   public Student save(Student student) {
      return studentRepository.save(student);
   }

   public Student getStudentByName(String name) {
      return studentRepository.getStudentByName(name);
   }

   public Student getStudentByGithubId(Integer id) {
      return studentRepository.getStudentByGithubId(id);
   }

   public Student getStudentByMatrikelnummer(int matrikelnummer) {
      return studentRepository.getStudentByMatrikelnummer(matrikelnummer);
   }

}
