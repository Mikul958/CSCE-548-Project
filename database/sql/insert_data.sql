USE speedrun_app;

-- -------------------------
-- Insert Games (10 rows)
-- -------------------------
INSERT INTO Game (title, description, thumbnail_url, release_date, developer, publisher) VALUES
('Celeste', 'Precision platformer about climbing a mountain.', 'https://example.com/img/celeste.jpg', '2018-01-25', 'Extremely OK Games', 'Matt Makes Games'),
('Super Mario 64', 'Classic 3D Mario platformer.', 'https://example.com/img/sm64.jpg', '1996-09-26', 'Nintendo', 'Nintendo'),
('The Legend of Zelda: Ocarina of Time', 'Action adventure in Hyrule.', 'https://example.com/img/oot.jpg', '1998-11-21', 'Nintendo', 'Nintendo'),
('Portal', 'Puzzle game using portals.', 'https://example.com/img/portal.jpg', '2007-10-10', 'Valve', 'Valve'),
('Half-Life 2', 'Story-driven FPS.', 'https://example.com/img/hl2.jpg', '2004-11-16', 'Valve', 'Valve'),
('Hollow Knight', 'Metroidvania in Hallownest.', 'https://example.com/img/hk.jpg', '2017-02-24', 'Team Cherry', 'Team Cherry'),
('Dark Souls', 'Challenging action RPG.', 'https://example.com/img/ds1.jpg', '2011-09-22', 'FromSoftware', 'Bandai Namco'),
('Minecraft', 'Sandbox survival builder.', 'https://example.com/img/minecraft.jpg', '2011-11-18', 'Mojang', 'Mojang'),
('Super Metroid', 'Classic sci-fi exploration platformer.', 'https://example.com/img/sm.jpg', '1994-03-19', 'Nintendo', 'Nintendo'),
('Cuphead', 'Run and gun boss rush with 1930s art.', 'https://example.com/img/cuphead.jpg', '2017-09-29', 'Studio MDHR', 'Studio MDHR');


