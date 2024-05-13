-- Insert Table User
INSERT INTO users (id, username, authentication_code, first_name, last_name, email, phone_number, date_of_birth)
    VALUES (1, 'admin', '$2a$10$Hj4cEnXFNuUuVucxMa18ceoYoK.XFh3QwJ.OPsspWFI3jnsw9cx3i', 'Dang Huu', 'Thang','admin@gmail.com', '0987654321', '2002-11-21');
INSERT INTO users (id, username, authentication_code, first_name, last_name, email, phone_number, date_of_birth)
VALUES (2, 'user', '$2a$10$Hj4cEnXFNuUuVucxMa18ceoYoK.XFh3QwJ.OPsspWFI3jnsw9cx3i', 'Dang Huu', 'Thang','user@gmail.com', '0987654321', '2002-11-21');

-- Insert Table Role
INSERT INTO roles (id, role_name)
VALUES (1, 'ADMIN');
INSERT INTO roles (id, role_name)
VALUES (2, 'USER');

-- Insert table Roles_Users
INSERT INTO users_roles (role_id, user_id)
VALUES (1, 1);
-- INSERT INTO users_roles (role_id, user_id)
-- VALUES (2, 1);
INSERT INTO users_roles (role_id, user_id)
VALUES (2, 2);

