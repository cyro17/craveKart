

-- add aggregate_id to outbox events.
-- used as kafka partition key so all events for the same aggregate id land on the same partition

ALTER TABLE outbox_events
    ADD COLUMN IF NOT EXISTS aggregate_id VARCHAR(100) NOT NULL DEFAULT 'unknown';

