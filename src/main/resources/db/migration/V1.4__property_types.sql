CREATE TABLE "property_type"
(
    "id"               SERIAL PRIMARY KEY,
    "code"             varchar NOT NULL,
    "name"             varchar NOT NULL,
    "code_source"      varchar NOT NULL,
    "alternative_name" varchar,
    "last_update"      timestamp DEFAULT (current_timestamp),
    UNIQUE ("code", "code_source")
);
