INSERT INTO users (reference_id, email, first_name, last_name, password, username, profile_picture)
VALUES ('1','ana@dev.com', 'Ana', 'Dev', '$2a$10$2JT8fttnQZr6JdvE2QP42OGUIglYcOCf5EeEHIpdt/UBPZPXAw2a6', 'aninhadev', null);
INSERT INTO users (reference_id, email, first_name, last_name, password, username, profile_picture)
VALUES ('2','bruno@db.com', 'Bruno', 'Db', '$2a$10$2JT8fttnQZr6JdvE2QP42OGUIglYcOCf5EeEHIpdt/UBPZPXAw2a6', 'debenelson', null);
INSERT INTO users (reference_id, email, first_name, last_name, password, username, profile_picture)
VALUES ('3', 'carlos@marketing.com', 'Carlos', 'Marketing', '$2a$10$2JT8fttnQZr6JdvE2QP42OGUIglYcOCf5EeEHIpdt/UBPZPXAw2a6', 'carlosmarques', null);
INSERT INTO user_roles (role_id, user_id) SELECT 2, id FROM users WHERE email = 'ana@dev.com';
INSERT INTO user_roles (role_id, user_id) SELECT 2, id FROM users WHERE email = 'bruno@db.com';
INSERT INTO user_roles (role_id, user_id) SELECT 2, id FROM users WHERE email = 'carlos@marketing.com';
