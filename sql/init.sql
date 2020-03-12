DROP SCHEMA IF EXISTS mystherbe CASCADE;
CREATE SCHEMA mystherbe;

CREATE TABLE mystherbe.utilisateurs
(
    id_util       SERIAL
        PRIMARY KEY,
    pseudo        VARCHAR(25)
        NOT NULL,
    mdp           VARCHAR(255)
        NOT NULL,
    nom           VARCHAR(25)
        NOT NULL,
    prenom        VARCHAR(25)
        NOT NULL,
    ville         VARCHAR(25)
        NOT NULL,
    email         VARCHAR(50)
        NOT NULL,
    date_inscript DATE
        NOT NULL,
    statut        VARCHAR(50)
        NOT NULL
);

CREATE TABLE mystherbe.clients
(
    id_client   SERIAL
        PRIMARY KEY,
    nom         VARCHAR(25)
        NOT NULL,
    prenom      VARCHAR(25)
        NOT NULL,
    adresse     VARCHAR(100)
        NOT NULL,
    code_postal INT
        NOT NULL,
    ville       VARCHAR(25)
        NOT NULL,
    email       VARCHAR(50)
        NOT NULL,
    no_tel      VARCHAR(14)
        NOT NULL,
    id_util     INT
        REFERENCES mystherbe.utilisateurs (id_util)
        NULL
);

CREATE TABLE mystherbe.etats
(
    id_etat SERIAL
        PRIMARY KEY,
    titre   VARCHAR(25)
        NOT NULL
);

CREATE TABLE mystherbe.devis
(
    id_devis      VARCHAR(25)
        PRIMARY KEY,
    id_client     INT
        REFERENCES mystherbe.clients (id_client)
        NOT NULL,
    date_devis    DATE
        NOT NULL,
    montant_total MONEY
        NOT NULL,
    duree_travaux INT
        NOT NULL,
    id_etat       INT
        REFERENCES mystherbe.etats (id_etat)
        NOT NULL,
    date_debut    DATE
        NULL
);

CREATE TABLE mystherbe.types_amenagement
(
    id_type SERIAL
        PRIMARY KEY,
    titre   VARCHAR(25)
        NOT NULL
);

CREATE TABLE mystherbe.photos
(
    id_photo      SERIAL
        PRIMARY KEY,
    titre         VARCHAR(25)
        NOT NULL,
    lien          VARCHAR(50)
        NOT NULL,
    id_devis      VARCHAR(25)
        REFERENCES mystherbe.devis (id_devis)
        NOT NULL,
    est_visible   BOOLEAN
        NOT NULL
        DEFAULT FALSE,
    id_type       INT
        REFERENCES mystherbe.types_amenagement (id_type)
        NOT NULL,
    avant_travaux BOOLEAN
        NOT NULL
        DEFAULT TRUE
);

CREATE TABLE mystherbe.types_devis
(
    id_devis VARCHAR(25)
        REFERENCES mystherbe.devis (id_devis)
        NOT NULL,
    id_type  INT
        REFERENCES mystherbe.types_amenagement (id_type)
        NOT NULL,
    CONSTRAINT pk_types_devis PRIMARY KEY (id_devis, id_type)
);

ALTER TABLE mystherbe.devis
    ADD COLUMN
        id_photo INT
            REFERENCES mystherbe.photos (id_photo)
            NULL;
