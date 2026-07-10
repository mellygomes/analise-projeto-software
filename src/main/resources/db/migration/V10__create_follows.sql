CREATE TABLE public.follows
(
    id BIGINT PRIMARY KEY DEFAULT nextval('primary_key_seq'),
    created_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,

    CONSTRAINT fk_follower
        FOREIGN KEY (follower_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_following
        FOREIGN KEY (following_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT unique_follow_relationship
        UNIQUE (follower_id, following_id),

    CONSTRAINT check_self_follow
        CHECK (follower_id <> following_id)
);