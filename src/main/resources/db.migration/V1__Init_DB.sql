CREATE SEQUENCE authors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE authors (
    id int8 NOT NULL DEFAULT nextval('authors_id_seq'),
    last_name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    primary key (id));

CREATE SEQUENCE books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE books (
    id int8 NOT NULL DEFAULT nextval('books_id_seq'),
    name_book varchar(255) NOT NULL,
    number_pages int4 NOT NULL,
    year_publishing TIMESTAMP NOT NULL,
    availability int4 NOT NULL,
    primary key (id));

CREATE TABLE fc_books_authors (
    id_author int8 NOT NULL,
    id_book int8 NOT NULL,
    primary key (id_book, id_author));

CREATE SEQUENCE issue_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE issue_status (
    id int8 NOT NULL DEFAULT nextval('issue_status_id_seq'),
    status_name VARCHAR(255) NOT NULL,
    primary key (id));

CREATE SEQUENCE loan_of_books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE loan_of_books (
    id int8 NOT NULL DEFAULT nextval('loan_of_books_id_seq'),
    id_books int8 NOT NULL,
    id_readers int8 NOT NULL,
    date_of_issue TIMESTAMP NOT NULL,
    issuance_period TIMESTAMP NOT NULL,
    number_of_books_given_out int4 NOT NULL,
    primary key (id));

CREATE SEQUENCE readers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE readers (
    id int8 NOT NULL DEFAULT nextval('readers_id_seq'),
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    passport_data VARCHAR(255) NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    primary key (id));

CREATE SEQUENCE return_of_books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE return_of_books (
    id int8 NOT NULL DEFAULT nextval('return_of_books_id_seq'),
    id_readers int8 NOT NULL,
    id_extradition int8 NOT NULL,
    return_amount int4 NOT NULL,
    return_date TIMESTAMP NOT NULL,
    primary key (id));

CREATE SEQUENCE books_returned_counter_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE books_returned_counter (
    id int8 NOT NULL DEFAULT nextval('books_returned_counter_id_seq'),
    quantity_returned int4 NOT NULL,
    id_extradition int8 NOT NULL,
    id_issue int8 NOT NULL,
    id_return int8 NOT NULL,
    primary key (id));


ALTER TABLE if EXISTS fc_books_authors
ADD CONSTRAINT books_fk
FOREIGN KEY (id_book) REFERENCES books;

ALTER TABLE if EXISTS fc_books_authors
ADD CONSTRAINT authors_fk
FOREIGN KEY (id_author) REFERENCES authors;

ALTER TABLE if EXISTS loan_of_books
ADD CONSTRAINT books_fk
FOREIGN KEY (id_books) REFERENCES books;

ALTER TABLE if EXISTS loan_of_books
ADD CONSTRAINT readers_fk
FOREIGN KEY (id_readers) REFERENCES readers;

ALTER TABLE if EXISTS return_of_books
ADD CONSTRAINT readers_fk
FOREIGN KEY (id_readers) REFERENCES readers;

ALTER TABLE if EXISTS return_of_books
ADD CONSTRAINT extradition_fk
FOREIGN KEY (id_extradition) REFERENCES loan_of_books;

ALTER TABLE if EXISTS books_returned_counter
ADD CONSTRAINT extradition_fk
FOREIGN KEY (id_extradition) REFERENCES loan_of_books;

ALTER TABLE if EXISTS books_returned_counter
ADD CONSTRAINT issue_fk
FOREIGN KEY (id_issue) REFERENCES issue_status;

ALTER TABLE if EXISTS books_returned_counter
ADD CONSTRAINT return_fk
FOREIGN KEY (id_return) REFERENCES return_of_books;

CREATE OR REPLACE FUNCTION loan_of_books_insert_and_update_func() RETURNS TRIGGER AS $$
DECLARE
	old_availability INT;
	old_id_issue INT;
	old_quantity_returned INT;
