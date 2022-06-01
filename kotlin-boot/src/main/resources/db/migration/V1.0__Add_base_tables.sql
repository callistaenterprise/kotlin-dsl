CREATE TYPE customer_status AS ENUM ('ACTIVE', 'INACTIVE');

CREATE TABLE customer
(
    id          bigint          NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    customer_id text            NOT NULL,
    status      customer_status NOT NULL,
    first_name  text            NOT NULL,
    last_name   text            NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (customer_id)
);

CREATE TABLE product
(
    id           bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    product_id   text   NOT NULL,
    product_name text   NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (product_id)
);

CREATE TABLE "order"
(
    id          bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    order_id    text   NOT NULL,
    customer_id bigint NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (order_id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE item
(
    order_id   bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity   bigint NOT NULL,

    FOREIGN KEY (order_id) REFERENCES "order" (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
