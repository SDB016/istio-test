create table if not exists messages (
    id SERIAL not null primary key,
    message VARCHAR(255) NOT NULL
);