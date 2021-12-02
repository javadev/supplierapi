CREATE TABLE "pricing_model"
(
    "id"          SERIAL PRIMARY KEY,
    "type_id"     int NOT NULL,
    "record_id"   int NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "pricing_model_type"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar NOT NULL,
    "table_name"  varchar NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "pricing_model"
    ADD FOREIGN KEY ("type_id") REFERENCES "pricing_model_type" ("id");

CREATE TABLE "standard_pricing_model"
(
    "id"                    SERIAL PRIMARY KEY,
    "maximum_persons_price" decimal,
    "single_person_price"   decimal,
    "last_update"           timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "derived_pricing_model"
(
    "id"                 SERIAL PRIMARY KEY,
    "standard_occupancy" int,
    "standard_price"     decimal,
    "min_age"            int,
    "max_age"            int,
    "last_update"        timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "derived_pricing_model_options"
(
    "id"                       SERIAL PRIMARY KEY,
    "derived_pricing_model_id" int,
    "number_of_persons"        int,
    "derive_percentage"        decimal,
    "derive_price"             decimal,
    "last_update"              timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "derived_pricing_model_options"
    ADD FOREIGN KEY ("derived_pricing_model_id") REFERENCES "derived_pricing_model" ("id");

CREATE TABLE "occupancy_based_pricing_model"
(
    "id"          SERIAL PRIMARY KEY,
    "min_age"     int,
    "max_age"     int,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "occupancy_based_pricing_model_options"
(
    "id"                               SERIAL PRIMARY KEY,
    "occupancy_based_pricing_model_id" int,
    "number_of_persons"                int,
    "price"                            decimal,
    "last_update"                      timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "occupancy_based_pricing_model_options"
    ADD FOREIGN KEY ("occupancy_based_pricing_model_id") REFERENCES "occupancy_based_pricing_model" ("id");

CREATE TABLE "length_of_stay_pricing_model"
(
    "id"          SERIAL PRIMARY KEY,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "length_of_stay_pricing_model_options"
(
    "id"                              SERIAL PRIMARY KEY,
    "length_of_stay_pricing_model_id" int,
    "number_of_days"                  int,
    "number_of_months"                int,
    "price"                           decimal,
    "last_update"                     timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "length_of_stay_pricing_model_options"
    ADD FOREIGN KEY ("length_of_stay_pricing_model_id") REFERENCES "length_of_stay_pricing_model" ("id");
