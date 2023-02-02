DROP TABLE IF EXISTS subjects;

CREATE TABLE subjects
(
    subject_id integer NOT NULL PRIMARY KEY,
    subject_name character varying(50) NOT NULL,
    tutor character varying(50) NOT NULL
);