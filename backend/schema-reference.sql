-- =========================================================
-- Loyalty Rewards - reference schema
-- Optional: Hibernate will create/update these tables for you
-- automatically (ddl-auto=update). Use this file only if you
-- prefer to create the tables by hand.
-- =========================================================

CREATE DATABASE IF NOT EXISTS loyalty_rewards;
USE loyalty_rewards;

CREATE TABLE IF NOT EXISTS rewards_config (
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    earn_rate_per_amount   DOUBLE   NOT NULL,
    min_redeem_points      INT      NOT NULL,
    redeem_value_per_point DOUBLE   NOT NULL
);

CREATE TABLE IF NOT EXISTS members (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    phone          VARCHAR(50),
    join_date      DATE NOT NULL,
    points_balance INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS transactions (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id         BIGINT NOT NULL,
    amount_spent      DOUBLE NOT NULL,
    points_earned     INT NOT NULL,
    transaction_date  DATETIME NOT NULL,
    description       VARCHAR(500),
    CONSTRAINT fk_transactions_member FOREIGN KEY (member_id) REFERENCES members(id)
);

CREATE TABLE IF NOT EXISTS redemptions (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id         BIGINT NOT NULL,
    points_redeemed   INT NOT NULL,
    cash_value        DOUBLE NOT NULL,
    redemption_date   DATETIME NOT NULL,
    description       VARCHAR(500),
    CONSTRAINT fk_redemptions_member FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Seed a default rewards configuration (1 point per currency unit spent,
-- 100 point minimum redemption, each point worth 0.5 currency units)
INSERT INTO rewards_config (earn_rate_per_amount, min_redeem_points, redeem_value_per_point)
SELECT 1.0, 100, 0.5
WHERE NOT EXISTS (SELECT 1 FROM rewards_config);
