CREATE TABLE Users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role ENUM('ADMINISTRATOR', 'MODERATOR', 'REGULAR_USER') NOT NULL DEFAULT 'REGULAR_USER'
);

CREATE TABLE Libraries (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           user_id INT NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Games (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       genre VARCHAR(255),
                       price DECIMAL(10,2) NOT NULL
);

CREATE TABLE LibraryGames (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              library_id INT NOT NULL,
                              user_id INT NOT NULL,
                              game_id INT NOT NULL,
                              state ENUM('Available', 'Playing', 'Paused', 'Completed', 'Dropped', 'Wishlist', 'Replaying') DEFAULT 'Available',
                              game_score INT,
                              FOREIGN KEY (library_id) REFERENCES Libraries(id) ON DELETE CASCADE,
                              FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                              FOREIGN KEY (game_id) REFERENCES Games(id) ON DELETE CASCADE
);

CREATE TABLE Friendships (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             requester_id INT NOT NULL,
                             receiver_id INT NOT NULL,
                             status ENUM('Pending', 'Accepted', 'Rejected') NOT NULL,
                             FOREIGN KEY (requester_id) REFERENCES Users(id) ON DELETE CASCADE,
                             FOREIGN KEY (receiver_id) REFERENCES Users(id) ON DELETE CASCADE
);

