---DIVISION QUERY---
--return sid and name of subscriber who subscribed to all channels
SELECT DISTINCT sup.sid, sup.name
FROM supporter sup
WHERE NOT EXISTS(
    (SELECT cid FROM channel c) MINUS
    (SELECT DISTINCT sub.cid FROM subscribe sub WHERE sub.sid = sup.sid));

---JOIN QUERY---
--Given a vid, find the channel info
SELECT DISTINCT v.vid, v.title, c.name, c.num_subs
FROM video v, channel c
WHERE v.vid = 003 AND v.cid = c.cid;

---INSERT OPERATIONS---
--insert row into channel table
INSERT INTO channel
VALUES(006, 'beach_sunset', 10, 20, 2000, 'abc123');
--insert row into video table
INSERT INTO video
VALUES(010, 002, TO_DATE('2021/01/20', 'yyyy/mm/dd'), 'New House', 'So happy', 32, 63, 'www.wetube.com/sfdiu34');
--insert row into wetuber table
INSERT INTO wetuber
VALUES(006, 'yogamat', 'peace123@gmail.com', TO_DATE('2000/01/10', 'yyyy/mm/dd'));

--insert row into supporter table
INSERT INTO supporter
VALUES(123, 'Catty', TO_DATE('2021/05/06', 'yyyy/mm/dd'));

---DELETE OPERATIONS---
--delete row from channel table given cid
DELETE FROM channel WHERE cid = 001;
--delete row from wetuber table given wid
DELETE FROM wetuber WHERE wid = 004;

---UPDATE OPERATIONS---
--change channel name given cid
UPDATE channel SET name = 'catlady' WHERE cid = 003;
--change channel password given cid
UPDATE channel SET password = 'newpw' WHERE cid = 005;

---SELECTION QUERY---
--return all videos with the keyword in title
SELECT * FROM video WHERE title LIKE '%Day%';

---PROJECTION QUERY---
--choose any attributes from wetuber table
SELECT name,email FROM wetuber;
SELECT date_birth FROM wetuber;

---AGGREGATION QUERY---
--return the highest number of subscribers out of all the channels
SELECT cid, name, num_subs
FROM channel
WHERE num_subs = (SELECT MAX(num_subs) FROM channel);

---NESTED AGGREGATION WITH GROUP-BY QUERY---
--return cid and name of the channels where:
--(1) the average likes of the videos uploaded on that channel is at least 20
--(2) the channel has at least 2 videos uploaded
--(3) the channel has at least 10 subscribers
SELECT c.cid, c.name
FROM channel c, video v
WHERE c.cid = v.cid
GROUP BY c.cid, c.name
HAVING AVG(v.num_like) >= 20 AND COUNT(v.vid) >= 2 AND MAX(c.num_subs) >= 10;

SELECT start_time, COUNT(sid) FROM supporter GROUP BY start_time;









