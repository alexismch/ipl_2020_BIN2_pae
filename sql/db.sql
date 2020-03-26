drop schema if exists mystherbe cascade;
create schema mystherbe;

create table mystherbe.users
(
    id_user       serial       not null
        constraint users_pkey
            primary key,
    pseudo        varchar(25)  not null,
    passwd        varchar(255) not null,
    lastname      varchar(25)  not null,
    firstname     varchar(25)  not null,
    city          varchar(25)  not null,
    email         varchar(50)  not null,
    register_date date         not null,
    status char not null
);

create table mystherbe.customers
(
    id_customer serial       not null
        constraint customers_pkey
            primary key,
    lastname    varchar(25)  not null,
    firstname   varchar(25)  not null,
    address     varchar(100) not null,
    postal_code integer      not null,
    city        varchar(25)  not null,
    email       varchar(50)  not null,
    tel_nbr     varchar(14)  not null,
    id_user     integer
        constraint customers_id_user_fkey
            references mystherbe.users
);

create table mystherbe.states
(
    id_state serial      not null
        constraint states_pkey
            primary key,
    title    varchar(25) not null
);

create table mystherbe.quotes
(
    id_quote      varchar(25) not null
        constraint quotes_pkey
            primary key,
    id_customer   integer     not null
        constraint quotes_id_customer_fkey
            references mystherbe.customers,
    quote_date    date        not null,
    total_amount  money       not null,
    work_duration integer     not null,
    id_state      integer     not null
        constraint quotes_id_state_fkey
            references mystherbe.states,
    start_date    date,
    id_photo      integer
);

create table mystherbe.development_types
(
    id_type serial      not null
        constraint development_types_pkey
            primary key,
    title varchar(50) not null
);

create table mystherbe.photos
(
    id_photo    serial                not null
        constraint photos_pkey
            primary key,
    title       varchar(100)          not null,
    base64      varchar(10485760)     not null,
    id_quote    varchar(25)           not null
        constraint photos_id_quote_fkey
            references mystherbe.quotes,
    is_visible  boolean default false not null,
    id_type     integer               not null
        constraint photos_id_type_fkey
            references mystherbe.development_types,
    before_work boolean default true  not null
);

alter table mystherbe.quotes
    add constraint quotes_id_photo_fkey
        foreign key (id_photo) references mystherbe.photos;

create table mystherbe.quote_types
(
    id_quote varchar(25) not null
        constraint quote_types_id_quote_fkey
            references mystherbe.quotes,
    id_type  integer     not null
        constraint quote_types_id_type_fkey
            references mystherbe.development_types,
    constraint pk_quote_types
        primary key (id_quote, id_type)
);

