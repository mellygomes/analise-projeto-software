CREATE TABLE public.confirmations
(
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('primary_key_seq'),
    reference_id VARCHAR(255) UNIQUE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    confirmation_key VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_confirmation_user_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_confirmation_user_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON DELETE CASCADE
);