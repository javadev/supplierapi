CREATE TABLE "notification"
(
    "id"          SERIAL PRIMARY KEY,
    "entity_name" varchar NOT NULL,
    "entity_id"   int     NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "supplier_x_notification"
(
    "supplier_id"     int,
    "notification_id" int,
    "retry_count"     int,
    "is_send"         boolean NOT NULL,
    "is_email_send"   boolean NOT NULL,
    "last_update"     timestamp DEFAULT (current_timestamp),
    PRIMARY KEY (supplier_id, notification_id)
);

ALTER TABLE "supplier_x_notification"
    ADD FOREIGN KEY ("notification_id") REFERENCES "notification" ("id");

ALTER TABLE "supplier_x_notification"
    ADD FOREIGN KEY ("supplier_id") REFERENCES "supplier" ("id");
