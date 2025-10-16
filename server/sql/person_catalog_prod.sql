drop database if exists person_catalog_prod;
create database person_catalog_prod;
use person_catalog_prod;

create table person (
	person_id int primary key auto_increment,
    first_name varchar (50) not null,
    last_name varchar (50) not null,
    dob date not null,
    email varchar (255) not null unique,
    phone varchar(25) not null
);

set sql_safe_updates = 0;

insert into person (first_name, last_name, dob, email, phone)
values
	('John', 'Doe', '1999-01-01', 'doe@email.com', '123-456-7890'),
	('Mary', 'Sue', '2000-02-02', 'sue@email.com', '123-456-7890'),
	('Peter', 'Parker', '2001-03-03', 'parker@email.com', '123-456-7890');
        
set sql_safe_updates = 1;