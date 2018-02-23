CREATE TABLE ratings (
    artist_name      varchar( 256 ) NOT NULL, -- artists
    email            varchar( 256 ) NOT NULL, -- users
    rating_type_name varchar( 256 ) NOT NULL, -- rating_types
    rating           int( 11 )      NOT NULL,
    at               TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key ( artist_name, email, rating_type_name ),
    INDEX artist_name_ind ( artist_name ),
    INDEX user_email_ind ( email ),
    INDEX rating_type_name_ind ( rating_type_name ),

    CONSTRAINT fk_artist_name FOREIGN KEY ( artist_name )
        REFERENCES artists( name )
        ON DELETE CASCADE
        ON UPDATE CASCADE, 

    CONSTRAINT fk_user_email FOREIGN KEY ( email )
        REFERENCES users( email )
        ON DELETE CASCADE
        ON UPDATE CASCADE, 

    CONSTRAINT fk_rating_type_name FOREIGN KEY ( rating_type_name )
        REFERENCES rating_types( name )
        ON DELETE CASCADE
        ON UPDATE CASCADE 
);
