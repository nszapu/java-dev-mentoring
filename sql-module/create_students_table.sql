DROP TABLE IF EXISTS students;

CREATE TABLE students
(
    student_id integer NOT NULL PRIMARY KEY,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    email character varying(50) NOT NULL,
    phone_number character varying(50) NOT NULL,
    primary_skill character varying(50) NOT NULL,
    created_datetime DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_datetime DATE NOT NULL DEFAULT CURRENT_DATE
);