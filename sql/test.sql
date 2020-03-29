INSERT INTO mystherbe.users (id_user, pseudo, passwd, lastname, firstname, city, email,
                             register_date, status)
VALUES (DEFAULT, 'test', '$2a$10$m92K4ElUtr94eYCKuKWkHulOkBTq1HTssTY0a7D8ehanb909Mn.Ra', 'TEST',
        'test', 'une ville', 'test@example.com', '2020-03-26', 'o');
INSERT INTO mystherbe.customers (id_customer, lastname, firstname, address, postal_code, city,
                                 email, tel_nbr, id_user)
VALUES (DEFAULT, 'COSYNS', 'Mathieu', 'Rue du blabla', 1000, 'Bxl', 'mc@example.com',
        '+32476332211', null);