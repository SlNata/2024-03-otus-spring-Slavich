insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(text_comment, book_id)
values ('Comment_1', 1), ('Comment_2', 2), ('Comment_3', 3), ('Comment_4', 3);

insert into users(username, password, role)
values ('user', '$2y$10$CPCRLRXmdgugBC23KTkfR.419L6J3PGRUZ1y/SLKPoduPQWBSqKQq', 'USER'),
('admin', '$2y$10$9cCTtCpy/CFnNISlwGo4bOjJrb5kkFAJpeNNgSceAuh6n8GvpyeHe', 'ADMIN')
--логин и пароль написаны в readme