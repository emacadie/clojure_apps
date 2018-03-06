create table twitter_user (
    name                 character varying ( 100 ) not null,
    id_str               character varying (  20 ) not null,
    id                   bigint                    not null,
    screen_name          character varying ( 100 ) not null,
    description          character varying ( 200 ) not null,
    location             character varying ( 200 ) not null,
    lang                 character ( 2 )           not null,
    display_url          character varying ( 100 ) not null, -- (get-in map-from-twitter [:body :entities :url :urls 0 :display_url])
    expaned_url          character varying ( 100 ) not null, -- (get-in ebody [:entities :url :urls 0 :expanded_url])
    friends_count        bigint not null,
    followers_count      bigint not null,
    has_extended_profile boolean,
    protected            boolean,
    statuses_count       bigint,
    time_zone            character varying ( 50 ),
    utc_offset           bigint,
    created_at  TIMESTAMP  not null, -- when user was created on Twitter -- "Sun Dec 20 18:17:33 +0000 2009" "Thu Feb 04 18:04:20 +0000 2010"
    record_created_at  TIMESTAMP default clock_timestamp() not null -- when this record was created
        
);

create table twitter_auth (
    twitter_auth_id          serial primary key            not null, -- incremented id
    twitter_user_name        character varying (  50 )     not null,
    app_consumer_key         character varying ( 100 )     not null,
    app_consumer_secret      character varying ( 100 )     not null,
    user_access_token        character varying ( 100 )     not null,
    user_access_token_secret character varying ( 100 )     not null,
    record_created_at  TIMESTAMP default clock_timestamp() not null
);
CREATE INDEX twitter_auth_twitter_user_name ON twitter_auth ( twitter_user_name );

