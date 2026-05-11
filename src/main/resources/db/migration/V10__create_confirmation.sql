CREATE TABLE public.confirmation
(
    key VARCHAR NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_confirmation_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);