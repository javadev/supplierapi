INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Caption',
        'Caption (Alphanumeric)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Description',
        'Long Caption / Description (Alphanumeric)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('File Format',
        'File Format (JPEG/TIFF/PNG/etc.)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('File Size',
        'File Size (KB)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Dimensions',
        'Dimensions (Pixels (W) x Pixels (H))',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Width',
        'Width (Pixels)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Height',
        'Height (Pixels)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Aspect Ratio',
        'Aspect Ratio (Number : number)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Resolution',
        'Resolution (Numeric in dpi)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Created',
        'Created (Original) (Date and Time (W3C))',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Modified',
        'Modified (Date and Time (W3C))',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Owner',
        'Owner (Alphanumeric)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Copyright',
        'Copyright (URL)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Location',
        'Location (Geocode) (Lat./long./alt.)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Location Latitude',
        'Location Latitude (Geocode)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
VALUES ('Location Longitude',
        'Location Longitude (Geocode)',
        (select id from media_type where code = 'img'));

INSERT INTO media_attribute_type (code, name, media_type_id)
    VALUES ('OTA Category',
            'Open Travel Alliance Category',
            (select id from media_type where code = 'img'));

-- OTA Category - predefined values --
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '1', 'Exterior view');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '2', 'Lobby view');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '3', 'Pool view');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '4', 'Restaurant');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '5', 'Health club');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '6', 'Guest room');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '7', 'Suite');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '8', 'Meeting room');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '9', 'Ballroom');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '10', 'Golf course');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '11', 'Beach');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '12', 'Spa');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '13', 'Bar/Lounge');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '14', 'Recreational facility');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '15', 'Logo');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '16', 'Basics');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '17', 'Map');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '18', 'Promotional');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '19', 'Hot news');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '20', 'Miscellaneous');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '21', 'Guest room amenity');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '22', 'Property amenity');
INSERT INTO media_attribute_predefined_value (media_attribute_type_id, value, description)
    VALUES ((select id from media_attribute_type where code = 'OTA Category'),
            '23', 'Business center');
