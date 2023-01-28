CREATE TYPE sex_type AS ENUM ('male', 'female');

CREATE TABLE city
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE country
(
    key  CHAR(3) PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE marital_status
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE disability
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE client
(
    last_name           VARCHAR  NOT NULL,
    first_name          VARCHAR  NOT NULL,
    patronymic          VARCHAR  NOT NULL,
    birthday            DATE     NOT NULL,
    sex                 sex_type NOT NULL,
    passport_series     VARCHAR  NOT NULL,
    passport_number     VARCHAR  NOT NULL,
    produced_by         VARCHAR  NOT NULL,
    producing_date      DATE     NOT NULL,
    id                  VARCHAR  NOT NULL,
    birthplace          VARCHAR  NOT NULL,
    residence_city      INT      NOT NULL,
    residence_address   VARCHAR  NOT NULL,
    home_phone_number   VARCHAR,
    mobile_phone_number VARCHAR,
    email               VARCHAR,
    marital_status      INT      NOT NULL,
    citizenship         CHAR(3)  NOT NULL,
    disability          INT      NOT NULL,
    pensioner           BOOLEAN  NOT NULL,
    month_income        MONEY,
    conscript           BOOLEAN  NOT NULL,

    FOREIGN KEY (residence_city)
        REFERENCES city (id),

    FOREIGN KEY (marital_status)
        REFERENCES marital_status (id),

    FOREIGN KEY (citizenship)
        REFERENCES country (key),

    FOREIGN KEY (disability)
        REFERENCES disability (id)
);