CREATE TABLE "state"
(
    "id"                   SERIAL PRIMARY KEY,
    "country_id"           int     NOT NULL,
    "code"                 varchar NOT NULL,
    "name"                 varchar NOT NULL,
    "local_name"           varchar,
    "language_id"          int,
    "subdivision_category" varchar,
    "last_update"          timestamp DEFAULT (current_timestamp),
    UNIQUE ("country_id", "code")
);

ALTER TABLE "state" ADD FOREIGN KEY ("country_id") REFERENCES "country" ("id");
ALTER TABLE "state" ADD FOREIGN KEY ("language_id") REFERENCES "language" ("id");
