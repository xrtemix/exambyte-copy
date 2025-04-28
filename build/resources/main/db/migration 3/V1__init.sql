create table test_dto
(
    id         serial primary key,
    uuid       uuid,
    test_name  varchar(255),
    test_start date,
    test_ende  date
);

create table frei_text_frage
(
    id serial primary key,
    test_dto integer references test_dto(id),
    test_dto_key integer,
    uuid uuid,
    topic varchar(255),
    title varchar(255),
    body varchar(255),
    points float,
    correct_answer varchar(255)
);

create table multiple_choice_frage
(
    id serial primary key,
    test_dto integer references test_dto(id),
    test_dto_key integer,
    uuid uuid,
    topic varchar(255),
    title varchar(255),
    points float
);

create table multiple_choice_frage_body
(
    id serial primary key,
    multiple_choice_frage integer references multiple_choice_frage(id),
    multiple_choice_frage_key integer,
    body varchar(255)
);
