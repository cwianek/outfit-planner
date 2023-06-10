CREATE SCHEMA IF NOT EXISTS product;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS product.products (
                                                id uuid NOT NULL,
                                                category character varying COLLATE pg_catalog."default" NOT NULL,
                                                image bytea,
                                                username character varying COLLATE pg_catalog."default" NOT NULL,
                                                CONSTRAINT products_pkey PRIMARY KEY (id)
    );

DO 'DECLARE BEGIN
CREATE TYPE outbox_status AS ENUM (''STARTED'', ''COMPLETED'', ''FAILED'');
EXCEPTION
    WHEN duplicate_object THEN null;
END;
' LANGUAGE PLPGSQL;

CREATE TABLE IF NOT EXISTS "product".product_outbox (
                                                        id uuid NOT NULL,
                                                        created_at TIMESTAMP WITH TIME ZONE,
                                                        payload jsonb NOT NULL,
                                                        outbox_status outbox_status NOT NULL,
                                                        version integer,
                                                        CONSTRAINT product_outbox_pkey PRIMARY KEY (id)
    );