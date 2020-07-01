DROP TABLE IF EXISTS coffee_machine_history, news, "user";

CREATE TABLE IF NOT EXISTS coffee_machine_history (
    id serial PRIMARY KEY, 
    status INTEGER,
    user_name VARCHAR(40),
    status_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS news (
    id serial PRIMARY KEY,
    title VARCHAR(100),
    text VARCHAR(8000),
    user_name VARCHAR(40),
    time TIMESTAMP,
    image LONGBLOB,
    is_deleted bool default false
);

CREATE TABLE IF NOT EXISTS user (id serial PRIMARY KEY,k_number VARCHAR(10),name VARCHAR(60),allowance_date TIMESTAMP,role int,is_deleted bool);



