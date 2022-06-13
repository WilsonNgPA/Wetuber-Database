# Wetuber-Database

## Group Members
- Nga Shan (Wendy) Ngan
- Pei Aou Wilson Ng
- Yuhao Ma

## Overview
The code is included in the folder Wetuber. With Intellij, build the project on Wetuber/src. 
After compiling, Wetuber.java is the main file to run the application. 

Also in the Wetuber.zip file, two SQL scripts can be found in Wetuber\src\ca\ubc\cs304\sql\scripts
databaseSetup.sql file contains the required statements to setup all the tables and data;
queries.sql has the example queries that can be performed in our application.

Our final project, the Wetuber database, consists of tables: channel, wetuber, video, supporter
and subscribe. In the user interface, the user who is a Wetube administrator, can view the
tables channels, wetubers, videos, supporter and subscribe. They can perform operations such
as: insert rows into these tables, delete channel and wetuber, update channel name and
password. In the wetuber table, they can select any attributes (name, email, date of birth) as
desired. In the video table, they can input a keyword to search for a video based on title, they
can also find the channel info based on a given vid. In the channel table, they can find the
highest number of subscribers out of all channels and see all the active channels that have at
least average 20 likes of the videos uploaded, at least 2 videos uploaded and at least 10
subscribers. In the supporter table, they can find how many supporters created their account
for each day and find the subscribers who subscribed to all channels.

Overall, this database stores the core information in the Wetuber video platform up to an
extent, since not all tables in the original schema are implemented. In the initial project
description in milestone 1, we restricted Wetube administrators from changing the channel
password, however this has changed and they are allowed to change the channel password. In
the original schema, we had upload_video_1 and upload_video_2 tables from normalization,
but in our database implementation we had only included upload_video_1 which is called
video table in our user interface. As well, we decided to remove attributes ‘num_vid’,
‘total_video_dislikes’, ‘total_video_likes’ to avoid conflicts in the video table that already
has the attributes ‘num_likes’ and ‘num_dislikes’, as we can perform queries to get these
values instead. We have also changed all the foreign keys with on-delete-cascade
implementation.
