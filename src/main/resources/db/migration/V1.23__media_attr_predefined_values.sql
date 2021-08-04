CREATE TABLE "media_attribute_predefined_value"
(
    "id"                      SERIAL PRIMARY KEY,
    "media_attribute_type_id" int NOT NULL,
    "value"                   varchar NOT NULL,
    "description"             varchar,
    "last_update"             timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "media_attribute_predefined_value" ADD FOREIGN KEY
    ("media_attribute_type_id") REFERENCES "media_attribute_type" ("id");
