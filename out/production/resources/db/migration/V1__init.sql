CREATE TABLE test (
    id         SERIAL PRIMARY KEY,
    uuid       UUID UNIQUE NOT NULL,
    test_name  VARCHAR(255) NOT NULL,
    test_start DATE,
    test_ende  DATE
);

CREATE TABLE frage (
    id                   SERIAL PRIMARY KEY,
    test_id              INTEGER REFERENCES test(id) ON DELETE CASCADE,
    uuid                 UUID UNIQUE NOT NULL,
    fragestellung        TEXT NOT NULL,
    titel                VARCHAR(255) NOT NULL,
    max_punkte           FLOAT NOT NULL,
    loesungsvorschlag    TEXT,
    type          VARCHAR(255) NOT NULL
);

CREATE TABLE antwort_moeglichkeiten (
    id SERIAL PRIMARY KEY,
    frage_id INTEGER REFERENCES frage(id) ON DELETE CASCADE,
    uuid UUID UNIQUE NOT NULL,
    antwort_moeglichkeit VARCHAR(255)
);

CREATE TABLE korrekte_loesungen (
    id SERIAL PRIMARY KEY,
    frage_id INTEGER REFERENCES frage(id) ON DELETE CASCADE,
    uuid UUID UNIQUE NOT NULL,
    korrekte_loesung VARCHAR(255)
);

-- Studenten
create table student
(
    github_id int primary key,
    matrikelnummer int unique not null,
    name varchar(255),
    hat_zulassung boolean default false
)
