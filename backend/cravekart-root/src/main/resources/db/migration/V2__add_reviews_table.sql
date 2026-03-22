CREATE TABLE reviews
(
    id            BIGSERIAL PRIMARY KEY,
    order_id      BIGINT  NOT NULL,
    customer_id   BIGINT  NOT NULL,
    restaurant_id BIGINT  NOT NULL,
    rating        INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment       TEXT,
    created_at    TIMESTAMP DEFAULT NOW()
);