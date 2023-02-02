DROP TABLE IF EXISTS results;

CREATE TABLE results
(
    results_id integer NOT NULL PRIMARY KEY,
    student_id integer NOT NULL FOREIGN KEY,
    subject_id integer NOT NULL FOREIGN KEY,
    mark integer NOT NULL
);