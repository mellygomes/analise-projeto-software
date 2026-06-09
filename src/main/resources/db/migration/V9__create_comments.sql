CREATE TABLE public.comments
(
    id BIGINT PRIMARY KEY DEFAULT nextval('primary_key_seq'),
    reference_id VARCHAR(255) UNIQUE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    post_id BIGINT NOT NULL,
    content VARCHAR(255),
    CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_user_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_user_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON DELETE CASCADE
);