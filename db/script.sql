DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;

CREATE TABLE city
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE country
(
    key  CHAR(3) PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE marital_status
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE disability
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE client
(
    last_name           VARCHAR NOT NULL,
    first_name          VARCHAR NOT NULL,
    patronymic          VARCHAR NOT NULL,
    birthday            DATE    NOT NULL,
    sex                 VARCHAR NOT NULL,
    passport_series     VARCHAR NOT NULL,
    passport_number     VARCHAR NOT NULL,
    produced_by         VARCHAR NOT NULL,
    producing_date      DATE    NOT NULL,
    id                  VARCHAR NOT NULL UNIQUE,
    birthplace          VARCHAR NOT NULL,
    residence_city      INT     NOT NULL,
    residence_address   VARCHAR NOT NULL,
    home_phone_number   VARCHAR,
    mobile_phone_number VARCHAR,
    email               VARCHAR,
    marital_status      INT     NOT NULL,
    citizenship         CHAR(3) NOT NULL,
    disability          INT     NOT NULL,
    pensioner           BOOLEAN NOT NULL,
    month_income        INT,
    conscript           BOOLEAN NOT NULL,

    FOREIGN KEY (residence_city)
        REFERENCES city (id),

    FOREIGN KEY (marital_status)
        REFERENCES marital_status (id),

    FOREIGN KEY (citizenship)
        REFERENCES country (key),

    FOREIGN KEY (disability)
        REFERENCES disability (id)
);

INSERT INTO city(name)
VALUES ('Minsk'),
       ('Grodno'),
       ('Mogilev'),
       ('Brest');
INSERT INTO country(key, name)
VALUES ('BLR', 'Belarus'),
       ('RUS', 'Russia'),
       ('UKR', 'Ukraine'),
       ('POL', 'Poland');
INSERT INTO marital_status(name)
VALUES ('Single'),
       ('Married'),
       ('Civil Marriage'),
       ('Divorced'),
       ('Widow');
INSERT INTO disability(name)
VALUES ('None'),
       ('I Group'),
       ('II Group'),
       ('III Group');