CREATE TABLE "country" (
  "id" SERIAL PRIMARY KEY,
  "code" varchar NOT NULL,
  "code_a3" varchar,
  "code_num" varchar,
  "last_update" timestamp DEFAULT (current_timestamp),
  UNIQUE ("code")
);

CREATE TABLE "language" (
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar NOT NULL,
    "name"        varchar NOT NULL,
    "native_name" varchar,
    "code_2t"      varchar,
    "code_2b"      varchar,
    "code_3"       varchar,
    "last_update" timestamp DEFAULT (current_timestamp),
    UNIQUE ("code")
);

CREATE TABLE "country_translation" (
  "id"          SERIAL PRIMARY KEY,
  "country_id" int,
  "name" varchar NOT NULL,
  "full_name" varchar,
  "language_id" int NOT NULL,
  "last_update" timestamp DEFAULT (current_timestamp),
  UNIQUE ("country_id", "language_id")
);

ALTER TABLE "country_translation" ADD FOREIGN KEY ("language_id") REFERENCES "language" ("id");
ALTER TABLE "country_translation" ADD FOREIGN KEY ("country_id") REFERENCES "country" ("id");
