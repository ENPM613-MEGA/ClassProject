-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 25, 2018 at 08:33 PM
-- Server version: 5.7.21
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `pols`
--

-- --------------------------------------------------------

--
-- Table structure for table `Assignments`
--

CREATE TABLE `Assignments` (
  `id` int(11) NOT NULL,
  `c_id` int(11) NOT NULL,
  `ass_name` varchar(255) NOT NULL,
  `path` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `due_date` date NOT NULL,
  `publish` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Classes`
--

CREATE TABLE `Classes` (
  `id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `class_name` varchar(45) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `decription` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Classes`
--

INSERT INTO `Classes` (`id`, `instructor_id`, `class_name`, `start_date`, `end_date`, `decription`) VALUES
(1, 1, 'software engineering', '2017-02-04', '2018-02-03', 'Created by Frank');

-- --------------------------------------------------------

--
-- Table structure for table `Documents`
--

CREATE TABLE `Documents` (
  `id` int(11) NOT NULL,
  `c_id` int(11) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `type` varchar(10) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `publish` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Documents`
--

INSERT INTO `Documents` (`id`, `c_id`, `filename`, `type`, `path`, `create_date`, `publish`) VALUES
(9, 1, 'Musics', 'video', 'http://wj98127.iteye.com/blog/360644', '2018-11-20', 1),
(11, 1, 'panda.pdf', 'syllabus', '/Users/frankyu/WorkSpace/Projects/POLS/ClassProject/BackEnd/target/POLS/WEB-INF/upload/1/panda.pdf', '2018-11-21', 1),
(13, 1, 'video', 'video', 'https://docs.google.com/document/d/12Q-htIRhtxGYtRnjLwVoP5UyWq1T9y4vzfd6yv_zPZg/edit#', '2018-11-21', 0),
(14, 1, 'Haonan Yu Resume.pdf', 'syllabus', '/Users/frankyu/WorkSpace/Projects/POLS/ClassProject/BackEnd/target/POLS/WEB-INF/upload/1/Haonan Yu Resume.pdf', '2018-11-24', 0),
(15, 1, 'software quality.pdf', 'file', '/Users/frankyu/WorkSpace/Projects/POLS/ClassProject/BackEnd/target/POLS/WEB-INF/upload/1/software quality.pdf', '2018-11-24', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Grades`
--

CREATE TABLE `Grades` (
  `u_id` int(11) NOT NULL,
  `a_id` int(11) NOT NULL,
  `grade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `gender` varchar(6) DEFAULT 'male',
  `birth` date DEFAULT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'student',
  `addr` varchar(45) DEFAULT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `color_blind` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`id`, `username`, `password`, `email`, `gender`, `birth`, `role`, `addr`, `points`, `color_blind`) VALUES
(1, 'frank', '1111', 'frank.yu@gmail.com', 'male', NULL, 'instructor', '5304 Smiths Cove Ln', 100, 1),
(2, 'aaa', '122', NULL, 'male', NULL, 'student', NULL, 10, 0),
(12, 'Haonan Yu', '940205', NULL, 'male', NULL, 'student', NULL, 0, 0),
(13, 'Haonan', '940205', NULL, 'male', NULL, 'instuctor', NULL, 0, 0),
(14, 'Hao', '940205', NULL, 'male', NULL, 'instuctor', NULL, 0, 0),
(15, 'MQ', '940205', NULL, 'male', NULL, 'instuctor', NULL, 100, 0);

-- --------------------------------------------------------

--
-- Table structure for table `User_Class`
--

CREATE TABLE `User_Class` (
  `u_id` int(11) NOT NULL,
  `c_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `User_Class`
--

INSERT INTO `User_Class` (`u_id`, `c_id`) VALUES
(1, 1),
(2, 1),
(12, 1),
(13, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Assignments`
--
ALTER TABLE `Assignments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_assignment_class` (`c_id`);

--
-- Indexes for table `Classes`
--
ALTER TABLE `Classes`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `Documents`
--
ALTER TABLE `Documents`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `fileAndClass_unique` (`c_id`,`filename`);

--
-- Indexes for table `Grades`
--
ALTER TABLE `Grades`
  ADD PRIMARY KEY (`u_id`,`a_id`),
  ADD KEY `fk_grade_assignment` (`a_id`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `User_Class`
--
ALTER TABLE `User_Class`
  ADD PRIMARY KEY (`u_id`,`c_id`),
  ADD KEY `fk_class` (`c_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Assignments`
--
ALTER TABLE `Assignments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Classes`
--
ALTER TABLE `Classes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Documents`
--
ALTER TABLE `Documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Assignments`
--
ALTER TABLE `Assignments`
  ADD CONSTRAINT `fk_assignment_class` FOREIGN KEY (`c_id`) REFERENCES `Classes` (`id`);

--
-- Constraints for table `Documents`
--
ALTER TABLE `Documents`
  ADD CONSTRAINT `fk_document_class` FOREIGN KEY (`c_id`) REFERENCES `Classes` (`id`);

--
-- Constraints for table `Grades`
--
ALTER TABLE `Grades`
  ADD CONSTRAINT `fk_grade_assignment` FOREIGN KEY (`a_id`) REFERENCES `Assignments` (`id`),
  ADD CONSTRAINT `fk_grade_user` FOREIGN KEY (`u_id`) REFERENCES `Users` (`id`);

--
-- Constraints for table `User_Class`
--
ALTER TABLE `User_Class`
  ADD CONSTRAINT `fk_class` FOREIGN KEY (`c_id`) REFERENCES `Classes` (`id`),
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`u_id`) REFERENCES `Users` (`id`);
 6

