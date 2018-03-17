/*
From HugSQL docs:
:result followed by
:affected or :n = number of rows affected (inserted/updated/deleted)
:raw = passthrough an untouched result (default)
:many or :* = many rows as a vector of hash-maps

:command followed by
:query or :? = query with a result-set (default)
:execute or :! = any statement
*/

-- :name insert-user
-- :command :execute 
-- :result :affected
-- :doc insert new users
insert into twitter_user (
    name, user_id_str, user_id, screen_name, description, location,    
    lang, display_url, expanded_url, friends_count, followers_count, 
    has_extended_profile, protected, statuses_count, time_zone, utc_offset,           
    created_at 
) values (
    :name, :user_id_str, :user_id, :screen_name, :description, :location,    
    :lang, :display_url, :expanded_url, :friends_count, :followers_count, 
    :has_extended_profile, :protected, :statuses_count, :time_zone, :utc_offset,           
    :created_at 
);

-- :name insert-tweet
-- :command :execute
-- :result :affected
-- :doc insert tweet data
insert into twitter_tweet (
    tweet_id_str, tweet_id, full_text, -- display_text_range, 
    user_id,
    user_id_str, in_reply_to_screen_name, in_reply_to_status_id,
    in_reply_to_status_id_str, in_reply_to_user_id, 
    in_reply_to_user_id_str, created_at, batch_time
) values (
    :tweet_id_str, :tweet_id, :full_text, :user_id,
    :user_id_str, :in_reply_to_screen_name, :in_reply_to_status_id,
    :in_reply_to_status_id_str, :in_reply_to_user_id, 
    :in_reply_to_user_id_str, :created_at, :batch_time
);

-- :name check-user
-- :command :query
-- :result n
select count( screen_name) 
from twitter_user 
where lower( screen_name ) = lower( :screen_name )

-- :name get-max-tweet-id
-- :command :query
-- :result n
select max( t.tweet_id_str )
from twitter_tweet t
where t.user_id = (
      select u.user_id 
      from twitter_user u
      where lower( u.screen_name ) = lower( :screen_name )
)

-- :name insert-processed-tweet
-- :command :execute
-- :result :affected
insert into processed-tweet (
  tweet_id_str, tweet_id, user_id, user_id_str, final_html_text, created_at, batch_time
) values (
  :tweet_id_str, :tweet_id, :user_id, :user_id_str, :final_html_text, :created_at, :batch_time
)

-- :name artists
-- :command :query
-- :result :many
-- :doc select all with cols defined by {:cols [<col_name>...]}
SELECT :i*:cols  
  FROM artists

-- Note the terse style below
-- ":command :query" -> ":?"
-- ":result n" -> ":*" 

-- :name artist-by-id :? :*
-- :doc get artist info by music_brainz id
SELECT *  
  FROM artists
 WHERE mb_id = :id

-- :name artists-by-name-like :? :*
-- :doc use {:name-like "P%"} as the option param to get the P's
SELECT *  
  FROM artists
 WHERE name 
  LIKE :name-like

-- :name new-rating 
-- :command :execute 
-- :result :affected
-- :doc insert new ratings
INSERT INTO ratings (artist_name, email, rating_type_name, rating)  
VALUES (:artist_name, :email, :rating_type_name, :rating)

-- :name new-rating-type 
-- :command :execute 
-- :result :affected
-- :doc insert new rating-types
INSERT INTO rating_types (name, description)  
VALUES (:name, :description)

-- :name new-user 
-- :command :execute 
-- :result :affected
-- :doc insert new user
INSERT INTO users ( email,  pass, role )
VALUES (:email, :pass, :role)

-- :name new-users 
-- :command :execute 
-- :result :affected
/* :doc
Insert multiple users with :tuple* parameter type,
fields in this order: email, pass, role
*/
insert into users ( email, pass, role )
values :tuple*:users

-- :name users-by-email 
-- :command :query
-- :result :raw
select * from users where email=:email

-- :name users-all
-- :command :query
-- :result :many 
-- :doc select all the users with all the attributes
SELECT *  
  FROM users


-- :name update-rating-type 
-- :command :execute 
-- :result :affected
update rating_types set description = :description
where name = :name

-- :name delete-user 
-- :command :execute 
-- :result :affected
delete from users where email = :email   

-- :name delete-from-ratings-by-rating-type-name
-- :comamnd :execute
-- :result :affected
delete from ratings where rating_type_name = :rating_type_name

