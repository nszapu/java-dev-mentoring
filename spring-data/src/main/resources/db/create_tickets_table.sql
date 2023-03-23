drop table if exists tickets;

create table tickets (
	id serial primary key,
	event_id integer,
	user_id integer,
	category varchar(10),
	place integer,
	foreign key (event_id) references events (id),
	foreign key (user_id) references users (id)
);