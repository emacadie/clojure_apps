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

-- :name artists-all
-- :command :query
-- :result n 
-- :doc select all the artists with all the attributes
SELECT *  
  FROM artists

-- :name artists
-- :command :query
-- :result n
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
-- :result n 
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

