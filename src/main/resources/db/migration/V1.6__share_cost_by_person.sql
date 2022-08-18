ALTER TABLE COST
    DROP COLUMN is_pending_to_pay;

ALTER TABLE COST
    DROP COLUMN pending_to_pay_amount;

CREATE TABLE COST_SHARE
(
    id     SERIAL PRIMARY KEY,
    cost   INTEGER REFERENCES COST(id),
    amount DECIMAL,
    isPaid BOOLEAN,
    debtor VARCHAR(64)
)