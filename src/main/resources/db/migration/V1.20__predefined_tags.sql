CREATE TABLE "predefined_tag"
(
    "id"          SERIAL PRIMARY KEY,
    "parent_id"   int,
    "text"        varchar NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp),
    UNIQUE ("parent_id", "text")
);

ALTER TABLE media_tag
    ADD COLUMN predefined_tag_id int;

ALTER TABLE "media_tag"
    ADD FOREIGN KEY ("predefined_tag_id") REFERENCES "predefined_tag" ("id");
