package exambyte.application.persistenz.DTOs;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("student")
public class StudentDto {
   @Id
   private final Integer githubId;
   private int matrikelnummer;
   private String name;
   private boolean hatZulassung;

   public StudentDto(Integer id, int matrikelnummer, String name) {
      this.githubId = id;
      this.matrikelnummer = matrikelnummer;
      this.name = name;
      this.hatZulassung = false;
   }

   public Integer getGithubId() {
      return githubId;
   }

   public int getMatrikelnummer() {
      return matrikelnummer;
   }

   public void setMatrikelnummer(int matrikelnummer) {
      this.matrikelnummer = matrikelnummer;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isHatZulassung() {
      return hatZulassung;
   }

   public void setHatZulassung(boolean hatZulassung) {
      this.hatZulassung = hatZulassung;
   }
}
