-- Property Tags --
INSERT INTO predefined_tag (parent_id, text)
    VALUES (null, 'Property Tags');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Bar');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Beach');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Beach'), 'Time of Day');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day'), 'Dawn/Sunrise');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day'), 'Day');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day'), 'Dusk/Sunset');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day'), 'Evening/Night');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Business/Events');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Business/Events'), 'Business Services/Center');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Business/Events'), 'Event');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Event'), 'Banquet');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Event'), 'Conference');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Event'), 'Exhibition');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Event'), 'Group Event');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Event'), 'Trade Show/Conference');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Event'), 'Wedding');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Business/Events'), 'Fax');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Business/Events'), 'Meeting Room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meeting Room'), 'Auditorium/Amphitheater');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meeting Room'), 'Ballroom');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meeting Room'), 'Boardroom');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meeting Room'), 'Conference Room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meeting Room'), 'Meeting Facilities');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meeting Room'), 'Meeting Reception');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meeting Room'), 'Seating');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Cabaret');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Chevron');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Circle of Chairs');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Classroom');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Clusters');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Cocktail');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Crescent Rounds');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Dancefloor and Staging');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Double U');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Empty');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Exhibit Hall with Booths');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'General Session');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Highboys');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Hollow Square Style');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Imperial');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Rounds');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Theater');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'Theater in the Round');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Seating'), 'U Shape');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Business/Events'), 'Office');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Business/Events'), 'Printer');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Dining');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining'), 'Cuisine');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Cuisine'), 'American');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Cuisine'), 'Asian');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Cuisine'), 'Continental');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Cuisine'), 'English/Irish');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Cuisine'), 'French');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Cuisine'), 'Indian');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Cuisine'), 'Italian');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining'), 'Dining Area');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'BBQ/Picnic Area');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Breakfast Area');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Buffet');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Café');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Coffee shop');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Food Court');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Grill/Pub');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Restaurant');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Restaurant Feature');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining Area'), 'Snack Bar');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining'), 'Food/Drink');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Coffee Service');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Drink');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Drink - Alcoholic');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Drink - Non-Alcoholic');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Food');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Grocery store/supermarket');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Wine');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Wine Cellar');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Food/Drink'), 'Wine Tasting');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Dining'), 'Meals');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meals'), 'Breakfast');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meals'), 'Dinner');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Meals'), 'Lunch');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Exterior View of Building');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Courtyard');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Entrance');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Featured View');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Garden');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Gazebo');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Grounds');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Lake');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Natural landscape');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Street');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Sundeck');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'Time of Day');
-- INSERT INTO predefined_tag (parent_id, text)
--     VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day'), 'Dawn/Sunrise');
-- INSERT INTO predefined_tag (parent_id, text)
--     VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day'), 'Day');
-- INSERT INTO predefined_tag (parent_id, text)
--     VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day'), 'Dusk/Sunset');
-- INSERT INTO predefined_tag (parent_id, text)
--     VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day'), 'Evening/Night');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Exterior View of Building'), 'View From');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From'), 'Above');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From'), 'Back');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From'), 'Front');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From'), 'Satellite');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From'), 'Street');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Fitness Center');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Lobby');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Lobby'), 'Atrium');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Lobby'), 'Check-in/out Kiosk');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Lobby'), 'Concierge');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Lobby'), 'Entrance');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Lobby'), 'Reception');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Lobby'), 'Sitting area');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Miscellaneous');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Accessibility');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Décor detail');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Hallway');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Logo');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Logo'), 'Brand Logo');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Logo'), 'Certificate/award');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Logo'), 'Property logo or sign');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Parking');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Parking'), 'Parking - RV/Truck');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Pets');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Service');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Staircase');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Valet');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Miscellaneous'), 'Vending machine');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool'), 'Children''s Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool'), 'Indoor Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool'), 'Infinity Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Infinity Pool'), 'Lap Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool'), 'Outdoor Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool'), 'Waterfall');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool'), 'Waterslide');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool'), 'Wave Pool');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Property Amenity');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Casino');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Chapel/Place of worship');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Communal Kitchen');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Communal lounge/TV room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Fireplace');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Fountain');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Gift Shop');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Laundry Facilities');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Library');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Loyalty Program Lounge/Floor');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Pantry/Convenience Store');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Patio/Terrace');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Shuttle - Airport/Seaport');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Shuttle - City/Other');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Amenity'), 'Solarium');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Property Tags'), 'Spa');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Bathhouse/Turkish Bath');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Hot springs');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Hot Tub');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Massage');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Massage room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Salon');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Salon'), 'Salon - Hair');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Salon'), 'Salon - Nail');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Sauna');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Spa Reception');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Spa Treatment');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Spa Treatment Room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Spa'), 'Steam Room');


