INSERT INTO books (name_book, year_publishing, number_pages, availability)
VALUES
('Рыбак и рыбка','2013/06/12',310,5),
('Дайтона','2005/10/10',250,7),
('Громов','1993/10/13',545,23),
('Мастер и Маргарита','1929/10/10',250,65),
('Мёртвые души','1842/10/10',270,46),
('Двенадцать стульев','1928/10/10',244,31),
('Собачье сердце','1925/10/10', 232,0),
('Три товарища','1936/10/10',413,35),
('Преступление и наказание','1866/10/10',522,4),
('Война и мир','1865/10/10',213,15),
('Отверженные','1862/10/10',513,0),
('Отцы и дети','1860/10/10', 541,70),
('Ревизор','1836/10/10',351,30);

INSERT INTO authors (last_name, city, date_of_birth)
VALUES
('Иванов Иван Иванович','Пенза','1958/11/15'),
('Иванова Татьяна Геннадьевна','Пенза','1965/10/15'),
('Пункарский Сергей Иванович','Пенза','1976/12/15'),
('Белов Степан Валерьевич','Пенза','1998/10/23'),
('Антонов Кирилл Степанович','Москва','1892/05/13'),
('Верин Инакентий Валентинович','Саранск','1948/10/23'),
('Ермаков Николай Валентинович','Нижний Ломов','1950/10/27'),
('Ёжиков Геннадий Анатольевич','Казань','1943/10/27'),
('Петров Виталий Сергеевич','Ульяновск','1968/10/15'),
('Юлсулов Ибрагим Валентинович','Петрозаводск','1973/12/31');

INSERT INTO fc_books_authors (id_author, id_book)
VALUES
(1,1),(1,2),
(2,1),(2,2),
(3,1),(3,2),(3,3),
(4,4),(4,5),(4,6),
(5,7),(5,8),(5,9),
(6,10),(6,11),(6,12),
(7,5),(7,1),(7,7),
(8,3),(8,6),(8,4);

INSERT INTO issue_status (status_name)
VALUES
('Погашено'),
('Частично погашено'),
('Не погашено');

INSERT INTO readers (last_name, phone_number, passport_data, date_of_birth)
VALUES
('Исмаилов Мухаммед Интаниславович', '+79374445121', '5312-542132', '1994/10/13'),
('Иванов Иван Иванович', '+79374446132', '5358-642431', '1992/12/17'),
('Иванова Татьяна Владимировна', '+79374443763', '5358-642441', '1995/12/17'),
('Степанова Виктория Ивановна', '+79374443732', '5358-642441', '1995/12/17'),
('Зюганов Кирилл Степанович', '+79376453281', '5451-562413', '1983/05/10');

INSERT INTO loan_of_books (id_books, id_readers, date_of_issue, issuance_period,
                            number_of_books_given_out)
VALUES
(1,1,'2021/10/13','2021/10/20',2),
(2,2,'2022/05/08','2022/05/25',5);

INSERT INTO return_of_books (id_readers, id_extradition, return_amount, return_date)
VALUES
(1,1,2,'2022/05/15'),
(2,2,1,'2022/10/12');

INSERT INTO books_returned_counter (quantity_returned, id_issue, id_extradition, id_return)
VALUES
(1,1,1,1),
(1,1,2,2);
