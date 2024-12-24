CREATE TABLE IF NOT EXISTS sector (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS list_code (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    characteristics TEXT,
    cost DECIMAL(10, 2), 
    price DECIMAL(10, 2),
    date_entry TIMESTAMP,
    date_exit TIMESTAMP,
    sector_id BIGINT,  -- Foreign key to sector
    list_code_id BIGINT,  -- Foreign key to list_code
    FOREIGN KEY (sector_id) REFERENCES sector(id),
    FOREIGN KEY (list_code_id) REFERENCES list_code(id)
);
