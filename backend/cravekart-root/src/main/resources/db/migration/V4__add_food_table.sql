-- =========================
-- FOOD TABLE
-- =========================
CREATE TABLE food
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255),
    description   TEXT,
    price         NUMERIC(10, 2),

    category_id   BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,

    available     BOOLEAN   DEFAULT TRUE,
    vegetarian    BOOLEAN,
    seasonal      BOOLEAN,

    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_food_category
        FOREIGN KEY (category_id)
            REFERENCES food_category (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_food_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (id)
            ON DELETE CASCADE
);