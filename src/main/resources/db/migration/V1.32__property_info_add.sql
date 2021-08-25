CREATE TABLE "property_info"
(
    "id"               SERIAL PRIMARY KEY,
    "property_id"      int NOT NULL,
    "geo_code_id"      int,
    "brand_id"         int,
    "website"          varchar,
    "taxpayer_id"      varchar,
    "capacity"         int,
    "capacity_type"    varchar,
    "exists_code"      varchar,
    "property_type_id" int,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "property_info_x_language"
(
    "property_info_id" int NOT NULL,
    "language_id"      int NOT NULL,
    "is_main"          boolean,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "geo_code"
(
    "id"          SERIAL PRIMARY KEY,
    "latitude"    decimal NOT NULL,
    "longitude"   decimal NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "brand"
(
    "id"               SERIAL PRIMARY KEY,
    "name"             varchar NOT NULL,
    "alternative_name" varchar,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "property_info_x_language"
    ADD FOREIGN KEY ("language_id") REFERENCES "language" ("id");

ALTER TABLE "property_info_x_language"
    ADD FOREIGN KEY ("property_info_id") REFERENCES "property_info" ("id");

ALTER TABLE "property_info"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");

ALTER TABLE "property_info"
    ADD FOREIGN KEY ("geo_code_id") REFERENCES "geo_code" ("id");

ALTER TABLE "property_info"
    ADD FOREIGN KEY ("property_type_id") REFERENCES "property_type" ("id");

ALTER TABLE "property_info"
    ADD FOREIGN KEY ("brand_id") REFERENCES "brand" ("id");
