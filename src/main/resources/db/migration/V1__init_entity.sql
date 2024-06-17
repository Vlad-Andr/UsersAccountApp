CREATE TABLE user_account
(
    id INT PRIMARY KEY NOT NULL,
    user_name VARCHAR(255),
    account_balance INT NOT NULL DEFAULT 0
)