-- -------------------------
-- Insert Authors (10 rows)
-- -------------------------
INSERT INTO Author (username, display_name, password_hash, profile_picture_url, create_date) VALUES
('speed_demon', 'Speed Demon', '$2b$10$aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'https://example.com/pfp/1.jpg', '2024-01-10'),
('frameperfect', 'Frame Perfect', '$2b$10$bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', 'https://example.com/pfp/2.jpg', '2024-02-11'),
('glitchhunter', 'Glitch Hunter', '$2b$10$cccccccccccccccccccccccccccccccccccccccccccccccccc', 'https://example.com/pfp/3.jpg', '2024-03-12'),
('route_master', 'Route Master', '$2b$10$dddddddddddddddddddddddddddddddddddddddddddddddddd', 'https://example.com/pfp/4.jpg', '2024-04-13'),
('resetking', 'Reset King', '$2b$10$eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee', 'https://example.com/pfp/5.jpg', '2024-05-14'),
('console_pro', 'Console Pro', '$2b$10$ffffffffffffffffffffffffffffffffffffffffffffffffff', 'https://example.com/pfp/6.jpg', '2024-06-15'),
('pc_runner', 'PC Runner', '$2b$10$11111111111111111111111111111111111111111111111111', 'https://example.com/pfp/7.jpg', '2024-07-16'),
('nohitlegend', 'No Hit Legend', '$2b$10$22222222222222222222222222222222222222222222222222', 'https://example.com/pfp/8.jpg', '2024-08-17'),
('marathoner', 'Marathon Runner', '$2b$10$33333333333333333333333333333333333333333333333333', 'https://example.com/pfp/9.jpg', '2024-09-18'),
('stratfinder', 'Strat Finder', '$2b$10$44444444444444444444444444444444444444444444444444', 'https://example.com/pfp/10.jpg', '2024-10-19');


-- -------------------------
-- Insert Runs (70 rows)
-- game_id and author_id range from 1-10
-- -------------------------
INSERT INTO Run (game_id, author_id, category, time_milliseconds, video_url, set_date, verified) VALUES
-- Celeste
(1,1,'Any%',1620000,'https://youtu.be/run1','2025-01-01',TRUE),
(1,2,'Any%',1580000,'https://youtu.be/run2','2025-01-02',TRUE),
(1,3,'100%',3240000,'https://youtu.be/run3','2025-01-03',TRUE),
(1,4,'Any%',1700000,'https://youtu.be/run4','2025-01-04',FALSE),
(1,5,'Any%',1650000,'https://youtu.be/run5','2025-01-05',TRUE),
(1,6,'All Hearts',4100000,'https://youtu.be/run6','2025-01-06',FALSE),
(1,7,'Any%',1595000,'https://youtu.be/run7','2025-01-07',TRUE),

-- SM64
(2,1,'16 Star',1200000,'https://youtu.be/run8','2025-01-08',TRUE),
(2,2,'70 Star',3600000,'https://youtu.be/run9','2025-01-09',TRUE),
(2,3,'120 Star',7200000,'https://youtu.be/run10','2025-01-10',TRUE),
(2,4,'16 Star',1185000,'https://youtu.be/run11','2025-01-11',TRUE),
(2,5,'70 Star',3550000,'https://youtu.be/run12','2025-01-12',FALSE),
(2,6,'16 Star',1220000,'https://youtu.be/run13','2025-01-13',TRUE),
(2,7,'120 Star',7300000,'https://youtu.be/run14','2025-01-14',FALSE),

-- OOT
(3,2,'Any%',5400000,'https://youtu.be/run15','2025-01-15',TRUE),
(3,3,'Any%',5300000,'https://youtu.be/run16','2025-01-16',TRUE),
(3,4,'100%',14400000,'https://youtu.be/run17','2025-01-17',FALSE),
(3,5,'Any%',5500000,'https://youtu.be/run18','2025-01-18',TRUE),
(3,6,'All Dungeons',9000000,'https://youtu.be/run19','2025-01-19',TRUE),
(3,7,'Any%',5250000,'https://youtu.be/run20','2025-01-20',TRUE),
(3,8,'Any%',5600000,'https://youtu.be/run21','2025-01-21',FALSE),

-- Portal
(4,1,'Any%',1100000,'https://youtu.be/run22','2025-01-22',TRUE),
(4,2,'Any%',1050000,'https://youtu.be/run23','2025-01-23',TRUE),
(4,3,'Glitchless',1400000,'https://youtu.be/run24','2025-01-24',TRUE),
(4,4,'Any%',1120000,'https://youtu.be/run25','2025-01-25',FALSE),
(4,5,'Any%',1080000,'https://youtu.be/run26','2025-01-26',TRUE),
(4,6,'Glitchless',1450000,'https://youtu.be/run27','2025-01-27',FALSE),
(4,7,'Any%',1040000,'https://youtu.be/run28','2025-01-28',TRUE),

-- Half-Life 2
(5,3,'Any%',4800000,'https://youtu.be/run29','2025-01-29',TRUE),
(5,4,'Any%',4700000,'https://youtu.be/run30','2025-01-30',TRUE),
(5,5,'Any%',4900000,'https://youtu.be/run31','2025-01-31',FALSE),
(5,6,'Any%',4650000,'https://youtu.be/run32','2025-02-01',TRUE),
(5,7,'Any%',4750000,'https://youtu.be/run33','2025-02-02',TRUE),
(5,8,'Any%',5000000,'https://youtu.be/run34','2025-02-03',FALSE),
(5,9,'Any%',4600000,'https://youtu.be/run35','2025-02-04',TRUE),

-- Hollow Knight
(6,1,'Any%',5400000,'https://youtu.be/run36','2025-02-05',TRUE),
(6,2,'Any%',5300000,'https://youtu.be/run37','2025-02-06',TRUE),
(6,3,'112%',9000000,'https://youtu.be/run38','2025-02-07',FALSE),
(6,4,'Any%',5500000,'https://youtu.be/run39','2025-02-08',TRUE),
(6,5,'Any%',5600000,'https://youtu.be/run40','2025-02-09',TRUE),
(6,6,'112%',9200000,'https://youtu.be/run41','2025-02-10',FALSE),
(6,7,'Any%',5250000,'https://youtu.be/run42','2025-02-11',TRUE),

-- Dark Souls
(7,2,'Any%',7200000,'https://youtu.be/run43','2025-02-12',TRUE),
(7,3,'Any%',7100000,'https://youtu.be/run44','2025-02-13',TRUE),
(7,4,'All Bosses',12000000,'https://youtu.be/run45','2025-02-14',FALSE),
(7,5,'Any%',7300000,'https://youtu.be/run46','2025-02-15',TRUE),
(7,6,'Any%',7050000,'https://youtu.be/run47','2025-02-16',TRUE),
(7,7,'All Bosses',11800000,'https://youtu.be/run48','2025-02-17',FALSE),
(7,8,'Any%',7000000,'https://youtu.be/run49','2025-02-18',TRUE),

-- Minecraft
(8,1,'Any%',1800000,'https://youtu.be/run50','2025-02-19',TRUE),
(8,2,'Any%',1750000,'https://youtu.be/run51','2025-02-20',TRUE),
(8,3,'Set Seed',900000,'https://youtu.be/run52','2025-02-21',TRUE),
(8,4,'Any%',1850000,'https://youtu.be/run53','2025-02-22',FALSE),
(8,5,'Any%',1780000,'https://youtu.be/run54','2025-02-23',TRUE),
(8,6,'Set Seed',880000,'https://youtu.be/run55','2025-02-24',TRUE),
(8,7,'Any%',1820000,'https://youtu.be/run56','2025-02-25',FALSE),

-- Super Metroid
(9,2,'Any%',2400000,'https://youtu.be/run57','2025-02-26',TRUE),
(9,3,'Any%',2350000,'https://youtu.be/run58','2025-02-27',TRUE),
(9,4,'100%',4200000,'https://youtu.be/run59','2025-02-28',FALSE),
(9,5,'Any%',2450000,'https://youtu.be/run60','2025-03-01',TRUE),
(9,6,'Any%',2300000,'https://youtu.be/run61','2025-03-02',TRUE),
(9,7,'100%',4300000,'https://youtu.be/run62','2025-03-03',FALSE),
(9,8,'Any%',2280000,'https://youtu.be/run63','2025-03-04',TRUE),

-- Cuphead
(10,1,'Any%',2100000,'https://youtu.be/run64','2025-03-05',TRUE),
(10,2,'Any%',2050000,'https://youtu.be/run65','2025-03-06',TRUE),
(10,3,'All Bosses',2600000,'https://youtu.be/run66','2025-03-07',FALSE),
(10,4,'Any%',2150000,'https://youtu.be/run67','2025-03-08',TRUE),
(10,5,'Any%',2080000,'https://youtu.be/run68','2025-03-09',TRUE),
(10,6,'All Bosses',2550000,'https://youtu.be/run69','2025-03-10',FALSE),
(10,7,'Any%',2020000,'https://youtu.be/run70','2025-03-11',TRUE);