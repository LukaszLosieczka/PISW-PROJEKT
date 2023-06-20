INSERT INTO role (name) VALUES
    ('ROLE_USER'),
    ('ROLE_ADMIN'),
    ('ROLE_TICKET_COLLECTOR')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO user (login, password, role, surname, name) VALUES
    ('admin1', '$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK', 'ROLE_ADMIN', 'admin', 'admin'),
    ('user1', '$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK', 'ROLE_USER', 'user', 'user'),
    ('collector1', '$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK', 'ROLE_TICKET_COLLECTOR', 'collector', 'collector')
ON DUPLICATE KEY UPDATE login=login;

INSERT INTO ticket (id, validity_period_sec, price, name, description, ticket_type) VALUES
    (1, 30, 2.30, 'jednorazowy', 'wszystkie linie', 'SINGLE'),
    (2, 900, 1.60, '15-minutowy', 'wszystkie linie', 'TIME'),
    (3, 1800, 2.70, '30-minutowy', 'wszystkie linie', 'TIME'),
    (4, 3600, 5.15, 'godzinny', 'wszystkie linie', 'TIME'),
    (5, 7200, 9.80, 'godzinny', 'wszystkie linie', 'TIME'),
    (6, 2592000, 110.50, 'miesięczny', 'wszystkie linie', 'SEASON'),
    (7, 7776000, 310.20, '3-miesięczny', 'wszystkie linie', 'SEASON'),
    (8, 15552000, 610.15, '6-miesięczny', 'wszystkie linie', 'SEASON'),
    (9, 31104000, 1010.55, 'roczny', 'wszystkie linie', 'SEASON')
ON DUPLICATE KEY UPDATE id=id;

INSERT INTO discount (id, discount_percent, name) VALUES
    (1, 50.0, 'studencka 50%'),
    (2, 75.0, 'seniorska 75%'),
    (3, 100.0, 'uczniowska 100%')
ON DUPLICATE KEY UPDATE id=id;
