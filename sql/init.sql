/**
  TYPES D'AMÉNAGEMENTS
 */

INSERT INTO mystherbe.development_types (id_type, title)
VALUES (1, 'Aménagement de jardin de ville');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (2, 'Aménagement de jardin');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (3, 'Aménagement de parc paysagiste');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (4, 'Création de potagers');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (5, 'Entretien de vergers haute-tige');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (6, 'Entretien de vergers basse-tige');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (7, 'Aménagement d’étang');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (8, 'Installation de système d’arrosage automatique');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (9, 'Terrasses en bois');
INSERT INTO mystherbe.development_types (id_type, title)
VALUES (10, 'Terrasses en pierres naturelles');

/**
  UTILISATEURS
 */

--OUVRIERS
INSERT INTO mystherbe.users (id_user, pseudo, passwd, lastname, firstname, city, email,
                             register_date, status)
VALUES (1, 'bri', '$2a$10$8TPgku.84iixWU3Nm66sGOWY.O1qY1Yy.s.oQe40v.tjC1VrTo.3e', 'Lehmann',
        'Brigitte', 'Wavre', 'brigitte.lehmann@vinci.be', now(), 'o');
INSERT INTO mystherbe.users (id_user, pseudo, passwd, lastname, firstname, city, email,
                             register_date, status)
VALUES (2, 'lau', '$2a$10$TV/abhXdIbFFDutSkXrWde7o6WToyRQy2W9.iT9Ke1ohoDte3XcnS', 'Leleux',
        'Laurent', 'Bruxelles', 'laurent.leleux@vinci.be', now(), 'o');
INSERT INTO mystherbe.users (id_user, pseudo, passwd, lastname, firstname, city, email,
                             register_date, status)
VALUES (3, 'chri', '$2a$10$kEsiHqH8wmjoLNCSDF7cMOH2QuARvrVDTqRKypB5Wj9LyaRdmCCrC', 'Damas',
        'Christophe', 'Bruxelles', 'christophe.damas@vinci.be', now(), 'o');

--CLIENTS
INSERT INTO mystherbe.users (id_user, pseudo, passwd, lastname, firstname, city, email,
                             register_date, status)
VALUES (4, 'achil', '$2a$10$Nihl2oM4JtE6g/sB8UbDE.27L7LFNGthEtJG68KLPJd2EZPDXdXdu', 'Ile',
        'Achille', 'Verviers', 'ach.ile@gmail.com', now(), 'c');
INSERT INTO mystherbe.users (id_user, pseudo, passwd, lastname, firstname, city, email,
                             register_date, status)
VALUES (5, 'bazz', '$2a$10$Nihl2oM4JtE6g/sB8UbDE.27L7LFNGthEtJG68KLPJd2EZPDXdXdu', 'Ile', 'Basile',
        'Liège', 'bas.ile@gmail.com', now(), 'c');
INSERT INTO mystherbe.users (id_user, pseudo, passwd, lastname, firstname, city, email,
                             register_date, status)
VALUES (6, 'caro', '$2a$10$Nihl2oM4JtE6g/sB8UbDE.27L7LFNGthEtJG68KLPJd2EZPDXdXdu', 'Line',
        'Caroline', 'Stoumont', 'caro.line@hotmail.com', now(), 'c');


/**
  CLIENTS
 */

INSERT INTO mystherbe.customers (id_customer, lastname, firstname, address, postal_code, city,
                                 email, tel_nbr, id_user)
VALUES (1, 'Line', 'Caroline', 'Rue de l’Eglise, 11', 4987, 'Stoumont', 'caro.line@hotmail.com',
        '080.12.56.78', 6);
INSERT INTO mystherbe.customers (id_customer, lastname, firstname, address, postal_code, city,
                                 email, tel_nbr, id_user)
VALUES (2, 'Ile', 'Théophile', 'Rue de Renkin, 7', 4800, 'Verviers', 'theo.phile@proximus.be',
        '087.25.69.74', null);


/**
  DEVIS
 */

--DEVIS
INSERT INTO mystherbe.quotes (id_quote, id_customer, quote_date, total_amount, work_duration,
                              id_state, start_date, id_photo)
VALUES ('1', 1, '2018-11-12', 4260, 5, 7, '2019-03-01', null);
INSERT INTO mystherbe.quotes (id_quote, id_customer, quote_date, total_amount, work_duration,
                              id_state, start_date, id_photo)
VALUES ('2', 1, '2018-12-15', 18306, 25, 7, '2019-03-15', null);
INSERT INTO mystherbe.quotes (id_quote, id_customer, quote_date, total_amount, work_duration,
                              id_state, start_date, id_photo)
VALUES ('3', 1, '2019-11-12', 8540, 10, 2, '2020-03-30', null);
INSERT INTO mystherbe.quotes (id_quote, id_customer, quote_date, total_amount, work_duration,
                              id_state, start_date, id_photo)
VALUES ('4', 2, '2020-01-10', 6123, 7, 6, '2020-03-02', null);

--TYPES
INSERT INTO mystherbe.quote_types (id_quote, id_type)
VALUES ('1', 6);
INSERT INTO mystherbe.quote_types (id_quote, id_type)
VALUES ('2', 5);
INSERT INTO mystherbe.quote_types (id_quote, id_type)
VALUES ('3', 3);
INSERT INTO mystherbe.quote_types (id_quote, id_type)
VALUES ('4', 1);
INSERT INTO mystherbe.quote_types (id_quote, id_type)
VALUES ('4', 8);