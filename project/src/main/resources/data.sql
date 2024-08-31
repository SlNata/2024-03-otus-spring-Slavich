insert into categories(owner_id, catname_rus, catname_en)
values (null, 'Книги', 'Books'), (null, 'Журналы', 'Magazine'), (null, 'Газеты', 'Newspapers'),
(1, 'Научные', 'Science'), (3, 'Информационные', 'Information'), (2, 'Научные', 'Science'),
(1, 'Художественные', 'Picture'), (2, 'Публицистические', 'Public'), (3, 'Аналитические', 'Analytical');

insert into documents(doc_name, doc_author, doc_year, doc_istochnik, doc_keyword, doc_dateinput, annotation, cat_id, typedoc_id, path_id)
values ('Java', 'Tom Mathew', '1970', 'authent.pdf', 'java, junior', '2024-06-25', '', 4, 2, 1),
('Java', 'Tom Mathew', '1999', 'config.pdf', 'java, middle', '2024-06-25', 'middle', 5, 2, 1),
('Java', 'Tom Mathew', '2005', 'authent.pdf', 'java, senior', '2024-06-25', '', 4, 1, 1);

insert into typedocuments(typedoc_name)
values ('журнал'), ('книга'), ('презентация');

insert into pathsavepdf(path_name)
values ('D:\pdf');

insert into users(username, password, role)
values ('user', '$2y$10$CPCRLRXmdgugBC23KTkfR.419L6J3PGRUZ1y/SLKPoduPQWBSqKQq', 'USER'),
('admin', '$2y$10$9cCTtCpy/CFnNISlwGo4bOjJrb5kkFAJpeNNgSceAuh6n8GvpyeHe', 'ADMIN')