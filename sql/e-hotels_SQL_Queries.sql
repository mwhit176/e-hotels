SET search_path = "e-hotels";

-- Query Part

-- Adding a Customer (Name, SIN, Address)
INSERT INTO customer VALUES ('firstname', 'middlename', 'lastname', 'sin/ssn', 'address', 'city', 'province/state', 'country', 'zip');
INSERT INTO registration VALUES ('chain_name', 'sin/ssn', CURRENT_DATE);

-- Deleting a Customer
DELETE FROM customer WHERE (customer_sin_ssn = 'SIN');

-- Adding an Employee
INSERT INTO employee VALUES ('first', 'middle', 'last', 'sin', 'addr', 'city', 'state', 'country', 'zip', 'role', 'chain', 'hotelid')

-- Adding a Booking
INSERT INTO booking VALUES ('chain', 'hotelid', 'roomnum', 'floor', 'sin', 'startdate', 'enddate');

-- Adding a Renting
INSERT INTO renting VALUES ('chain', 'hotelid', 'roomnum', 'floor', 'sin', 'startdate', 'enddate');

-- Adding a Chain
INSERT INTO chain VALUES ('chain', 0);
INSERT INTO chain_email_addresses VALUES ('chain', 'em@il');
INSERT INTO chain_office_addresses VALUES ('chain', 'addr', 'city', 'state', 'country', 'zip');
INSERT INTO chain_phone_numbers VALUES ('chain', 'phone');
-- Deleting a Chain
DELETE FROM chain WHERE (chain_name = 'chain');

-- Adding a Hotel
INSERT INTO hotel VALUES ('chain', 'hotelid', 'addr', 'city', 'state', 'country', 'zip', 0, 'stars', 'em@il', 'mgr_sin')
INSERT INTO hotel_phone_numbers VALUES ('chain', 'hotelid', 'phone');
-- Updating the Manager
UPDATE TABLE hotel SET emp_sin_ssn = 'new_mgr_sin' WHERE (chain_name = 'chain', hotel_id = 'hotel');
-- Deleting a Hotel
DELETE FROM hotel WHERE (chain_name = 'chain' AND hotel_id = 'hotelid');

-- Adding a Room
SELECT * FROM room_problems_damages;
INSERT INTO room VALUES ('chain', 'hotelid', 'roomnum', 'floor', 'price', 'capacity', 'extend', 'view');
INSERT INTO room_amenities VALUES ('chain', 'hotelid', 'roomnum', 'floor', 'amenity');
INSERT INTO room_problems_damages VALUES ('chain', 'hotelid', 'roomnum', 'floor', 'problem');
-- Deleting a Room
DELETE FROM room WHERE (chain_name = 'chain', hotel_id = 'hotelid', room_number = 'roomnum', room_floor = 'floor');



