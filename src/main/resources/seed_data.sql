
INSERT INTO address (country, zip, address_line1, address_line2, city, state) VALUES
('USA', '12345', '336 Main St', 'Suite B', 'Anytown', 'CO'),
('USA', '12345', '446 Elm St', '', 'Othertown', 'CO'),
('USA', '54321', '551 Main St', '', 'Someplace', 'CA'),
('USA', '23456', '996 Oak St', '', 'Someotherplace', 'CA');

INSERT INTO client (given_name, surname, address_id) VALUES ('John', 'Doe',
                     (SELECT id from ADDRESS WHERE address_line1 = '446 Elm St' AND city = 'Othertown'
                                               AND state = 'CO'));

INSERT INTO contact (client_id, email, phone, mobile, fax) VALUES
( (SELECT ID FROM client  WHERE given_name = 'John' AND surname = 'Doe'),
                'data@datar.us', '3037987766', '3037987766', '7207986677' );

INSERT INTO client (given_name, middle_initial, surname, company, address_id) VALUES (
                  'Richard', 'M','Nixon', 'Not A Crook',
                  (SELECT id from ADDRESS WHERE address_line1 = '336 Main St' AND city = 'Anytown'
                                            AND state = 'CO'));

INSERT INTO contact (CLIENT_ID, EMAIL, PHONE, MOBILE, FAX) VALUES
    ( (SELECT ID FROM client  WHERE given_name = 'Richard' AND surname = 'Nixon'),
      'tricky@dick.us', '5057987766', '5057987766', '5057986677' );

INSERT INTO client (given_name, middle_initial, surname, company, address_id) VALUES
                ('Gerald', 'R', 'Ford', 'Vale Resort',
                 (SELECT id from ADDRESS WHERE address_line1 = '336 Main St' AND city = 'Anytown'
                                           AND state = 'CO'));

INSERT INTO contact (CLIENT_ID, EMAIL, PHONE, MOBILE, FAX) VALUES
    ( (SELECT ID FROM client  WHERE GIVEN_NAME = 'Gerald' AND SURNAME = 'Ford'),
      'ford@fpotus.us', '5057987766', '5057987766', '5057986677' );
