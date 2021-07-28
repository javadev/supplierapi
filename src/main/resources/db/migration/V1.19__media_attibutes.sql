CREATE TABLE "media_attribute_type"
(
    "id"            SERIAL PRIMARY KEY,
    "code"          varchar NOT NULL,
    "name"          varchar NOT NULL,
    "media_type_id" int,
    "last_update"   timestamp DEFAULT (current_timestamp),
    UNIQUE ("code", "name")
);

CREATE TABLE "media_attribute"
(
    "id"                      SERIAL PRIMARY KEY,
    "media_id"                int NOT NULL,
    "media_attribute_type_id" int NOT NULL,
    "value"                   varchar NOT NULL,
    "dimension"               varchar,
    "last_update"             timestamp DEFAULT (current_timestamp),
    UNIQUE ("media_id", "media_attribute_type_id")
);


ALTER TABLE "media_attribute"
    ADD FOREIGN KEY ("media_id") REFERENCES "media" ("id");

ALTER TABLE "media_attribute"
    ADD FOREIGN KEY ("media_attribute_type_id") REFERENCES "media_attribute_type" ("id");

ALTER TABLE "media_attribute_type"
    ADD FOREIGN KEY ("media_type_id") REFERENCES "media_type" ("id");


ALTER TABLE media_type ALTER COLUMN code SET NOT NULL;
ALTER TABLE media_type ALTER COLUMN name SET NOT NULL;
ALTER TABLE media_type ADD UNIQUE ("code", "name");

ALTER TABLE license_type ALTER COLUMN code SET NOT NULL;
ALTER TABLE license_type ALTER COLUMN name SET NOT NULL;
ALTER TABLE license_type ADD UNIQUE ("code", "name");

ALTER TABLE media_tag ALTER COLUMN media_id SET NOT NULL;
ALTER TABLE media_tag ALTER COLUMN text SET NOT NULL;
ALTER TABLE media_tag ADD UNIQUE ("media_id", "text");
