INSERT INTO role (name) VALUES ('ROLE_USER'),
                              ('ROLE_ADMIN'),
                              ('ROLE_TICKET_COLLECTOR') ON DUPLICATE KEY UPDATE name=name;

INSERT INTO user (login, password, role, surname, name) VALUES
    ('admin1', '$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK', 'ROLE_ADMIN', 'admin', 'admin')
ON DUPLICATE KEY UPDATE login=login;