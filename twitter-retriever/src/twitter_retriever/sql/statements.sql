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
)

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
)

-- :name check-user
-- :command :query
-- :result n
select count( screen_name) 
from twitter_user 
where lower( screen_name ) = lower( :screen_name )

-- :name get-max-tweet-id
-- :command :query
-- :result n
select max( t.tweet_id )
from twitter_tweet t
where t.user_id = (
      select u.user_id 
      from twitter_user u
      where lower( u.screen_name ) = lower( :screen_name )
)

-- :name get-min-tweet-id
-- :command :query
-- :result n
select min( t.tweet_id )
from twitter_tweet t
where t.user_id = (
      select u.user_id 
      from twitter_user u
      where lower( u.screen_name ) = lower( :screen_name )
)

-- :name get-min-tweet-id-for-batch
-- :command :query
-- :result n
select min( t.tweet_id )
from twitter_tweet t
where t.user_id = (
      select u.user_id 
      from twitter_user u
      where lower( u.screen_name ) = lower( :screen_name )
) and t.batch_time = :batch_time

-- :name insert-processed-tweet
-- :command :execute
-- :result :affected
insert into processed_tweet (
  tweet_id_str, tweet_id, user_id, user_id_str, final_html_text, created_at, batch_time
) values (
  :tweet_id_str, :tweet_id, :user_id, :user_id_str, :final_html_text, :created_at, :batch_time
)

-- :name get-processed-tweets-by-user
-- :command :query
-- :result :many
select p.auto_id, p.tweet_id, p.user_id, p.final_html_text, p.created_at
from processed_tweet p
join twitter_user u on u.user_id = p.user_id and lower( u.screen_name ) = lower( :user_name )
where p.batch_time = :batch-time
order by p.created_at

-- :name get-twitter-auth
-- :command :query
-- :result :one
select * from twitter_auth
where twitter_user_name = :twitter_auth_user


