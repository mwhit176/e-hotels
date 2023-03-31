SET search_path = "e-hotels";

-- Referential Integrity for `chain`
CREATE FUNCTION update_num_hotels()
RETURNS TRIGGER AS
$$
BEGIN
	IF NOT OLD IS NULL THEN
		UPDATE chain
		SET num_hotels = (SELECT COUNT(*) FROM hotel WHERE chain_name = chain.chain_name)
		WHERE chain_name = OLD.chain_name;
	END IF;
	IF NOT NEW IS NULL THEN
		UPDATE chain
		SET num_hotels = (SELECT COUNT(*) FROM hotel WHERE chain_name = chain.chain_name)
		WHERE chain_name = NEW.chain_name;
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_num_hotels AFTER INSERT OR DELETE OR UPDATE ON hotel
FOR EACH ROW
EXECUTE FUNCTION update_num_hotels();

-- We relax the "customer must have a registration" constraint.
-- In the context of a relational database, this creates a circular dependency.
-- This creates more pain than necessary; relaxation of the constraint is the best solution.

-- Referential Integrity for `hotel`
CREATE FUNCTION update_num_rooms()
RETURNS TRIGGER AS
$$
BEGIN
	IF NOT OLD IS NULL THEN
		UPDATE hotel
		SET num_rooms = (SELECT COUNT(*) FROM room WHERE (chain_name = hotel.chain_name AND hotel_id = hotel.hotel_id))
		WHERE (chain_name = OLD.chain_name AND hotel_id = OLD.hotel_id);
	END IF;
	IF NOT NEW IS NULL THEN
		UPDATE hotel
		SET num_rooms = (SELECT COUNT(*) FROM room WHERE (chain_name = hotel.chain_name AND hotel_id = hotel.hotel_id))
		WHERE (chain_name = NEW.chain_name AND hotel_id = NEW.hotel_id);
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_num_rooms AFTER INSERT OR DELETE OR UPDATE ON room
FOR EACH ROW
EXECUTE FUNCTION update_num_rooms();

-- For now, the updating of bookings is not supported.
CREATE FUNCTION on_booking_inserted()
RETURNS TRIGGER AS
$$
BEGIN
	-- Ensure that the start date of the added booking is after the current date.
	IF (NEW.start_date < CURRENT_DATE) THEN
		RAISE EXCEPTION 'The start date of a booking must be after the current date.';
	END IF;
	-- Ensure that the end date of the added booking is after the start date.
	IF (NEW.start_date >= NEW.end_date) THEN
		RAISE EXCEPTION 'The start date of a booking must be before the end date.';
	END IF;
	-- Ensure the added booking does not overlap with any existing booking (or renting) for the same room
	IF ((SELECT COUNT(*) FROM booking WHERE (
			chain_name = NEW.chain_name AND
			hotel_id = NEW.hotel_id AND
			room_number = NEW.room_number AND
			room_floor = NEW.room_floor AND
			(start_date, end_date) OVERLAPS (NEW.start_date, NEW.end_date))) > 0) THEN
		RAISE EXCEPTION 'No two bookings for the same room may have overlapping dates.';
	END IF;
	IF ((SELECT COUNT(*) FROM renting WHERE (
			chain_name = NEW.chain_name AND
			hotel_id = NEW.hotel_id AND
			room_number = NEW.room_number AND
			room_floor = NEW.room_floor AND
			(start_date, end_date) OVERLAPS (NEW.start_date, NEW.end_date))) > 0) THEN
		RAISE EXCEPTION 'A booking for a room must not overlap the current renting period.';
	END IF;
	-- Create an archive entry for the booking.
	INSERT INTO booking_archive VALUES (NEW.chain_name, NEW.hotel_id, NEW.room_number, NEW.room_floor, NEW.customer_SIN_SSN, NEW.start_date, NEW.end_date);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER booking_inserted BEFORE INSERT ON booking
FOR EACH ROW
EXECUTE FUNCTION on_booking_inserted();

-- For now, the updating of rentings is not supported.
CREATE FUNCTION on_renting_inserted()
RETURNS TRIGGER AS
$$
BEGIN
	-- Ensure that the start date of the added renting is the current date.
	IF (NEW.start_date != CURRENT_DATE) THEN
		RAISE EXCEPTION 'The start date of a renting must be the current date.';
	END IF;
	-- Ensure that the end date of the added renting is after the start date.
	IF (NEW.start_date >= NEW.end_date) THEN
		RAISE EXCEPTION 'The end date of a renting must be after the start date.';
	END IF;
	-- Check if a booking exists for this renting. If so, delete the booking.
	-- It should be impossible to have a situation where a booking gets deleted
	-- and the renting ends up invalid.
	IF ((SELECT COUNT(*) FROM booking WHERE (
			chain_name = NEW.chain_name AND
			hotel_id = NEW.hotel_id AND
			room_number = NEW.room_number AND
			room_floor = NEW.room_floor AND
			customer_sin_ssn = NEW.customer_sin_ssn AND
			start_date = NEW.start_date AND
			end_date = NEW.end_date)) > 0) THEN
		DELETE FROM booking WHERE (
				chain_name = NEW.chain_name AND
				hotel_id = NEW.hotel_id AND
				room_number = NEW.room_number AND
				room_floor = NEW.room_floor AND
				customer_sin_ssn = NEW.customer_sin_ssn AND
				start_date = NEW.start_date AND
				end_date = NEW.end_date);
	END IF;
	-- Ensure the added renting does not overlap with any existing renting or booking for the same room
	IF ((SELECT COUNT(*) FROM booking WHERE (
			chain_name = NEW.chain_name AND
			hotel_id = NEW.hotel_id AND
			room_number = NEW.room_number AND
			room_floor = NEW.room_floor AND
			(start_date, end_date) OVERLAPS (NEW.start_date, NEW.end_date))) > 0) THEN
		RAISE EXCEPTION 'The renting period must not overlap with a booking.';
	END IF;
	IF ((SELECT COUNT(*) FROM renting WHERE (
			chain_name = NEW.chain_name AND
			hotel_id = NEW.hotel_id AND
			room_number = NEW.room_number AND
			room_floor = NEW.room_floor AND
			(start_date, end_date) OVERLAPS (NEW.start_date, NEW.end_date))) > 0) THEN
		RAISE EXCEPTION 'A renting cannot be issued if another renting is currently in progress.';
	END IF;
	-- Now we know that the renting is valid, so delete any existing renting for the room.
	DELETE FROM renting WHERE (chain_name = NEW.chain_name AND hotel_id = NEW.hotel_id AND room_number = NEW.room_number AND room_floor = NEW.room_floor);
	-- Create an archive entry for the renting.
	INSERT INTO renting_archive VALUES (NEW.chain_name, NEW.hotel_id, NEW.room_number, NEW.room_floor, NEW.customer_SIN_SSN, NEW.start_date, NEW.end_date);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER renting_inserted BEFORE INSERT ON renting
FOR EACH ROW
EXECUTE FUNCTION on_renting_inserted();



