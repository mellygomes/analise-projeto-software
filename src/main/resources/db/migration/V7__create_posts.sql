CREATE TABLE public.posts
(
    id BIGINT PRIMARY KEY DEFAULT nextval('primary_key_seq'),
    reference_id VARCHAR(255) UNIQUE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    title VARCHAR(255),
    content VARCHAR(255),
    CONSTRAINT fk_posts_user FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);
