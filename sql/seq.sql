do
$$
    declare
        maxid int;
    begin
        SELECT MAX(id_user) FROM mystherbe.users into maxid;
        execute 'alter SEQUENCE mystherbe.users_id_user_seq RESTART with ' || maxid + 1;

        SELECT MAX(id_customer) FROM mystherbe.customers into maxid;
        execute 'alter SEQUENCE mystherbe.customers_id_customer_seq RESTART with ' || maxid + 1;

        SELECT MAX(id_type) FROM mystherbe.development_types into maxid;
        execute 'alter SEQUENCE mystherbe.development_types_id_type_seq RESTART with ' || maxid + 1;

        SELECT MAX(id_photo) FROM mystherbe.photos into maxid;
        execute 'alter SEQUENCE mystherbe.photos_id_photo_seq RESTART with ' || maxid + 1;
    end;
$$ language plpgsql

