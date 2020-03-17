DROP SCHEMA IF EXISTS mystherbe CASCADE;
CREATE SCHEMA mystherbe;

CREATE TABLE mystherbe.users
(
    id_user       SERIAL
        PRIMARY KEY,
    pseudo        VARCHAR(25)
        NOT NULL,
    passwd        VARCHAR(255)
        NOT NULL,
    lastname      VARCHAR(25)
        NOT NULL,
    firstname     VARCHAR(25)
        NOT NULL,
    city          VARCHAR(25)
        NOT NULL,
    email         VARCHAR(50)
        NOT NULL,
    register_date DATE
        NOT NULL,
    status        VARCHAR(50)
        NOT NULL
);

CREATE TABLE mystherbe.customers
(
    id_customer SERIAL
        PRIMARY KEY,
    lastname    VARCHAR(25)
        NOT NULL,
    firstname   VARCHAR(25)
        NOT NULL,
    address     VARCHAR(100)
        NOT NULL,
    postal_code INT
        NOT NULL,
    city        VARCHAR(25)
        NOT NULL,
    email       VARCHAR(50)
        NOT NULL,
    tel_nbr     VARCHAR(14)
        NOT NULL,
    id_user     INT
        REFERENCES mystherbe.users (id_user)
        NULL
);

CREATE TABLE mystherbe.states
(
    id_state SERIAL
        PRIMARY KEY,
    title    VARCHAR(25)
        NOT NULL
);

CREATE TABLE mystherbe.quotes
(
    id_quote      VARCHAR(25)
        PRIMARY KEY,
    id_customer   INT
        REFERENCES mystherbe.customers (id_customer)
        NOT NULL,
    quote_date    DATE
        NOT NULL,
    total_amount  MONEY
        NOT NULL,
    work_duration INT
        NOT NULL,
    id_state      INT
        REFERENCES mystherbe.states (id_state)
        NOT NULL,
    start_date    DATE
        NULL
);

CREATE TABLE mystherbe.development_types
(
    id_type SERIAL
        PRIMARY KEY,
    title   VARCHAR(25)
        NOT NULL
);

CREATE TABLE mystherbe.photos
(
    id_photo    SERIAL
        PRIMARY KEY,
    title       VARCHAR(25)
        NOT NULL,
    link        VARCHAR(50)
        NOT NULL,
    id_quote    VARCHAR(25)
        REFERENCES mystherbe.quotes (id_quote)
        NOT NULL,
    is_visible  BOOLEAN
        NOT NULL
        DEFAULT FALSE,
    id_type     INT
        REFERENCES mystherbe.development_types (id_type)
        NOT NULL,
    before_work BOOLEAN
        NOT NULL
        DEFAULT TRUE
);

CREATE TABLE mystherbe.quote_types
(
    id_quote VARCHAR(25)
        REFERENCES mystherbe.quotes (id_quote)
        NOT NULL,
    id_type  INT
        REFERENCES mystherbe.development_types (id_type)
        NOT NULL,
    CONSTRAINT pk_quote_types PRIMARY KEY (id_quote, id_type)
);

ALTER TABLE mystherbe.quotes
    ADD COLUMN
        id_photo INT
            REFERENCES mystherbe.photos (id_photo)
            NULL;
