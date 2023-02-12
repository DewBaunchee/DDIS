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


CREATE TABLE deposit_program
(
    id                     SERIAL PRIMARY KEY,
    name                   VARCHAR NOT NULL,
    revocable              BOOLEAN NOT NULL,
    period                 VARCHAR NOT NULL,
    first_payment          INT     NOT NULL,
    percent_strategy       VARCHAR NOT NULL,
    replenishment          VARCHAR NOT NULL,
    expenditure_operations VARCHAR NOT NULL,
    auto_prolongation      BOOLEAN NOT NULL
);

CREATE TABLE currency
(
    key VARCHAR PRIMARY KEY
);

CREATE TABLE deposit
(
    id       SERIAL PRIMARY KEY,
    type     INTEGER NOT NULL,
    begin    DATE    NOT NULL,
    "end"    DATE    NOT NULL,
    amount   INTEGER NOT NULL,
    currency VARCHAR NOT NULL,
    client   VARCHAR NOT NULL,

    FOREIGN KEY (type) REFERENCES deposit_program (id),
    FOREIGN KEY (currency) REFERENCES currency (key),
    FOREIGN KEY (client) REFERENCES client (id)
);

CREATE TABLE account
(
    number   CHAR(13) NOT NULL,
    code     CHAR(4)  NOT NULL,
    active   BOOLEAN  NOT NULL,
    passive  BOOLEAN  NOT NULL,
    debit    INTEGER  NOT NULL,
    credit   INTEGER  NOT NULL,
    balance  INTEGER  NOT NULL,
    currency VARCHAR  NOT NULL,
    client   VARCHAR  NOT NULL,

    FOREIGN KEY (currency) REFERENCES currency (key),
    FOREIGN KEY (client) REFERENCES client (id)
);