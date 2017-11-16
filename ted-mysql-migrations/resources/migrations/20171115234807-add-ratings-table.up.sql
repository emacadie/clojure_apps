CREATE TABLE ratings (
    artist_name text NOT NULL,
    email text NOT NULL,
    rating_type_name text NOT NULL,
    rating integer NOT NULL,
    at timestamp with time zone DEFAULT now()
);