-- Guest Room Tags --
INSERT INTO predefined_tag (parent_id, text)
    VALUES (null, 'Guest Room Tags');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Accessibility');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Bathroom');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bathroom'), 'Amenities');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bathroom'), 'Shower');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bathroom'), 'Sink');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bathroom'), 'Toilet');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bathroom'), 'Tub');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bathroom'), 'Tub - Deep Soaking');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bathroom'), 'Tub - Jetted');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Bed');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bed'), 'Bedding');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bed'), 'Bunk bed');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bed'), 'Cot/Rollaway Bed');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bed'), 'Crib');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bed'), 'King bed');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bed'), 'Queen bed');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bed'), 'Sofabed');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Dining area');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Guest Room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room'), 'Guest Room - Whole Room');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Guest Room Amenity');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Balcony');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Business Facilities');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Crib');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Coffee/tea facilities');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Décor detail');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Fireplace');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Full Bar');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Furnishings');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Hot Tub');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Kitchen/Kitchenette');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Minibar');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Mini-Refrigerator');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Patio/Terrace');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Refrigerator');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Safe');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'Telephone');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Amenity'), 'TV and multimedia');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Living Area');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Miscellaneous');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Pets');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Room Theme');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Theme'), 'Children');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Theme'), 'Family');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Theme'), 'Honeymoon');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Guest Room Tags'), 'Room Type');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Apartment');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Budget');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Deluxe');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Double');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Quad');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Single');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Standard');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Superior');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Triple');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Sitting Area');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Room Type'), 'Suite');


-- Activity Tags --
INSERT INTO predefined_tag (parent_id, text)
    VALUES (null, 'Activity Tags');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Activity Tags'), 'Activities (General)');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Activity Tags'), 'Entertainment');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Entertainment'), 'Band');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Entertainment'), 'Cinema');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Entertainment'), 'Comedy');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Entertainment'), 'Disc Jockey');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Entertainment'), 'Nightclub');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Entertainment'), 'Theater Show');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Activity Tags'), 'Interests');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Adventure Travel');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Amusement Parks');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Architecture');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Arts and Entertainment');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Bird watching');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'College Visits');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Computers/Internet/Social Media');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Cooking and Culinary');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Ecotourism');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Education');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Escape');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Experiential Travel');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Family Travel');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Gardening/Gardens');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Health and Well-being');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Historical Vacations');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Hot Air Ballooning');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Luxury Travel');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Mountain Vacations');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Museums');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Music');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'National Parks');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Other');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Photography');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Pop Culture');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Road Trips');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Romantic Getaways');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Shopping');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Single Parent Travel');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Whale Watching');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Interests'), 'Vineyards');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Activity Tags'), 'Local Area/Attractions');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Bar');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Bar' and pt.parent_id =
                                                                           (select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions')),
            'Bar - Sports Bar');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Beach');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Casino');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'City');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Landmark');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Local Attraction');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Mountains');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Ocean');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'River');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Riverwalk');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Skyline');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Sports Stadium');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Auto Racing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Baseball');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Basketball');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Cricket');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Football');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Hockey');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Horse Racing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Olympic');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Rugby');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Stadium'), 'Soccer');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Local Area/Attractions'), 'Waterfront');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Activity Tags'), 'Recreation');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Bowling');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Cycling');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Dancing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Fitness Center');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Game room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Game room'), 'Arcade');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Game room'), 'Billiards');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Game room'), 'Darts');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Game room'), 'Ping-pong');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Golf');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Golf'), 'Club House');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Golf'), 'Driving Range');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Golf'), 'Golf Course');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Golf'), 'Golf Course Feature');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Golf'), 'Golf School');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Golf'), 'Mini Golf');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Golf'), 'Putting Green');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Golf'), 'Pro Shop');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Hiking');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Horseback Riding');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Karaoke');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Kids'' club');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Park/Playground');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Pool/Swimming');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool/Swimming'), 'Children''s Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool/Swimming'), 'Indoor Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool/Swimming'), 'Outdoor Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool/Swimming'), 'Infinity Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool/Swimming'), 'Waterfall');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool/Swimming'), 'Waterslide');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Pool/Swimming'), 'Wave Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Pro Shop');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Recreational Facility');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Rock Climbing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Rock Climbing Wall');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Sports Clinic');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Sports Courts/Fields');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Courts/Fields'), 'Baseball');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Courts/Fields'), 'Basketball');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Courts/Fields'), 'Bocce');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Courts/Fields'), 'Football');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Courts/Fields'), 'Soccer');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Courts/Fields'), 'Squash');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Courts/Fields'), 'Tennis');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Sports Courts/Fields'), 'Volleyball');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Water Sports/Recreation');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Boating');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Canoeing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Dock');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Fishing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Kayaking');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Rafting');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Marina');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Sailing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Scuba Diving');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Snorkeling');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Surfing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Surfing Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Water park');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Water skiing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Water Sports/Recreation'), 'Windsurfing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Winter/Ice Sports');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Winter/Ice Sports'), 'Hockey');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Winter/Ice Sports'), 'Ice Skating');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Winter/Ice Sports'), 'Snow Skiing');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Winter/Ice Sports'), 'Snowmobiling');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Winter/Ice Sports'), 'Yoga');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Recreation'), 'Zip lining');


