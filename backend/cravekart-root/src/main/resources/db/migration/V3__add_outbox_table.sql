-- db/migration/V3__add_outbox_table.sql
CREATE TABLE outbox_events
(
    id           UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    event_type   VARCHAR(100) NOT NULL,
    topic        VARCHAR(100) NOT NULL,
    payload      TEXT         NOT NULL,
    status       VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    created_at   TIMESTAMP    NOT NULL DEFAULT now(),
    processed_at TIMESTAMP,
    retry_count  INT          NOT NULL DEFAULT 0
);

CREATE INDEX idx_outbox_status ON outbox_events (status, created_at);