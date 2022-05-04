drop table channel cascade constraints;
drop table supporter cascade constraints;
drop table subscribe cascade constraints;
drop table video cascade constraints;
drop table wetuber cascade constraints;

CREATE TABLE channel (
    cid integer PRIMARY KEY,
    name varchar2(20) UNIQUE,
    num_subs integer,
    num_post integer,
    total_video_views integer,
    password varchar2(20)
);

CREATE TABLE supporter (
    sid integer PRIMARY KEY,
    name varchar2(20),
    start_time Date
);

CREATE TABLE subscribe (
    cid integer,
    sid integer,
    start_date Date,
    duration integer,
    PRIMARY KEY(cid, sid),
    FOREIGN KEY (cid) REFERENCES channel
    ON DELETE CASCADE,
    FOREIGN KEY (sid) REFERENCES supporter
    ON DELETE CASCADE
);

CREATE TABLE video (
    vid integer PRIMARY KEY,
    cid integer not null,
    start_date Date,
    title varchar2(100),
    description varchar2(200),
    num_dislike integer,
    num_like integer,
    link varchar2(2083) UNIQUE,
    FOREIGN KEY (cid) REFERENCES channel
    ON DELETE CASCADE
);

CREATE TABLE wetuber (
    wid integer PRIMARY KEY,
    name varchar2(20),
    email varchar2(50) UNIQUE,
    date_birth Date
);

INSERT INTO channel VALUES (001, 'Amy_Cooking', 234, 5, 5065, 'IloveCats');
INSERT INTO channel VALUES (002, 'Bob_Paints', 1834, 1, 50345, 'ree34');
INSERT INTO channel VALUES (003, 'Kid_Sings', 10, 12, 140, 'Bee22');
INSERT INTO channel VALUES (004, 'Sunny_Vlog', 52, 25, 6750, 'dogfox6');
INSERT INTO channel VALUES (005, 'we_news', 10650, 1230, 9320670, 'w3news');


INSERT INTO supporter VALUES (001, 'Anon_hacker', TO_DATE('2020/02/20', 'yyyy/mm/dd'));
INSERT INTO supporter VALUES (002, 'Welkin', TO_DATE('2022/03/02', 'yyyy/mm/dd'));
INSERT INTO supporter VALUES (003, 'A good husband', TO_DATE('2021/12/25', 'yyyy/mm/dd'));
INSERT INTO supporter VALUES (004, 'Sotired', TO_DATE('2022/01/15', 'yyyy/mm/dd'));
INSERT INTO supporter VALUES (005, '4Chicken', TO_DATE('2022/01/15', 'yyyy/mm/dd'));

INSERT INTO subscribe VALUES (001, 001, TO_DATE('2022/01/01', 'yyyy/mm/dd'), 2);
INSERT INTO subscribe VALUES (003, 004, TO_DATE('2022/01/03', 'yyyy/mm/dd'), 15);
INSERT INTO subscribe VALUES (003, 001, TO_DATE('2022/04/05', 'yyyy/mm/dd'), 5);
INSERT INTO subscribe VALUES (004, 005, TO_DATE('2022/11/12', 'yyyy/mm/dd'), 6);
INSERT INTO subscribe VALUES (002, 001, TO_DATE('2022/12/31', 'yyyy/mm/dd'), 50);
INSERT INTO subscribe VALUES (004, 001, TO_DATE('2022/12/31', 'yyyy/mm/dd'), 50);
INSERT INTO subscribe VALUES (005, 001, TO_DATE('2022/12/31', 'yyyy/mm/dd'), 50);

INSERT INTO video VALUES (001, 001, TO_DATE('2020/08/20', 'yyyy/mm/dd'), 'Spaghetti', 'Dinner Time!', 14, 47, 'www.wetube.com/fhsd87h4');
INSERT INTO video VALUES (002, 002, TO_DATE('2020/09/04', 'yyyy/mm/dd'), 'Trees', 'Forest Painting', 3, 167, 'www.wetube.com/dsfjf445');
INSERT INTO video VALUES (003, 001, TO_DATE('2020/12/25', 'yyyy/mm/dd'), 'Sandwich', 'Lunch Time!', 57, 2, 'www.wetube.com/fj82j344');
INSERT INTO video VALUES (004, 003, TO_DATE('2021/05/30', 'yyyy/mm/dd'), 'ABC Song', 'I love the alphabets hehe', 9, 96, 'www.wetube.com/oghkio84');
INSERT INTO video VALUES (005, 004, TO_DATE('2022/01/01', 'yyyy/mm/dd'), 'Terrible Day', 'I stepped on poop so sad', 1, 23, 'www.wetube.com/retjo4i5635');

INSERT INTO wetuber VALUES (001, 'Apple_pie', 'lovepie@163.com', TO_DATE('2002/03/02', 'yyyy/mm/dd'));
INSERT INTO wetuber VALUES (002, 'EsportinToday', 'eplayer88@gmail.com', TO_DATE('1988/05/01', 'yyyy/mm/dd'));
INSERT INTO wetuber VALUES (003, 'Coco the Cat', 'cattycatty@gmail.com', TO_DATE('2000/12/20', 'yyyy/mm/dd'));
INSERT INTO wetuber VALUES (004, 'Sunny', 'sleep1527@gmail.com', TO_DATE('1995/11/25', 'yyyy/mm/dd'));
INSERT INTO wetuber VALUES (005, 'Color_sky', 'bob0820@gmail.com', TO_DATE('2003/08/20', 'yyyy/mm/dd'));