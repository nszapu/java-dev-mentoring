-- task 6
-- Add validation on DB level that will check username on special characters (reject student name with next characters '@', '#', '$').
DROP TABLE IF EXISTS students CASCADE;

CREATE TABLE students
(
    student_id integer NOT NULL PRIMARY KEY,
    first_name character varying(50) NOT NULL
	CONSTRAINT check1 CHECK (first_name NOT LIKE '%@%' AND first_name NOT LIKE '%#%' and first_name NOT LIKE '%$%'),
    last_name character varying(50) NOT NULL
	CONSTRAINT check2 CHECK (last_name NOT LIKE '%@%' AND last_name NOT LIKE '%#%' and last_name NOT LIKE '%$%'),
    date_of_birth DATE NOT NULL,
    email character varying(50) NOT NULL,
    phone_number character varying(50) NOT NULL,
    primary_skill character varying(50) NOT NULL,
	created_datetime DATE DEFAULT CURRENT_DATE,
    updated_datetime DATE DEFAULT CURRENT_DATE
);

DROP TABLE IF EXISTS subjects CASCADE;

CREATE TABLE subjects
(
    subject_id integer NOT NULL PRIMARY KEY,
    subject_name character varying(50) NOT NULL,
    tutor character varying(50) NOT NULL
);

DROP TABLE IF EXISTS results CASCADE;

CREATE TABLE results
(
    results_id integer NOT NULL PRIMARY KEY,
    student_id integer,
    subject_id integer,
    mark integer NOT NULL,
	CONSTRAINT fk_student FOREIGN KEY(student_id) REFERENCES students(student_id),
	CONSTRAINT fk_subject FOREIGN KEY(subject_id) REFERENCES subjects(subject_id)
);

COPY students FROM 'C:/workspace/dev-mentoring/sql-module/Student2.csv' DELIMITER ',' CSV HEADER;
COPY subjects FROM 'C:/workspace/dev-mentoring/sql-module/Subject.csv' DELIMITER ',' CSV HEADER;
COPY results FROM 'C:/workspace/dev-mentoring/sql-module/Result.csv' DELIMITER ',' CSV HEADER;

insert into students values 
(999999, 'Jack', 'Cooper$', '1984-11-05', 'Jack_Cooper@gmail.com', '405-523-1230', 'Mathematics');

-- using alter table
ALTER TABLE students ADD CONSTRAINT check1 CHECK (first_name NOT LIKE '%@%' AND first_name NOT LIKE '%#%' and first_name NOT LIKE '%$%');

ALTER TABLE students ADD CONSTRAINT check2 CHECK (last_name NOT LIKE '%@%' AND last_name NOT LIKE '%#%' and last_name NOT LIKE '%$%');

-- task 5
-- Add trigger that will update column updated_datetime to current date in case of updating any of student.
CREATE OR REPLACE FUNCTION trigger_set_date()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_datetime = NOW()::DATE;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_date
BEFORE UPDATE ON students
FOR EACH ROW
EXECUTE FUNCTION trigger_set_date();

update students set first_name = 'Gabi' where student_id = 1;

select * from students where student_id = 1;

-- task 7
-- Create snapshot that will contain next data: student name, student surname, subject name, mark
-- (snapshot means that in case of changing some data in source table – your snapshot should not change).
CREATE MATERIALIZED VIEW students_subjects_marks AS
SELECT st.first_name, st.last_name, su.subject_name, r.mark
FROM students st INNER JOIN results r ON st.student_id=r.student_id
INNER JOIN subjects su ON r.subject_id=su.subject_id;


select * from students_subjects_marks