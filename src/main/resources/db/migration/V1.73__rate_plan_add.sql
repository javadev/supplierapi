CREATE TABLE "rate_plan"
(
    "id"                     SERIAL PRIMARY KEY,
    "property_id"            int     NOT NULL,
    "name"                   varchar NOT NULL,
    "parent_id"              int,
    "stop_sell"              boolean,
    "closed_to_arrival"      boolean,
    "closed_to_departure"    boolean,
    "min_length_of_stay"     int,
    "max_length_of_stay"     int,
    "payment_policy_id"      int,
    "cancellation_policy_id" int,
    "booking_policy_id"      int,
    "policy_info_id"         int,
    "pet_policy_id"          int,
    "commission"             decimal,
    "last_update"            timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "product"
    ADD FOREIGN KEY ("rate_plan_id") REFERENCES "rate_plan" ("id");

ALTER TABLE "rate_plan"
    ADD FOREIGN KEY ("parent_id") REFERENCES "rate_plan" ("id");

ALTER TABLE "rate_plan"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");
