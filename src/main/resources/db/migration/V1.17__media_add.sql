CREATE TABLE "media"
(
    "id"              SERIAL PRIMARY KEY,
    "property_id"     int NOT NULL,
    "media_type_id"   int,
    "license_type_id" int,
    "is_main"         boolean,
    "is_logo"         boolean,
    "sort_order"      int,
    "url"             varchar,
    "last_update"     timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "media_type"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar,
    "name"        varchar,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "license_type"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar,
    "name"        varchar,
    "last_update" timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "media"
    ADD FOREIGN KEY ("media_type_id") REFERENCES "media_type" ("id");

ALTER TABLE "media"
    ADD FOREIGN KEY ("license_type_id") REFERENCES "license_type" ("id");

ALTER TABLE "media"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");

CREATE TABLE "media_tag"
(
    "id"          SERIAL PRIMARY KEY,
    "media_id"    int,
    "text"        varchar,
    "last_update" timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "media_tag"
    ADD FOREIGN KEY ("media_id") REFERENCES "media" ("id");
