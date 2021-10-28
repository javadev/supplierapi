CREATE TABLE "property_identifier"
(
    "id"                   SERIAL PRIMARY KEY,
    "property_id"          int     NOT NULL,
    "identifier"           varchar NOT NULL,
    "identifier_source_id" int     NOT NULL,
    "last_update"          timestamp DEFAULT (current_timestamp),
    UNIQUE ("property_id", "identifier_source_id")
);

CREATE TABLE "identifier_source"
(
    "id"           SERIAL PRIMARY KEY,
    "abbreviation" varchar NOT NULL,
    "name"         varchar,
    "last_update"  timestamp DEFAULT (current_timestamp),
    UNIQUE ("abbreviation")
);

ALTER TABLE "property_identifier"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");

ALTER TABLE "property_identifier"
    ADD FOREIGN KEY ("identifier_source_id") REFERENCES "identifier_source" ("id");
