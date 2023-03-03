drop table if exists user_accounts;

create table user_accounts (
	id serial primary key,
	user_id integer,
	balance integer,
	foreign key (user_id) references users (id)
);