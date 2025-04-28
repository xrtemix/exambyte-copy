package exambyte.application.domainModel;

import java.util.UUID;

public interface Frage {

   String getFragestellung();

   void setFragestellung(String topic);

   String getTitel();

   void setTitel(String titel);

   double getMaxPunkte();

   void setMaxPunkte(double maxPunkte);

   //Ist dafuer da um herauszufinden um welches Typ es genau handelt (fuer Thymeleafe)
   String getType();

   UUID getUuid();

}
