package exambyte.application.persistenz;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import exambyte.application.domainModel.Student;
import exambyte.application.domainService.StudentRepository;
import exambyte.application.persistenz.DTOs.StudentDto;
import exambyte.application.persistenz.Repositorys.StudentDataRepository;
import org.springframework.stereotype.Repository;

@SuppressFBWarnings("EI_EXPOSE_REP2")
@Repository
public class StudentRepositoryImpl implements StudentRepository {
   private final StudentDataRepository studentDataRepository;

   public StudentRepositoryImpl(StudentDataRepository studentDataRepository) {
      this.studentDataRepository = studentDataRepository;
   }


   // DB-Operationen
   @Override
   public Student save(Student student) {
      StudentDto saved = studentDataRepository.save(toStudentDto(student));
      return toStudent(saved);
   }

   @Override
   public Student getStudentByName(String name) {
      StudentDto studentDto = studentDataRepository.getStudentByName(name);
      return toStudent(studentDto);
   }

   @Override
   public Student getStudentByGithubId(Integer id) {
      StudentDto studentDto = studentDataRepository.getStudentByGithubId(id);
      return toStudent(studentDto);
   }

   @Override
   public Student getStudentByMatrikelnummer(int matrikelnummer) {
      StudentDto studentDto =
          studentDataRepository.getStudentByMatrikelnummer(matrikelnummer);
      return toStudent(studentDto);
   }

   public boolean getHatZulassung(Integer id) {
      return studentDataRepository.getHatZulassung(id);
   }

   public boolean setHatZulassung(Integer githubId, boolean hatZulassung) {
      return studentDataRepository.setHatZulassung(githubId, hatZulassung);
   }

   // Umwandlung von DTO zu Domain-Objekt
   private Student toStudent(StudentDto studentDto) {
      return new Student(studentDto.getGithubId(), studentDto.getMatrikelnummer(),
          studentDto.getName());
   }

   private StudentDto toStudentDto(Student student) {
      return new StudentDto(student.getGithubId(), student.getMatrikelnummer(),
          student.getName());
   }


}
