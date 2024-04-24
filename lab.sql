-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2024-04-24 15:58:12
-- 服务器版本： 8.0.24
-- PHP 版本： 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `lab`
--

-- --------------------------------------------------------

--
-- 表的结构 `lab`
--

CREATE TABLE `lab` (
  `lab_id` varchar(8) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The unique primary key, denoting the actual location of the lab in the building.',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The name of the lab with maximum length of 32 characters.',
  `category` enum('软件','硬件','网络','') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The category of the lab, including ''软件'', ''硬件'' and ''网络''. ',
  `equipment_count` int UNSIGNED NOT NULL COMMENT 'The count of the equipment in the lab.',
  `technician_id` int UNSIGNED DEFAULT NULL COMMENT 'The id of the lab technician that supervises the lab. This lab technician is responsible for the repair of all the equipment in the lab.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='The info of a lab.';

-- --------------------------------------------------------

--
-- 表的结构 `lab_borrow_request`
--

CREATE TABLE `lab_borrow_request` (
  `request_id` int UNSIGNED NOT NULL COMMENT 'The unique primary key.',
  `semester_id` int UNSIGNED NOT NULL COMMENT 'The id of the requested semester.',
  `student_id` int UNSIGNED NOT NULL COMMENT 'The id of the student that sends the lab borrowing request.',
  `week` int UNSIGNED NOT NULL COMMENT 'The requested week for the lab.',
  `day` enum('星期一','星期二','星期三','星期四','星期五','星期六','星期日') COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The scheduled day of the week, including ''星期一'', ''星期二'', ''星期三'', ''星期四'', ''星期五'', ''星期六'' and ''星期日''.',
  `session` enum('1-2','3-5','6-7','8-9','10-12','13-15') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The requested session, including ''1-2'',''3-5'',''6-7'',''8-9'',''10-12'' and ''13-15''.',
  `lab_id` varchar(8) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The id of the requested lab.',
  `reason` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The reason for the lab borrowing request.',
  `date` date NOT NULL COMMENT 'The date of the lab borrowing request.',
  `status` enum('未审核','通过','驳回','使用完毕') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '未审核' COMMENT 'The status of the lab borrowing request, including ''未审核'', ''通过'', ''驳回'' and ''使用完毕''.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='The info of a lab borrowing request sent by a student.';

-- --------------------------------------------------------

--
-- 表的结构 `lab_schedule_request`
--

CREATE TABLE `lab_schedule_request` (
  `request_id` int UNSIGNED NOT NULL COMMENT 'The unique primary key.',
  `semester_id` int UNSIGNED NOT NULL COMMENT 'The id of the requested semester.',
  `teacher_id` int UNSIGNED NOT NULL COMMENT 'The id of the teacher that sends the request.',
  `course_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The name of the course with the maximum length of 32 characters.',
  `lab_category` enum('软件','硬件','网络','') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The category of the requested lab, including ''软件'',''硬件'' and ''网络''.',
  `student_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The class of the students attending the course with the maximum length of 32 characters.',
  `student_count` int UNSIGNED NOT NULL COMMENT 'The requested number of the students for the lab.',
  `start_week` int UNSIGNED NOT NULL COMMENT 'The requested start week.',
  `end_week` int UNSIGNED NOT NULL COMMENT 'The requested end week.',
  `day` enum('星期一','星期二','星期三','星期四','星期五','星期六','星期日') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The scheduled day of the week, including ''星期一'', ''星期二'', ''星期三'', ''星期四'', ''星期五'', ''星期六'' and ''星期日''.',
  `session` enum('1-2','3-5','6-7','8-9','10-12','13-15') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The requested session, including ''1-2'', ''3-5'', ''6-7'', ''8-9'', ''10-12'' and ''13-15''.',
  `lab_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'The id of the lab that is assigned to the course.',
  `status` enum('已排课','未排课') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '未排课' COMMENT 'The status of the lab schedule request, including ''已排课'' and ''未排课''.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='The info of a lab schedule request sent by a teacher.';

-- --------------------------------------------------------

--
-- 表的结构 `repair_request`
--

CREATE TABLE `repair_request` (
  `request_id` int UNSIGNED NOT NULL COMMENT 'The unique primary key.',
  `teacher_id` int UNSIGNED NOT NULL COMMENT 'The id of the teacher that sends the equipment repair request.',
  `lab_id` varchar(8) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The id of the lab that has the equipment to be repaired.',
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The description of the malfunction of the equipment  with the maximum length of 256 characters.',
  `date` date NOT NULL COMMENT 'The date of the equipment repair request.',
  `status` enum('已维修','未维修','维修中') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '未维修' COMMENT 'The status of the equipment repair request, including ''已维修'', ''未维修'' and ''维修中''.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='The info of a equipment repair request.';

-- --------------------------------------------------------

--
-- 表的结构 `schedule`
--

CREATE TABLE `schedule` (
  `schedule_id` int UNSIGNED NOT NULL COMMENT 'The unique primary key.',
  `request_id` int UNSIGNED NOT NULL COMMENT 'The id of the corresponding lab schedule request.',
  `semester_id` int UNSIGNED NOT NULL COMMENT 'The id of the scheduled semester.',
  `lab_id` varchar(8) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The id of the scheduled lab.',
  `start_week` int UNSIGNED NOT NULL COMMENT 'The scheduled start week.',
  `end_week` int UNSIGNED NOT NULL COMMENT 'The scheduled end week.',
  `day` enum('星期一','星期二','星期三','星期四','星期五','星期六','星期日') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The scheduled day of the week, including ''星期一'', ''星期二'', ''星期三'', ''星期四'', ''星期五'', ''星期六'' and ''星期日''.',
  `session` enum('1-2','3-5','6-7','8-9','10-12','13-15') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The scheduled session, including ''1-2'', ''3-5'', ''6-7'', ''8-9'', ''10-12'' and ''13-15''.',
  `course_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The name of the course  with the maximum length of 32 characters.',
  `teacher_id` int UNSIGNED NOT NULL COMMENT 'The id of the teacher that requests the lab.',
  `student_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The class of the students in the course with the maximum length of 32 characters.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='The info of a lab schedule that corresponds a lab schedule request.';

-- --------------------------------------------------------

--
-- 表的结构 `semester`
--

CREATE TABLE `semester` (
  `semester_id` int UNSIGNED NOT NULL COMMENT 'The unique primary key.',
  `semester` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The actual semester. \r\nAn semester is in the format like ''2023-2024-1'', with ''1'' denoting ''Autumn'' and ''2'' denoting ''Spring''.',
  `week_count` int UNSIGNED NOT NULL COMMENT 'The number of the weeks of a semester, usually 18-20.',
  `is_current_semester` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether a semester is the current semester.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='One of the two periods that the school or college year is divided into.';

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `user_id` int UNSIGNED NOT NULL COMMENT 'The unique primary key.',
  `role` enum('系统管理员','实验员','教师','学生') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The role of a user. User roles include ''系统管理员'', ''实验员'', ''教师'' and ''学生''.',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The username of a user. \r\nFor lab technicians and teachers, employee number is their username, and for students, student number is their username. As for system admins, their username does not have any particular meaning.\r\nThe valid length of a username is within the range [4, 20].',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'cslab@scau' COMMENT 'The password of a user.\r\nThe valid length of a password is within the range [8, 128].',
  `full_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'The full name of a user in real life with the maximum length of 128 characters.',
  `title` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'The job title of a teacher or a lab technician with the maximum length of 16 characters.',
  `major` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'The major of a student  with the maximum length of 32 characters.',
  `student_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'The class of a student  with the maximum length of 32 characters.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT=' A person in a particular role in the system.';

--
-- 转储表的索引
--

--
-- 表的索引 `lab`
--
ALTER TABLE `lab`
  ADD PRIMARY KEY (`lab_id`),
  ADD KEY `fk_lab_user` (`technician_id`) USING BTREE;

--
-- 表的索引 `lab_borrow_request`
--
ALTER TABLE `lab_borrow_request`
  ADD PRIMARY KEY (`request_id`),
  ADD KEY `fk_lab_borrow_request_semester` (`semester_id`),
  ADD KEY `fk_lab_borrow_request_user` (`student_id`),
  ADD KEY `fl_lab_borrow_requset_lab` (`lab_id`);

--
-- 表的索引 `lab_schedule_request`
--
ALTER TABLE `lab_schedule_request`
  ADD PRIMARY KEY (`request_id`),
  ADD KEY `fk_request_semester` (`semester_id`) USING BTREE,
  ADD KEY `fk_request_user` (`teacher_id`) USING BTREE;

--
-- 表的索引 `repair_request`
--
ALTER TABLE `repair_request`
  ADD PRIMARY KEY (`request_id`),
  ADD KEY `fk_repair_request_user` (`teacher_id`),
  ADD KEY `fk_repair_request_lab` (`lab_id`);

--
-- 表的索引 `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`schedule_id`),
  ADD KEY `fk_schedule_semester` (`semester_id`) USING BTREE,
  ADD KEY `fk_schedule_lab` (`lab_id`) USING BTREE,
  ADD KEY `fk_schedule_user` (`teacher_id`) USING BTREE,
  ADD KEY `fk_schedule_lab_request` (`request_id`);

--
-- 表的索引 `semester`
--
ALTER TABLE `semester`
  ADD PRIMARY KEY (`semester_id`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `lab_borrow_request`
--
ALTER TABLE `lab_borrow_request`
  MODIFY `request_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The unique primary key.';

--
-- 使用表AUTO_INCREMENT `lab_schedule_request`
--
ALTER TABLE `lab_schedule_request`
  MODIFY `request_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The unique primary key.';

--
-- 使用表AUTO_INCREMENT `repair_request`
--
ALTER TABLE `repair_request`
  MODIFY `request_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The unique primary key.';

--
-- 使用表AUTO_INCREMENT `schedule`
--
ALTER TABLE `schedule`
  MODIFY `schedule_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The unique primary key.';

--
-- 使用表AUTO_INCREMENT `semester`
--
ALTER TABLE `semester`
  MODIFY `semester_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The unique primary key.';

--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The unique primary key.';

--
-- 限制导出的表
--

--
-- 限制表 `lab`
--
ALTER TABLE `lab`
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`technician_id`) REFERENCES `user` (`user_id`);

--
-- 限制表 `lab_borrow_request`
--
ALTER TABLE `lab_borrow_request`
  ADD CONSTRAINT `fk_lab_borrow_request_semester` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`),
  ADD CONSTRAINT `fk_lab_borrow_request_user` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `fl_lab_borrow_requset_lab` FOREIGN KEY (`lab_id`) REFERENCES `lab` (`lab_id`);

--
-- 限制表 `lab_schedule_request`
--
ALTER TABLE `lab_schedule_request`
  ADD CONSTRAINT `fk_lab_schedule_request_semester` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_lab_schedule_request_user` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 限制表 `repair_request`
--
ALTER TABLE `repair_request`
  ADD CONSTRAINT `fk_repair_request_lab` FOREIGN KEY (`lab_id`) REFERENCES `lab` (`lab_id`),
  ADD CONSTRAINT `fk_repair_request_user` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`);

--
-- 限制表 `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `fk_schedule_lab` FOREIGN KEY (`lab_id`) REFERENCES `lab` (`lab_id`),
  ADD CONSTRAINT `fk_schedule_lab_request` FOREIGN KEY (`request_id`) REFERENCES `lab_schedule_request` (`request_id`),
  ADD CONSTRAINT `fk_schedule_semester` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_schedule_user` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
