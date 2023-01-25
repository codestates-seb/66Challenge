insert into category (type) values ('운동');
insert into category (type) values ('식습관');
insert into category (type) values ('학습');
insert into category (type) values ('일상생활');
insert into category (type) values ('취미');
insert into category (type) values ('셀프케어');
insert into category (type) values ('에코');
insert into category (type) values ('마음챙김');
insert into category (type) values ('기타');
insert into users (email, password, username) values ('jpetran0@google.cn', 'Ju9skkz', 'bhellwing0');
insert into users (email, password, username) values ('svanderwalt1@sciencedirect.com', 'SPLcH1AJRR', 'mcrighton1');
insert into users (email, password, username) values ('jbaudacci2@twitpic.com', 'moMZriaon', 'mnormand2');
insert into users (email, password, username) values ('mbarroux3@techcrunch.com', 'dXMNkROjx', 'kdows3');
insert into users (email, password, username) values ('lgiddings4@livejournal.com', 'VsCPPkNQwkJ', 'mkiwitz4');
insert into users (email, password, username) values ('cjosefs5@sciencedirect.com', 'Q3sgrO', 'whitzke5');
insert into users (email, password, username) values ('ostonary6@dion.ne.jp', 'sPq4Wf4V5T', 'cdainty6');
insert into users (email, password, username) values ('sosherrin7@opera.com', '49n8vbfKQOY', 'brusso7');
insert into users (email, password, username) values ('lswayne8@netvibes.com', 'SlnPfOvP9', 'bobray8');
insert into users (email, password, username) values ('vtomsett9@who.int', '7QA5EOUZS', 'clummasana9');
insert into users (email, password, username) values ('gmerana@nytimes.com', 'UZDITTK0W', 'adraysona');
insert into users (email, password, username) values ('sharmeb@printfriendly.com', 'EoiK0p', 'mposenb');
insert into users (email, password, username) values ('dyatesc@scientificamerican.com', 'bFdayu', 'cpitherickc');
insert into users (email, password, username) values ('kturevilled@vinaora.com', 'kFLIJJW0gH', 'lskewisd');
insert into users (email, password, username) values ('lbalmee@nba.com', '1PfbXMz8Sd', 'cholsteine');
insert into users (email, password, username) values ('bhartingtonf@cnet.com', '55zLUBe8Q4', 'qhazlewoodf');
insert into users (email, password, username) values ('fpuffettg@domainmarket.com', '7qjPxUan', 'gblowingg');
insert into users (email, password, username) values ('okincaidh@cyberchimps.com', 'bO5DUc0BAlTn', 'fgustickeh');
insert into users (email, password, username) values ('rgamlyni@squarespace.com', 'IfwR8YDcItJ', 'dclingoei');
insert into users (email, password, username) values ('bduddanj@typepad.com', 'fnFH17pTtv', 'abannellj');
insert into habit (title, sub_title, body, auth_start_time, auth_end_time, category_id, user_id) values ('매일매일 일기 쓰기', '매일일기', '매일매일 일기를 작성하며 나의 하루를 돌아봅시다.', '00:00', '23:59', 1, 1);
insert into habit (title, sub_title, body, auth_start_time, auth_end_time, category_id, user_id) values ('매일매일 독서하기', '매일독서', '매일매일 독서하며 소양과 지식을 기릅시다.', '00:00', '23:59', 1, 1);
insert into habit (title, sub_title, body, auth_start_time, auth_end_time, category_id, user_id) values ('하루 10000보 걷기', '만보걷기', '매일매일 만보를 채워 잠만보에서 벗어납시다.', '00:00', '23:59', 2, 2);
insert into habit (title, sub_title, body, auth_start_time, auth_end_time, category_id, user_id) values ('아침에 산책하기', '아침산책', '아침 산책으로 하루를 상쾌하게 시작합시다.', '05:00', '09:00', 2, 3);
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (1, 1, 'SUCCESS', '2022-10-12 07:30:00', '2022-01-25 23:15:26');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (1, 2, 'CHALLENGE', '2022-03-21 04:08:34', '2022-06-18 15:52:43');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (1, 3, 'SUCCESS', '2022-11-14 16:08:54', '2022-04-21 11:56:53');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (2, 1, 'FAIL', '2022-05-22 18:50:26', '2022-11-13 20:07:02');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (2, 2, 'SUCCESS', '2022-11-11 15:37:01', '2022-01-16 12:14:56');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (2, 3, 'CHALLENGE', '2023-01-01 17:01:38', '2022-10-25 23:01:24');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (3, 1, 'FAIL', '2022-03-31 06:36:46', '2022-09-28 20:45:42');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (3, 2, 'SUCCESS', '2022-03-23 07:24:10', '2022-05-09 22:26:44');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (3, 3, 'FAIL', '2022-08-02 21:49:01', '2022-07-10 03:22:26');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (4, 1, 'CHALLENGE', '2022-07-08 14:39:35', '2022-07-17 13:52:11');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (4, 2, 'CHALLENGE', '2022-04-22 01:26:22', '2022-11-13 17:12:22');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (4, 3, 'FAIL', '2022-06-18 05:12:19', '2022-11-01 10:02:32');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (4, 4, 'CHALLENGE', '2022-05-03 05:10:21', '2022-07-30 10:52:58');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (5, 1, 'SUCCESS', '2022-05-04 17:04:24', '2022-09-14 03:34:43');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (5, 2, 'FAIL', '2022-05-29 19:04:48', '2022-11-02 06:31:30');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (5, 3, 'FAIL', '2022-03-21 09:40:25', '2021-12-15 13:06:52');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (5, 4, 'SUCCESS', '2022-05-25 05:51:22', '2022-10-24 02:06:15');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (6, 1, 'FAIL', '2022-06-12 21:15:51', '2022-07-12 23:00:09');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (6, 2, 'FAIL', '2022-08-14 14:38:57', '2022-08-01 17:01:59');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (6, 3, 'CHALLENGE', '2022-09-19 15:27:21', '2022-01-08 03:37:20');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (6, 4, 'FAIL', '2022-04-28 21:57:19', '2022-02-27 16:37:49');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (7, 1, 'CHALLENGE', '2022-04-01 17:17:42', '2022-08-12 11:21:01');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (7, 2, 'FAIL', '2022-02-01 05:34:39', '2022-07-14 21:06:58');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (7, 3, 'SUCCESS', '2022-12-16 20:13:55', '2022-09-15 17:57:06');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (7, 4, 'SUCCESS', '2022-07-10 22:13:33', '2022-09-28 03:53:00');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (8, 1, 'FAIL', '2022-04-21 14:41:40', '2022-07-08 17:41:34');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (8, 2, 'SUCCESS', '2022-09-25 10:12:03', '2022-07-04 21:08:55');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (8, 3, 'CHALLENGE', '2022-03-06 18:10:15', '2022-09-09 14:48:50');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (8, 4, 'CHALLENGE', '2022-10-12 02:47:33', '2022-03-02 03:13:04');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (9, 1, 'CHALLENGE', '2022-05-23 20:10:04', '2022-05-13 16:16:16');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (9, 2, 'FAIL', '2022-09-13 22:32:32', '2022-01-08 09:46:02');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (9, 3, 'SUCCESS', '2022-03-22 03:06:02', '2022-02-25 12:57:42');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (9, 4, 'FAIL', '2022-09-09 18:21:38', '2022-08-22 16:59:17');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (10, 1, 'FAIL', '2022-02-14 21:01:13', '2022-05-23 06:36:08');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (10, 2, 'SUCCESS', '2022-08-11 12:51:41', '2022-07-12 06:39:50');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (10, 3, 'CHALLENGE', '2022-06-20 03:42:00', '2022-10-11 21:53:00');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (10, 4, 'FAIL', '2022-03-25 05:22:11', '2023-01-09 17:32:54');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (11, 1, 'CHALLENGE', '2022-11-24 18:53:32', '2022-09-29 09:04:43');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (11, 2, 'CHALLENGE', '2022-05-21 23:58:55', '2022-09-09 15:27:15');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (11, 3, 'SUCCESS', '2022-04-27 16:41:51', '2021-12-16 17:26:12');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (11, 4, 'FAIL', '2022-09-06 19:29:27', '2022-07-05 21:40:46');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (12, 1, 'CHALLENGE', '2022-05-02 16:38:12', '2022-03-07 03:31:01');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (12, 2, 'FAIL', '2022-07-02 02:51:19', '2022-04-24 01:14:37');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (12, 3, 'FAIL', '2022-07-10 23:09:43', '2022-11-09 15:09:05');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (12, 4, 'SUCCESS', '2023-01-05 01:58:32', '2022-04-18 12:31:08');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (13, 1, 'FAIL', '2022-03-07 18:13:01', '2022-05-08 04:14:51');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (13, 2, 'FAIL', '2022-11-24 17:07:30', '2022-06-18 07:58:11');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (13, 3, 'SUCCESS', '2022-08-25 08:49:49', '2022-04-24 08:29:25');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (13, 4, 'FAIL', '2022-07-24 10:21:17', '2022-05-16 06:27:31');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (14, 1, 'FAIL', '2022-12-03 18:35:02', '2022-04-23 09:54:53');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (14, 2, 'FAIL', '2022-05-20 22:51:59', '2022-09-05 22:51:22');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (14, 3, 'CHALLENGE', '2022-12-25 19:30:22', '2022-08-01 19:15:26');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (14, 4, 'FAIL', '2022-12-13 21:25:46', '2022-11-06 13:24:44');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (15, 1, 'FAIL', '2022-12-05 11:49:16', '2022-03-25 22:15:36');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (15, 2, 'FAIL', '2022-03-19 08:03:02', '2022-01-22 19:53:49');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (15, 3, 'SUCCESS', '2022-06-24 12:46:10', '2021-12-15 08:51:02');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (15, 4, 'SUCCESS', '2022-11-08 20:39:30', '2022-03-14 06:29:17');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (16, 1, 'SUCCESS', '2022-02-26 18:41:50', '2022-01-14 00:36:33');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (16, 2, 'CHALLENGE', '2022-06-03 13:57:22', '2022-12-27 06:47:15');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (16, 3, 'CHALLENGE', '2022-09-27 10:04:41', '2022-05-01 08:05:20');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (16, 4, 'FAIL', '2022-10-11 07:30:00', '2022-01-25 23:10:26');
insert into challenge (user_id, habit_id, status, last_auth_at, created_at) values (17, 1, 'SUCCESS', '2022-11-01 15:37:01', '2022-01-16 12:10:56');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Vivamus vel nulla eget eros elementum pellentesque.', 1, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Aenean lectus.', 2, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Morbi non lectus.', 3, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.', 4, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Donec semper sapien a libero.', 5, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Praesent id massa id nisl venenatis lacinia.', 6, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Cras pellentesque volutpat dui.', 7, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nullam varius.', 8, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nulla justo.', 9, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Pellentesque eget nunc.', 10, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'In sagittis dui vel nisl.', 11, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nulla tellus.', 12, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Etiam justo.', 13, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Fusce consequat.', 14, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit.', 15, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nulla mollis molestie lorem.', 16, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo.', 17, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Morbi a ipsum.', 18, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl.', 19, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Sed ante.', 20, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Vestibulum quam sapien, varius ut, blandit non, interdum in, ante.', 21, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Sed ante.', 22, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Duis mattis egestas metus.', 23, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Vestibulum sed magna at nunc commodo placerat.', 24, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nullam sit amet turpis elementum ligula vehicula consequat.', 25, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Duis ac nibh.', 26, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci.', 27, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Pellentesque ultrices mattis odio.', 28, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.', 29, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Proin risus.', 30, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Praesent blandit lacinia erat.', 31, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Ut tellus.', 32, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'In sagittis dui vel nisl.', 33, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Aliquam sit amet diam in magna bibendum imperdiet.', 34, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Fusce consequat.', 35, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Suspendisse potenti.', 36, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nulla justo.', 37, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nunc rhoncus dui vel sem.', 38, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Suspendisse potenti.', 39, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.', 40, '2023-01-15 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.', 1, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'In eleifend quam a odio.', 2, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Cras non velit nec nisi vulputate nonummy.', 3, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Vivamus vel nulla eget eros elementum pellentesque.', 4, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nunc rhoncus dui vel sem.', 5, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci.', 6, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nunc nisl.', 7, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Ut tellus.', 8, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue.', 9, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit.', 10, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Curabitur gravida nisi at nibh.', 11, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Sed vel enim sit amet nunc viverra dapibus.', 12, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nulla ut erat id mauris vulputate elementum.', 13, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.', 14, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Etiam justo.', 15, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Etiam justo.', 16, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Proin at turpis a pede posuere nonummy.', 17, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nulla mollis molestie lorem.', 18, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Curabitur gravida nisi at nibh.', 19, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Duis bibendum.', 20, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', 21, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa.', 22, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Morbi ut odio.', 23, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Morbi ut odio.', 24, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Aenean fermentum.', 30, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo.', 25, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nulla ac enim.', 26, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Nullam sit amet turpis elementum ligula vehicula consequat.', 27, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.', 28, '2023-01-16 00:00:00');
insert into auth (auth_image_url, body, challenge_id, created_at) values ('https://challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com/images/3cb6c7f7-8af9-4957-92d4-eb46785277de.jpeg', 'Praesent lectus.', 29, '2023-01-16 00:00:00');
insert into wildcard (challenge_id) values (10);
insert into wildcard (challenge_id) values (10);
insert into wildcard (challenge_id) values (11);
insert into wildcard (challenge_id) values (11);
insert into wildcard (challenge_id) values (12);
insert into wildcard (challenge_id) values (12);
insert into bookmark (user_id, habit_id, created_at) values (1, 2, '2022-06-18 05:12:19');
insert into bookmark (user_id, habit_id, created_at) values (1, 3, '2022-10-24 02:06:15');
insert into bookmark (user_id, habit_id, created_at) values (2, 1, '2022-06-18 05:12:19');
insert into bookmark (user_id, habit_id, created_at) values (2, 2, '2022-10-24 02:06:15');
insert into report (post_id, post_type, report_type, reporter_user_id, reported_user_id, created_at) values ('1','HABIT','ABUSE','1','2','2023-01-23 00:00:00');
insert into report (post_id, post_type, report_type, reporter_user_id, reported_user_id, created_at) values ('2','HABIT','ABUSE','3','2','2023-01-23 21:00:00');
insert into report (post_id, post_type, report_type, reporter_user_id, reported_user_id, created_at) values ('1','AUTH','ADVERTISEMENT','1','2','2023-01-23 10:00:00');
insert into report (post_id, post_type, report_type, reporter_user_id, reported_user_id, created_at) values ('1','REVIEW','ADVERTISEMENT','2','4','2023-01-23 08:30:00');
insert into review (habit_id, user_id, score, body) values (1, 4, 5, '내 인생이 달라졌어요');
insert into review (habit_id, user_id, score, body) values (1, 7, 4, '66챌린지 최고!!');
insert into review (habit_id, user_id, score, body) values (1, 10, 3, '이불 개면 내 인생이 뭐가 달라지나요?');
insert into review (habit_id, user_id, score, body) values (1, 11, 4, '굿');
insert into review (habit_id, user_id, score, body) values (1, 13, 1, '별로');
insert into review (habit_id, user_id, score, body) values (2, 15, 3, '할만했어요');
insert into review (habit_id, user_id, score, body) values (2, 18, 4, '재밌다');
insert into review (habit_id, user_id, score, body) values (2, 20, 5, 'god challenge');
insert into review (habit_id, user_id, score, body) values (2, 8, 5, '얏호 성공!');
insert into review (habit_id, user_id, score, body) values (2, 9, 5, '대박');