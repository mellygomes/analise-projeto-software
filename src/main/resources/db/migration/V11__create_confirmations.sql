CREATE TABLE public.confirmations
(
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('primary_key_seq'),
    user_id BIGINT NOT NULL,
    confirmation_key VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_confirmation_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);