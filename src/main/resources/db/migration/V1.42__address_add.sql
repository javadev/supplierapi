CREATE TABLE "address"
(
    "id"           SERIAL PRIMARY KEY,
    "language_id"  int,
    "address_line" varchar,
    "city_name"    varchar,
    "country_id"   int,
    "postal_code"  varchar,
    "state_id"     int,
    "last_update"  timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "property_info_x_address"
(
    "property_info_id" int NOT NULL,
    "address_id"       int NOT NULL,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "address"
    ADD FOREIGN KEY ("language_id") REFERENCES "language" ("id");

ALTER TABLE "address"
    ADD FOREIGN KEY ("country_id") REFERENCES "country" ("id");

ALTER TABLE "address"
    ADD FOREIGN KEY ("state_id") REFERENCES "state" ("id");

ALTER TABLE "property_info_x_address"
    ADD FOREIGN KEY ("address_id") REFERENCES "address" ("id");

ALTER TABLE "property_info_x_address"
    ADD FOREIGN KEY ("property_info_id") REFERENCES "property_info" ("id");
