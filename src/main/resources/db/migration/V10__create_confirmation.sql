CREATE SEQUENCE IF NOT EXISTS primary_key_seq
START WITH 1
INCREMENT BY 1;

CREATE TABLE public.confirmations
(
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('primary_key_seq'),
    reference_id VARCHAR(255),
    created_by BIGINT NOT NULL,
    updated_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    confirmation_key VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_confirmation_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);