create table categories (
    cat_id bigint AUTO_INCREMENT PRIMARY KEY,
    owner_id bigint REFERENCES categories(cat_id) ON DELETE CASCADE,
    catname_rus varchar(255),
    catname_en varchar(255)
);

create table documents (
    doc_id bigint AUTO_INCREMENT PRIMARY KEY,
    doc_name varchar(255),
    doc_author varchar(100),
    doc_year varchar(4),
    doc_istochnik varchar(255),
    doc_keyword varchar(255),
    doc_dateinput DATE,
    annotation varchar(255),
    cat_id bigint REFERENCES categories(cat_id) ON DELETE CASCADE,
    typedoc_id bigint,
    path_id bigint
);

create table typedocuments (
    typedoc_id bigint AUTO_INCREMENT PRIMARY KEY,
    typedoc_name varchar(255)
);

create table pathsavepdf (
    path_id bigint AUTO_INCREMENT PRIMARY KEY,
    path_name varchar(255)
);

create table users (
    id bigserial,
    username varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
)