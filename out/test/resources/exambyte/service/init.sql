-- Tests
create table test_dto
(
    id         serial primary key,
    uuid       uuid,
    test_name  varchar(255),
    test_start date,
    test_ende  date
);

create table frage
(
    id             serial primary key,
    test_dto       integer references test_dto (id),
    test_dto_key   integer,
    uuid           uuid,
    topic          varchar(255),
    title          varchar(255),
    body           varchar(255),
    points         float,
    correct_answer varchar(255)
);

create table frage_dto_body
(
    id serial primary key,
    frage integer references frage (id),
    frage_key integer,
    content varchar(255)
);


-- Studenten
create table student
(
    github_id int primary key,
    matrikelnummer int unique not null,
    name varchar(255),
    hat_zulassung boolean default false
)
