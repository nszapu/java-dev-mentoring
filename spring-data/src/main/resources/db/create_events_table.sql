drop table if exists events;

create table events (
    id serial primary key,
    title varchar(30),
    date date
);