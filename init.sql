CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    order_number VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    extra_fields JSONB
);
