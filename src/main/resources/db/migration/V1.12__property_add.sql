CREATE TABLE "property"
(
    "id"                   SERIAL PRIMARY KEY,
    "supplier_property_id" varchar,
    "code"                 varchar,
    "name"                 varchar NOT NULL,
    "alternative_name"     varchar,
    "status"               varchar,
    "home_currency_id"     int,
    "supplier_id"          int NOT NULL,
    "last_update"          timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "property"
    ADD FOREIGN KEY ("supplier_id") REFERENCES "supplier" ("id");
