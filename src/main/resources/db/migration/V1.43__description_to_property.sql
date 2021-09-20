CREATE TABLE "property_x_description"
(
    "property_id"    int NOT NULL,
    "description_id" int NOT NULL,
    "last_update"    timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "property_x_description"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");

ALTER TABLE "property_x_description"
    ADD FOREIGN KEY ("description_id") REFERENCES "description" ("id");

INSERT INTO description_type (code, name) VALUES ('hotel-long', 'Hotel Description - Long version');
INSERT INTO description_type (code, name) VALUES ('hotel-short', 'Hotel Description - Short version');
INSERT INTO description_type (code, name) VALUES ('liability', 'Liability notice');
INSERT INTO description_type (code, name) VALUES ('location', 'Location Description');
INSERT INTO description_type (code, name) VALUES ('directions', 'Directions');
INSERT INTO description_type (code, name) VALUES ('insider-tips', 'Insider Tips');
