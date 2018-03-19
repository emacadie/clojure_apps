create table twitter_user (
    name                 character varying ( 100 ) not null,
    user_id_str          character varying (  20 ) not null, -- via twitter as id_str
    user_id              bigint PRIMARY KEY        not null unique, -- provided by twitter as id
    screen_name          character varying ( 100 ) not null,
    description          character varying ( 200 ) not null,
    location             character varying ( 200 ) not null,
    lang                 character ( 2 )           not null,
    display_url          character varying ( 100 ), -- (get-in map-from-twitter [:body :entities :url :urls 0 :display_url])
    expanded_url         character varying ( 100 ), -- (get-in ebody [:entities :url :urls 0 :expanded_url])
    friends_count        bigint not null,
    followers_count      bigint not null,
    has_extended_profile boolean,
    protected            boolean,
    statuses_count       bigint,
    time_zone            character varying ( 50 ),
    utc_offset           bigint,
    created_at           TIMESTAMP  not null, --"Sun Dec 20 18:17:33 +0000 2009" "Thu Feb 04 18:04:20 +0000 2010"
    record_created_at    TIMESTAMP default clock_timestamp() not null
);

create table twitter_tweet (
    auto_id                   serial primary key not null, -- provided by postgres
    tweet_id_str              character varying ( 100 ) not null, -- via twitter as id_str
    tweet_id                  bigint unique not null, -- provided by twitter as id
    full_text                 character varying ( 500 ) not null,
    -- display_text_range        int not null,
    user_id                   bigint not null, -- user who sent tweet
    user_id_str               character varying ( 100 ) not null,
    in_reply_to_screen_name   character varying ( 100 ),
    in_reply_to_status_id     bigint,
    in_reply_to_status_id_str character varying ( 100 ),
    in_reply_to_user_id       bigint,
    in_reply_to_user_id_str   character varying ( 100 ),
    created_at                TIMESTAMP  not null, --"Sun Dec 20 18:17:33 +0000 2009" "Thu Feb 04 18:04:20 +0000 2010",
    record_created_at         TIMESTAMP default clock_timestamp() not null,
    batch_time                TIMESTAMP  not null, --"Sun Dec 20 18:17:33 +0000 2009" "Thu Feb 04 18:04:20 +0000 2010"
    FOREIGN KEY ( user_id ) REFERENCES twitter_user ( user_id ) on delete cascade
);

create table processed_tweet (
    auto_id           serial primary key not null, -- provided by postgres
    tweet_id_str      character varying ( 100 )  not null, -- via twitter as id_str
    tweet_id          bigint unique not null, -- provided by twitter as id
    user_id           bigint not null, -- user who sent tweet
    user_id_str       character varying ( 100 )  not null,
    final_html_text   character varying ( 2000 ) not null,
    created_at        TIMESTAMP  not null, --"Sun Dec 20 18:17:33 +0000 2009" "Thu Feb 04 18:04:20 +0000 2010",
    record_created_at TIMESTAMP default clock_timestamp() not null,
    batch_time        TIMESTAMP  not null, --"Sun Dec 20 18:17:33 +0000 2009" "Thu Feb 04 18:04:20 +0000 2010"
    FOREIGN KEY ( user_id )  REFERENCES twitter_user  ( user_id  ) on delete cascade,
    FOREIGN KEY ( tweet_id ) REFERENCES twitter_tweet ( tweet_id ) on delete cascade
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

