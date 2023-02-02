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
	created_datetime DATE DEFAULT CURRENT_DATE,
    updated_datetime DATE DEFAULT CURRENT_DATE
);

DROP TABLE IF EXISTS subjects;

CREATE TABLE subjects
(
    subject_id integer NOT NULL PRIMARY KEY,
    subject_name character varying(50) NOT NULL,
    tutor character varying(50) NOT NULL
);

DROP TABLE IF EXISTS results;

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

SELECT DISTINCT student_id, first_name FROM students where first_name = 'Jack';

SELECT * FROM students where last_name = 'Cooper';

SELECT * FROM students where phone_number = '405-523-1230';

SELECT s.*, r.mark FROM students s INNER JOIN results r
	ON s.student_id=r.student_id WHERE last_name = 'Cooper';
	
	
CREATE OR REPLACE FUNCTION get_average_result_by_student(int, out res float)
RETURNS float AS
$BODY$
BEGIN
	SELECT AVG(r.mark) FROM students s INNER JOIN results r
	ON s.student_id=r.student_id WHERE s.student_id = $1 INTO res;
END;
$BODY$
LANGUAGE plpgsql;

SELECT get_average_result_by_student(3372);

CREATE OR REPLACE FUNCTION get_average_result_by_subject(text, out res float)
RETURNS float AS
$BODY$
BEGIN
	SELECT AVG(r.mark) FROM subjects s INNER JOIN results r
	ON s.subject_id=r.subject_id WHERE s.subject_name = $1 INTO res;
END;
$BODY$
LANGUAGE plpgsql;

SELECT get_average_result_by_subject('Mathematics');

CREATE OR REPLACE FUNCTION get_redzone_students()
RETURNS SETOF students AS
$BODY$
BEGIN
	RETURN QUERY SELECT * FROM students WHERE student_id IN 
		(SELECT student_id as poor_mark_count FROM results 
		WHERE mark <= 3 GROUP BY student_id HAVING COUNT(mark) >= 2);
END;
$BODY$
LANGUAGE plpgsql;

SELECT get_redzone_students();


SELECT * FROM students WHERE student_id IN 
(SELECT student_id as poor_mark_count FROM results 
WHERE mark <= 3 GROUP BY student_id HAVING COUNT(mark) >= 2)

