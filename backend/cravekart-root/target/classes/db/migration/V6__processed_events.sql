
CREATE TABLE IF NOT EXISTS processed_events(
   id BIGSERIAL PRIMARY KEY,
   event_id VARCHAR(200) NOT NULL UNIQUE,
    consumer VARCHAR(100) NOT NULL,
    processed_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX IF NOT EXISTS
    idx_processed_events_event_id ON processed_events(event_id);