BEGIN
	IF TG_OP = 'INSERT' THEN
		--Ищем количество книг в наличии.
		SELECT availability INTO old_availability FROM books WHERE id=NEW.id_books;

		IF (old_availability) IS NULL THEN
			--Выдаем сообщение, если книга не найдена.
			RAISE NOTICE 'Книга под id = % не найдена.', NEW.id_books;
			RETURN NULL;
		--Если найдена, то проверяем, достаточно ли книг в наличии для выдачи читателю.
		ELSE IF(old_availability>=NEW.number_of_books_given_out) THEN
			UPDATE books SET availability = availability-NEW.number_of_books_given_out WHERE id=NEW.id_books;
				ELSE
					RAISE NOTICE 'Количество книг под id = % недостаточно для добавления записи.', NEW.id_books;
					RETURN NULL;
			END IF;
		END IF;
	RETURN NEW;

	ELSE IF TG_OP = 'UPDATE' THEN
		--Ищем количество книг в наличии.
		SELECT availability INTO old_availability FROM books WHERE id=NEW.id_books;
		IF(SELECT NOT EXISTS(SELECT availability FROM books WHERE id=NEW.id_books)) THEN
            RAISE NOTICE 'Книга под id = % не найдена.', NEW.id_books;
            RETURN NULL;
                ELSE
			SELECT quantity_returned INTO old_quantity_returned FROM books_returned_counter WHERE id_extradition=NEW.id;
			IF((old_quantity_returned) IS NOT NULL) THEN
				--Если новое количество меньше, чем уже вернули книг.
				IF(old_quantity_returned>NEW.number_of_books_given_out) THEN
					RAISE NOTICE 'Количество книг под id = % уже было возвращено. Ошибка обновления.', NEW.id_books;
					RETURN NULL;
				--Если количество совпадает выдачей, погашаем выдачу.
				ELSE IF(old_quantity_returned=NEW.number_of_books_given_out) THEN
					RAISE NOTICE 'Запись успешно обновлена! Выдача полностью погашена.';
					UPDATE books SET availability = availability + (OLD.number_of_books_given_out-NEW.number_of_books_given_out) WHERE id=NEW.id_books;
					UPDATE books_returned_counter SET id_issue = 1 WHERE id_extradition=NEW.id;
					RETURN NEW;
				--Если количество меньше и количество книг достаточно.
				ELSE IF(OLD.number_of_books_given_out>NEW.number_of_books_given_out) AND (old_availability + (OLD.number_of_books_given_out-NEW.number_of_books_given_out))>0 THEN
					RAISE NOTICE 'Запись обновлена. Количество книг у книги под id = % обновлено. Количество книг увеличилось', NEW.id_books;
					UPDATE books SET availability = availability + (OLD.number_of_books_given_out-NEW.number_of_books_given_out) WHERE id=NEW.id_books;
					UPDATE books_returned_counter SET id_issue = 2 WHERE id_extradition=NEW.id;
					RETURN NEW;
				ELSE IF(OLD.number_of_books_given_out<NEW.number_of_books_given_out) AND (old_availability + (OLD.number_of_books_given_out-NEW.number_of_books_given_out))>0 THEN
					RAISE NOTICE 'Запись обновлена. Количество книг у книги под id = % обновлено. Количество книг уменьшилось', NEW.id_books;
					UPDATE books SET availability = availability - (NEW.number_of_books_given_out-OLD.number_of_books_given_out) WHERE id=NEW.id_books;
				UPDATE books_returned_counter SET id_issue = 2 WHERE id_extradition=NEW.id;
				RETURN NEW;
					END IF;
					END IF;
					END IF;
					END IF;
					--Проверяем, необходимо убавить или прибавить количество книг. После AND идет проверка, достаточно ли книг для обновления записи.
			ELSE
				IF(OLD.number_of_books_given_out>NEW.number_of_books_given_out) AND (old_availability + (OLD.number_of_books_given_out-NEW.number_of_books_given_out))>0 THEN
					RAISE NOTICE 'Запись обновлена. Возвратов по книге не было. Количество книг у книги под id = % обновлено. Количество книг увеличилось', NEW.id_books;
					UPDATE books SET availability = availability + (OLD.number_of_books_given_out-NEW.number_of_books_given_out) WHERE id=NEW.id_books;
					RETURN NEW;
				ELSE IF(OLD.number_of_books_given_out<NEW.number_of_books_given_out) AND (old_availability + (OLD.number_of_books_given_out-NEW.number_of_books_given_out))>0 THEN
						RAISE NOTICE 'Запись обновлена. Возвратов по книге не было. Количество книг у книги под id = % обновлено. Количество книг уменьшилось', NEW.id_books;
						UPDATE books SET availability = availability - (NEW.number_of_books_given_out-OLD.number_of_books_given_out) WHERE id=NEW.id_books;
						RETURN NEW;
				ELSE IF(OLD.number_of_books_given_out=NEW.number_of_books_given_out) AND (old_availability + (OLD.number_of_books_given_out-NEW.number_of_books_given_out))>0 THEN
						RETURN NEW;
				ELSE
					--Иначе книг для выдачи недостаточно.
					RAISE NOTICE 'Количество книг под id = % недостаточно для обновления записи.', NEW.id_books;
				RETURN NULL;
				END IF;
				END IF;
				END IF;
				END IF;
				END IF;
				END IF;
				END IF;
				RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER loan_of_books_insert_and_update_func
BEFORE INSERT OR UPDATE ON loan_of_books FOR EACH ROW EXECUTE PROCEDURE loan_of_books_insert_and_update_func ();

