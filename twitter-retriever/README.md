# twitter-retriever

A Clojure app to automate the retrieval of tweets and write them to a database and print them to an HTML file.   

The original plan was to use the WordPress REST API to post them to WordPress. I spent some time trying to do this and I was unsuccessful. So for the time being you will have to copy and paste.    

You will be able to use cron to run this every day/week/whenever to archive your tweets and/or display them on your site.   

The DB used is Postgres. You will need to get your own Twitter OAuth credentials.   

The "main" function is in twitter_retriever/work.clj.  

The OAuth creds are stored in the db in a table called twitter_auth:
```sql
insert into twitter_auth (
    twitter_user_name, app_consumer_key, app_consumer_secret, user_access_token, user_access_token_secret
) values (
    'YourNameHere',                         -- twitter_user_name
    '39bed06b-7ddc-461c-9fb0-8403d4bb6b0f', -- app_consumer_key
    '871c441f-a7cb-4155-9f05-bde679da33a2', -- app_consumer_secret
    '8caa1f65-d7ab-465e-a936-d35316e04d64', -- user_access_token
    '234fc214-467c-4da2-b8a9-41dea17525b4'  -- user_access_token_secret
);
```

Right now I am running with lein. Here is a command:   
```shell
lein run -m twitter-retriever.work --user=GitHub --oauth=YourNameHere
```
--user is the name of the user whose tweets you want to retrieve. --oauth is the name of the user whose credentials you are using.   

You might only have one user whose credentials you will use, but it's one less file to manage for you. You're welcome.   

The first time you call it for a user, it will get the most recent 3200 tweets. I do not fetch retweets, but retweets do count towards the 3200. The 3200 limit is set by Twitter.   

Every time you call it for the same user after that, it should get any tweets since the last time you called, again up to 3200. If you are missing tweets, increase the frequency. You should not have to specify whether or not you have retrieved tweets for a user in the past. The app should be able to figure out what to do.  

## Usage

FIXME

Uses [twitter-api "1.8.0"] at https://github.com/adamwynne/twitter-api   
and [clojurewerkz/propertied "1.3.0"] at https://github.com/michaelklishin/propertied   
and [clj-http "3.7.0"] at https://github.com/dakrone/clj-http   

## License



Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
