INSERT INTO users (reference_id, email, first_name, last_name, password, username, profile_picture)
VALUES ('550e8400-e29b-41d4-a716-446655440000','admin@jello.com', 'Admin', 'One', '$2a$10$2JT8fttnQZr6JdvE2QP42OGUIglYcOCf5EeEHIpdt/UBPZPXAw2a6', 'admin', null);
INSERT INTO user_roles (role_id, user_id) SELECT 1, id FROM users WHERE email = 'admin@jello.com';