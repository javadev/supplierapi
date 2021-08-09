CREATE TABLE "point_of_interest"
(
    "id"               SERIAL PRIMARY KEY,
    "property_id"      int NOT NULL,
    "category_code_id" int,
    "name"             varchar,
    "distance"         int,
    "distance_unit"    varchar,
    "language_id"      int,
    "last_update"      timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "category_code"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar,
    "name"        varchar,
    "code_source" varchar NOT NULL,
    "last_update" timestamp
);

ALTER TABLE "point_of_interest"
    ADD FOREIGN KEY ("category_code_id") REFERENCES "category_code" ("id");

ALTER TABLE "point_of_interest"
    ADD FOREIGN KEY ("language_id") REFERENCES "language" ("id");

ALTER TABLE "point_of_interest"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");
