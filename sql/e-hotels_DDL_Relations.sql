SET search_path = "e-hotels";

CREATE TABLE chain(
	chain_name VARCHAR(20),
	num_hotels INT,
	PRIMARY KEY (chain_name)
);

CREATE TABLE chain_office_addresses(
	chain_name VARCHAR(20),
	street_address VARCHAR(100),
	city VARCHAR(20),
	province_state VARCHAR(20),
	country VARCHAR(100),
	zip_postal_code VARCHAR(100),
	PRIMARY KEY (chain_name, street_address, city, province_state, country, zip_postal_code),
	FOREIGN KEY (chain_name) REFERENCES chain ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE chain_phone_numbers(
	chain_name VARCHAR(20),
	phone_number VARCHAR(20),
	PRIMARY KEY (chain_name, phone_number),
	FOREIGN KEY (chain_name) REFERENCES chain ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK (phone_number ~ '^[0-9]*$' AND LENGTH(phone_number) >= 7)
);

CREATE TABLE chain_email_addresses(
	chain_name VARCHAR(20),
	email_address VARCHAR(100),
	PRIMARY KEY (chain_name, email_address),
	FOREIGN KEY (chain_name) REFERENCES chain ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK (email_address ~ '^.*@.*$')
);

CREATE TABLE customer(
	first_name VARCHAR(20) NOT NULL,
	middle_name VARCHAR(20),
	last_name VARCHAR(20) NOT NULL,
	customer_SIN_SSN CHAR(9),
	street_address VARCHAR(100) NOT NULL,
	city VARCHAR(20) NOT NULL,
	province_state VARCHAR(20) NOT NULL,
	country VARCHAR(100) NOT NULL,
	zip_postal_code VARCHAR(100) NOT NULL,
	PRIMARY KEY (customer_SIN_SSN)
);

CREATE TABLE registration(
	chain_name VARCHAR(20),
	customer_SIN_SSN CHAR(9),
	registration_date DATE NOT NULL,
	PRIMARY KEY (chain_name, customer_SIN_SSN),
	FOREIGN KEY (chain_name) REFERENCES chain ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (customer_SIN_SSN) REFERENCES customer ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE hotel(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	street_address VARCHAR(100) NOT NULL,
	city VARCHAR(20) NOT NULL,
	province_state VARCHAR(20) NOT NULL,
	country VARCHAR(100) NOT NULL,
	zip_postal_code VARCHAR(100) NOT NULL,
	num_rooms INT NOT NULL,
	num_stars INT NOT NULL,
	email_address VARCHAR(100) NOT NULL,
	emp_SIN_SSN CHAR(9),
	PRIMARY KEY (chain_name, hotel_id),
	FOREIGN KEY (chain_name) REFERENCES chain ON DELETE CASCADE ON UPDATE CASCADE,
	UNIQUE (street_address, city, province_state, country, zip_postal_code),
	CHECK (num_stars >= 0 AND num_stars <= 5 AND email_address ~ '^.*@.*$')
);

CREATE TABLE hotel_phone_numbers(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	phone_number VARCHAR(20),
	PRIMARY KEY (chain_name, hotel_id, phone_number),
	FOREIGN KEY (chain_name, hotel_id) REFERENCES hotel ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK (phone_number ~ '^[0-9]*$' AND LENGTH(phone_number) >= 7)
);

CREATE TABLE employee(
	first_name VARCHAR(20) NOT NULL,
	middle_name VARCHAR(20),
	last_name VARCHAR(20) NOT NULL,
	emp_SIN_SSN CHAR(9),
	street_address VARCHAR(100) NOT NULL,
	city VARCHAR(20) NOT NULL,
	province_state VARCHAR(20) NOT NULL,
	country VARCHAR(100) NOT NULL,
	zip_postal_code VARCHAR(100) NOT NULL,
	role_position VARCHAR(100) NOT NULL,
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	PRIMARY KEY (emp_SIN_SSN),
	FOREIGN KEY (chain_name, hotel_id) REFERENCES hotel ON DELETE SET NULL ON UPDATE CASCADE
);

-- Add the foreign key to `hotel` from `employee`; the manager relationship.
ALTER TABLE hotel ADD FOREIGN KEY (emp_SIN_SSN) REFERENCES employee ON DELETE SET NULL;

CREATE TYPE view_enum AS ENUM('Sea', 'Mountain');

CREATE TABLE room(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	room_number INT,
	room_floor INT,
	price DECIMAL(7, 2) NOT NULL,
	capacity INT NOT NULL,
	can_be_extended BOOL NOT NULL,
	view_type view_enum NOT NULL,
	PRIMARY KEY (chain_name, hotel_id, room_number, room_floor),
	FOREIGN KEY (chain_name, hotel_id) REFERENCES hotel ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE booking(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	room_number INT,
	room_floor INT,
	customer_SIN_SSN CHAR(9),
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	PRIMARY KEY (chain_name, hotel_id, room_number, room_floor, customer_SIN_SSN),
	FOREIGN KEY (chain_name, hotel_id, room_number, room_floor) REFERENCES room ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (customer_SIN_SSN) REFERENCES customer ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE renting(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	room_number INT,
	room_floor INT,
	customer_SIN_SSN CHAR(9),
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	PRIMARY KEY (chain_name, hotel_id, room_number, room_floor, customer_SIN_SSN),
	FOREIGN KEY (chain_name, hotel_id, room_number, room_floor) REFERENCES room ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (customer_SIN_SSN) REFERENCES customer ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE room_problems_damages(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	room_number INT,
	room_floor INT,
	problem_damage VARCHAR,
	PRIMARY KEY (chain_name, hotel_id, room_number, room_floor, problem_damage),
	FOREIGN KEY (chain_name, hotel_id, room_number, room_floor) REFERENCES room ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE room_amenities(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	room_number INT,
	room_floor INT,
	amenity VARCHAR,
	PRIMARY KEY (chain_name, hotel_id, room_number, room_floor, amenity),
	FOREIGN KEY (chain_name, hotel_id, room_number, room_floor) REFERENCES room ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE booking_archive(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	room_number INT,
	room_floor INT,
	customer_SIN_SSN CHAR(9),
	start_date DATE,
	end_date DATE,
	PRIMARY KEY (chain_name, hotel_id, room_number, room_floor, customer_SIN_SSN, start_date, end_date)
);

CREATE TABLE renting_archive(
	chain_name VARCHAR(20),
	hotel_id CHAR(10),
	room_number INT,
	room_floor INT,
	customer_SIN_SSN CHAR(9),
	start_date DATE,
	end_date DATE,
	PRIMARY KEY (chain_name, hotel_id, room_number, room_floor, customer_SIN_SSN, start_date, end_date)
);



