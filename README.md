# ExamByte

### Projektbeschreibung

ExamByte ist eine Plattform für Lehrende und Studierende, um Tests durchzuführen und zu bewerten. Das System bietet eine Authentifizierung über GitHub OAuth und unterstützt spezialisierte Rollen für Studierende, darunter Korrektor:innen und Organisator:innen. Die Benutzerrechte sind über eine Konfigurationsdatei festgelegt, die beim Start der Anwendung eingelesen wird, sodass Änderungen ohne Neukompilierung der Java-Dateien möglich sind. Die Daten aller Nutzer werden in einer PostgreSQL-Datenbank gespeichert, die über Docker verwaltet wird. 

### Hauptfunktionen

- Authentifizierung: Nutzer melden sich über GitHub OAuth an.

- Benutzerrollen:
    - Studierende: Können Tests durchführen und ihre Ergebnisse einsehen.

    -  Korrektor:in: Kann zugewiesene freie Textantworten bewerten.

    - Organisator:in: Hat Zugriff auf alle Antworten, kann Bewertungen ändern und administrative Aufgaben durchführen.

- Testverwaltung: Erstellung, Durchführung und Auswertung von Tests.

- Ergebnissexport: Export der Testergebnisse als CSV-Dateien.

- Zugriffssteuerung: Rollenbasierte Zugriffskontrolle durch Konfigurationsdatei.

- Technologiestack

    - Backend: Spring Boot (Java)

    - Datenbank: PostgreSQL

    - Frontend: Bootstrap, JavaScript, Tailwind

    - Authentifizierung: GitHub OAuth


## Nutzung



Benutzer melden sich mit GitHub an.
Organisator:innen legen Tests an und weisen Korrektor:innen Aufgaben zu.

Korrektor:innen bewerten Antworten und speichern ihre Bewertungen.

Organisator:innen exportieren Testergebnisse als CSV.


### Zukünftige Erweiterungen


Mehr Authentifizierungsoptionen: Neben GitHub auch Google und Uni-SSO.

Bessere UI: Verbesserung des Designs und der Nutzerfreundlichkeit.


## Installation
Stelle sicher, dass folgende Abhängigkeiten auf deinem System vorhanden sind:
- Java (JDK 17 oder neuer)
- Gradle
- Docker

Projekt-Repository klonen:

```bash
    git clone https://github.com/hhu-propra2-ws24/exambyte-exambyte-befma.git
    cd exambyte
```
Docker-Container für die Datenbank starten:
```bash
    docker compose up -d
```
Projekt mit Gradle bauen und starten: 

```bash 
    ./gradlew build
    ./gradlew bootRun
```

Sobald ExamByte gestartet ist, ist die Anwendung unter 
```http://localhost:8080```
erreichbar.