create index IF NOT EXISTS calendar_su_id
    on calendar (sellable_unit_id);

ALTER TABLE calendar DROP CONSTRAINT IF EXISTS calendar_sellable_unit_id_date_time_segment_key;

CREATE UNIQUE INDEX calendar_sellable_unit_id_date_time_segment_key
    on calendar (sellable_unit_id, date, time_segment) where "time_segment" is not null;

CREATE UNIQUE INDEX calendar_sellable_unit_id_date_time_null_key
    ON calendar ("sellable_unit_id", "date") where "time_segment" is null;

