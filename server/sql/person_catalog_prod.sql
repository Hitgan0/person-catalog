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