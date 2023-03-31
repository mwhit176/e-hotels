SET search_path = "e-hotels";

-- Indexing Part

-- We choose capacity, price, and view_type since these will be sorted and searched by users often.
-- They are all in the room table.

CREATE INDEX price_idx ON room (price);
CREATE INDEX capacity_idx ON room (capacity);
CREATE INDEX view_type_idx ON room (view_type);

CREATE VIEW rooms_per_area AS SELECT city, province_state, country, SUM(num_rooms) AS num_rooms FROM hotel GROUP BY city, province_state, country;
SELECT * FROM rooms_per_area;

CREATE VIEW hotel_room_capacity AS SELECT room_number, room_floor, capacity FROM room WHERE (chain_name = 'Wyndham' AND hotel_id = '0000000001');
SELECT * FROM hotel_room_capacity;