-- Supplemental Tags --
INSERT INTO predefined_tag (parent_id, text)
    VALUES (null, 'Supplemental Tags');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags'), 'Animals');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags'), 'Location');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'Beachside');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'Common area');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'Indoor/Interior');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'In-Pool');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'In-Room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'Off-Site');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'On-Site');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'Outdoor/Exterior');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'Poolside');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Location'), 'Roof');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags'), 'Miscellaneous');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags'), 'People');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Accessibility');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Adults');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Adults'), 'Middle age');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Adults'), 'Over 65');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Adults'), 'Young Adults');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Businessmen/women');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Children');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Children' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'People')),
            'Infants');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Children' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'People')),
            'Teenagers');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Children' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'People')),
            'Young Children');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Ethnicity');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Ethnicity'), 'Asian/Pacific Islander');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Ethnicity'), 'Black/African');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Ethnicity'), 'Hispanic');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Ethnicity'), 'Other');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Ethnicity'), 'White/Caucasian');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Family');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Family' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'People')),
            'Guests');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Family' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'People')),
            'Group');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Marital Status');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Couple');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Divorced');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Engaged');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Expecting Parents');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Honeymooning');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'LGBT');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Married');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Other');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Single');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Marital Status'), 'Single Parent');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Social group');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Staff');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Sex – Female');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'People'), 'Sex – Male');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags'), 'Photo Style');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Photo Style'), 'Aspect Ratio');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Aspect Ratio'), 'Landscape (wide)');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Aspect Ratio'), 'Panorama (extra wide)');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Aspect Ratio'), 'Portrait (tall)');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Aspect Ratio'), 'Square');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Photo Style'), 'Close-up');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Photo Style'), 'Drawing/Illustration');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Photo Style'), 'Floor Plan');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Photo Style'), 'Map');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Photo Style'), 'Monochrome');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Photo Style'), 'Rendering/CGI');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Photo Style'), 'Text Overlay');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags'), 'Time of Day');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Dawn/Sunrise');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Day');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Dusk/Sunset');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Day' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Evening/Night');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags'), 'Time of Year');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Year'), 'Holiday');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Holiday'), 'Christmas');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Holiday'), 'New Year''s');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Holiday'), 'Valentine''s Day');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Time of Year'), 'Season');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Season'), 'Autumn');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Season'), 'Spring');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Season'), 'Summer');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Season'), 'Winter');

INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags'), 'View From');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Above');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Back');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Balcony');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Front');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Guest Room');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Property');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Rooftop');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Satellite');
INSERT INTO predefined_tag (parent_id, text)
    VALUES ((select pt.id from predefined_tag pt where pt.text = 'View From' and pt.parent_id = (select pt.id from predefined_tag pt where pt.text = 'Supplemental Tags')),
            'Street');