CREATE OR REPLACE FUNCTION return_of_books_insert_func() RETURNS TRIGGER AS $$
DECLARE
old_id_extradition INT;
old_number_of_books_given_out INT;
old_quantity_returned INT;
old_id_issue INT;
old_id_books INT;
old_date_issue DATE;
BEGIN
	IF TG_OP = 'INSERT' THEN
	SELECT id INTO old_id_extradition FROM books_returned_counter WHERE id=NEW.id_extradition;
	SELECT number_of_books_given_out INTO old_number_of_books_given_out FROM loan_of_books WHERE id=NEW.id_extradition;
	SELECT id_books INTO old_id_books FROM loan_of_books WHERE id=NEW.id_extradition;
	SELECT quantity_returned INTO old_quantity_returned FROM books_returned_counter WHERE id_extradition=NEW.id_extradition;
	SELECT date_of_issue INTO old_date_issue FROM loan_of_books WHERE id=NEW.id_extradition;
	--Проверяем, существует ли уже такая выдача.
	IF (old_id_extradition) IS NULL THEN
		--Если возвращаемое количество совпадает с выдачей, делаем полное погашение.
		IF(NEW.return_date<old_date_issue) THEN
			RAISE NOTICE 'Выдача была позже, чем текущий возврат. Проверьте дату!.';
			--Удаляем запись из return_of_books, так как запись не подходит.
			DELETE FROM return_of_books WHERE id = NEW.id;
			RETURN NULL;
		ELSE IF(old_number_of_books_given_out=NEW.return_amount) THEN
			--Если возвращаемое количество полностью погашает выдачу, то создаем запись со статусом 1 (выдача полность погашена) и возвращаем количество книг в books.
			INSERT INTO books_returned_counter(quantity_returned,id_issue,id_extradition,id_return)
			VALUES(NEW.return_amount,1,NEW.id_extradition,NEW.id);
			UPDATE books SET availability = availability + NEW.return_amount WHERE id=old_id_books;
			RAISE NOTICE 'Выдача погашена полностью!';
		--Если превышено количество возвращаемых книг, делаем откат.
		ELSE IF(NEW.return_amount>old_number_of_books_given_out) THEN
			RAISE NOTICE 'Превышено количество книг для возврата.';
			--Удаляем запись из return_of_books, так как запись не подходит.
			DELETE FROM return_of_books WHERE id = NEW.id;
			RETURN NULL;
			--Иначе делаем обычную вставку со статусом (частично погашено).
		ELSE IF(NEW.return_amount<old_number_of_books_given_out) THEN
			INSERT INTO books_returned_counter(quantity_returned,id_issue,id_extradition,id_return)
			VALUES(NEW.return_amount,2,NEW.id_extradition,NEW.id);
			UPDATE books SET availability = availability + NEW.return_amount WHERE id=old_id_books;
			RAISE NOTICE 'Книга/ги успешно возвращены/на. Осталось вернуть % книг.', old_number_of_books_given_out - NEW.return_amount;
			RETURN NEW;
		END IF;
		END IF;
		END IF;
		END IF;
	ELSE
	SELECT id_issue INTO old_id_issue FROM books_returned_counter WHERE id_extradition=NEW.id_extradition;
	--Если книга существует, делаем проверку, не погашена ли выдача.
	IF(old_id_issue=1) THEN
		RAISE NOTICE 'Возврат не требуется. Выдача полностью погашена.';
		--Удаляем запись из return_of_books, так как запись не подходит.
		DELETE FROM return_of_books WHERE id = NEW.id;
		RETURN NULL;
	ELSE IF(NEW.return_date<old_date_issue) THEN
		RAISE NOTICE 'Выдача была позже, чем текущий возврат. Проверьте дату!.';
		--Удаляем запись из return_of_books, так как запись не подходит.
		DELETE FROM return_of_books WHERE id = NEW.id;
		RETURN NULL;
	--Если возвращаемое количество превышает выдаваемое.
	ELSE IF(NEW.return_amount+old_quantity_returned>old_number_of_books_given_out) THEN
		RAISE NOTICE 'Превышено количество книг для возврата.';
		--Удаляем запись из return_of_books, так как запись не подходит.
		DELETE FROM return_of_books WHERE id = NEW.id;
		RETURN NULL;
	--Все условия для добавления удовлетворяют. Производим возврат.
	ELSE IF(NEW.return_amount+old_quantity_returned<old_number_of_books_given_out) THEN
		UPDATE books_returned_counter SET quantity_returned = quantity_returned + NEW.return_amount WHERE id_extradition=NEW.id_extradition;
		UPDATE books SET availability = availability + NEW.return_amount WHERE old_id_extradition=NEW.id_extradition;
		RAISE NOTICE 'Книга/ги успешно возвращены/на. Осталось вернуть % книг.', old_number_of_books_given_out - (NEW.return_amount + old_quantity_returned);
		RETURN NEW;
	--Если возвращаемое количество совпадает с выдаваемым.
	ELSE IF(NEW.return_amount+old_quantity_returned=old_number_of_books_given_out) THEN
		UPDATE books_returned_counter SET quantity_returned = quantity_returned + NEW.return_amount WHERE id_extradition=NEW.id_extradition;
		UPDATE books_returned_counter SET id_issue = 1 WHERE id_extradition=NEW.id_extradition;
		UPDATE books SET availability = availability + NEW.return_amount WHERE id=old_id_books;
		RAISE NOTICE 'Выдача погашена полностью!';
		RETURN NEW;
		END IF;
		END IF;
		END IF;
		END IF;
		END IF;
		END IF;
RETURN NEW;
END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER return_of_books_insert_func
AFTER INSERT OR UPDATE ON return_of_books FOR EACH ROW EXECUTE PROCEDURE return_of_books_insert_func ();





