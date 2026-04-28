INSERT INTO users (email, first_name, last_name, password, username, profile_picture)
VALUES ('admin@jello.com', 'Admin', 'One', '$2a$10$2JT8fttnQZr6JdvE2QP42OGUIglYcOCf5EeEHIpdt/UBPZPXAw2a6', 'admin', null);
INSERT INTO user_roles (role_id, user_id) VALUES (1, 4);