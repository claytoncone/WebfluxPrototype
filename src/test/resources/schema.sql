
CREATE TABLE IF NOT EXISTS address (
                         id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         address_line1 VARCHAR(255) NOT NULL,
                         address_line2 VARCHAR(255),
                         city VARCHAR(255) NOT NULL,
                         country VARCHAR(255) NOT NULL,
                         state VARCHAR(255) NOT NULL,
                         zip VARCHAR(15) NOT NULL,
                         version BIGINT NOT NULL DEFAULT 0,
                         created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS client (
                        id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        given_name VARCHAR(255) NOT NULL,
                        middle_initial VARCHAR(1),
                        surname VARCHAR(255) NOT NULL,
                        title VARCHAR(255),
                        company VARCHAR(255),
                        address_id BIGINT,
                        version BIGINT NOT NULL DEFAULT 0,
                        created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE IF NOT EXISTS contact (
                         client_id BIGINT NOT NULL PRIMARY KEY,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         phone VARCHAR(15),
                         mobile VARCHAR(15),
                         fax VARCHAR(15),
                         FOREIGN KEY (client_id) REFERENCES client (id)
);