CREATE TABLE "description"
(
    "id"          SERIAL PRIMARY KEY,
    "text"        varchar NOT NULL,
    "language_id" int     NOT NULL,
    "type_id"     int,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "description_type"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar NOT NULL,
    "name"        varchar NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "description"
    ADD FOREIGN KEY ("type_id") REFERENCES "description_type" ("id");

ALTER TABLE "description"
    ADD FOREIGN KEY ("language_id") REFERENCES "language" ("id");



CREATE TABLE "point_of_interest_x_description"
(
    "point_of_interest_id" int NOT NULL,
    "description_id"       int NOT NULL,
    "last_update"          timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "point_of_interest_x_description"
    ADD FOREIGN KEY ("point_of_interest_id") REFERENCES "point_of_interest" ("id");

ALTER TABLE "point_of_interest_x_description"
    ADD FOREIGN KEY ("description_id") REFERENCES "description" ("id");



CREATE TABLE "media_x_description"
(
    "media_id"       int NOT NULL,
    "description_id" int NOT NULL,
    "last_update"    timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "media_x_description"
    ADD FOREIGN KEY ("media_id") REFERENCES "media" ("id");

ALTER TABLE "media_x_description"
    ADD FOREIGN KEY ("description_id") REFERENCES "description" ("id");
