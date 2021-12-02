CREATE TABLE "name"
(
    "id"          SERIAL PRIMARY KEY,
    "text"        varchar NOT NULL,
    "language_id" int     NOT NULL,
    "type_id"     int,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "name_type"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar NOT NULL,
    "name"        varchar,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "sellable_unit_x_name"
(
    "sellable_unit_id" int NOT NULL,
    "name_id"          int NOT NULL,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "sellable_unit"
(
    "id"             SERIAL PRIMARY KEY,
    "property_id"    int NOT NULL,
    "limited"        boolean,
    "sold_over_time" boolean,
    "base_price"     decimal,
    "type_id"        int,
    "time_type"      varchar,
    "last_update"    timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "sellable_unit_capacity"
(
    "sellable_unit_id" int NOT NULL,
    "capacity"         int NOT NULL,
    "isBlockout"       boolean,
    "time_range_id"    int,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "availability"
(
    "sellable_unit_id" int,
    "count_available"  int,
    "date"             date,
    "time_segment"     time,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "time_range"
(
    "id"          SERIAL PRIMARY KEY,
    "from"        timestamp,
    "to"          timestamp,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "sellable_unit_x_description"
(
    "sellable_unit_id" int NOT NULL,
    "description_id"   int NOT NULL,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "sellable_unit_type"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar NOT NULL,
    "name"        varchar,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "sellable_unit_attribute"
(
    "id"                    SERIAL PRIMARY KEY,
    "name"                  varchar NOT NULL,
    "description"           varchar,
    "dimension"             varchar,
    "sellable_unit_type_id" int,
    "last_update"           timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "sellable_unit_attribute_predefined_value"
(
    "id"                         SERIAL PRIMARY KEY,
    "sellable_unit_attribute_id" int     NOT NULL,
    "value"                      varchar NOT NULL,
    "description"                varchar,
    "value_source"               varchar,
    "last_update"                timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "sellable_unit_x_sellable_unit_attribute"
(
    "sellable_unit_id"              int NOT NULL,
    "sellable_unit_attribute_id"    int NOT NULL,
    "sellable_unit_attribute_value" int,
    "last_update"                   timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "name"
    ADD FOREIGN KEY ("type_id") REFERENCES "name_type" ("id");

ALTER TABLE "sellable_unit_x_name"
    ADD FOREIGN KEY ("name_id") REFERENCES "name" ("id");

ALTER TABLE "sellable_unit_x_name"
    ADD FOREIGN KEY ("sellable_unit_id") REFERENCES "sellable_unit" ("id");

ALTER TABLE "availability"
    ADD FOREIGN KEY ("sellable_unit_id") REFERENCES "sellable_unit" ("id");

ALTER TABLE "sellable_unit_capacity"
    ADD FOREIGN KEY ("sellable_unit_id") REFERENCES "sellable_unit" ("id");

ALTER TABLE "sellable_unit_capacity"
    ADD FOREIGN KEY ("time_range_id") REFERENCES "time_range" ("id");

ALTER TABLE "sellable_unit_x_description"
    ADD FOREIGN KEY ("sellable_unit_id") REFERENCES "sellable_unit" ("id");

ALTER TABLE "sellable_unit_x_description"
    ADD FOREIGN KEY ("description_id") REFERENCES "description" ("id");

ALTER TABLE "sellable_unit"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");

ALTER TABLE "sellable_unit"
    ADD FOREIGN KEY ("type_id") REFERENCES "sellable_unit_type" ("id");

ALTER TABLE "sellable_unit_attribute"
    ADD FOREIGN KEY ("sellable_unit_type_id") REFERENCES "sellable_unit_type" ("id");

ALTER TABLE "sellable_unit_x_sellable_unit_attribute"
    ADD FOREIGN KEY ("sellable_unit_id") REFERENCES "sellable_unit" ("id");

ALTER TABLE "sellable_unit_x_sellable_unit_attribute"
    ADD FOREIGN KEY ("sellable_unit_attribute_id") REFERENCES "sellable_unit_attribute" ("id");

ALTER TABLE "sellable_unit_attribute"
    ADD FOREIGN KEY ("sellable_unit_type_id") REFERENCES "sellable_unit_type" ("id");

ALTER TABLE "sellable_unit_attribute_predefined_value"
    ADD FOREIGN KEY ("sellable_unit_attribute_id") REFERENCES "sellable_unit_attribute" ("id");
