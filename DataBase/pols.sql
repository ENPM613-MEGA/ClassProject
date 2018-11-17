-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 17, 2018 at 11:10 PM
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
  `path` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL
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
  `decription` varchar(512) DEFAULT NULL,
  `syllabus` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Classes`
--

INSERT INTO `Classes` (`id`, `instructor_id`, `class_name`, `start_date`, `end_date`, `decription`, `syllabus`) VALUES
(1, 0, 'software engineering', '2017-02-04', '2018-02-03', 'Created by Frank', 'This is the syllabus');

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
  `create_person` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Documents`
--

INSERT INTO `Documents` (`id`, `c_id`, `filename`, `type`, `path`, `create_date`, `create_person`) VALUES
(1, 1, 'Background Interview CheatSheet.docx', 'syllabus', '/Users/frankyu/WorkSpace/Projects/POLS/ClassProject/BackEnd/target/POLS/WEB-INF/upload/Background Interview CheatSheet.docx', '2018-11-17', 'Frank Yu');

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
(1, 'frank', '1111', 'frank.yu@gmail.com', 'male', NULL, 'student', '5304 Smiths Cove Ln', 100, 1),
(2, 'aaa', '122', NULL, 'male', NULL, 'student', NULL, 10, 0),
(12, 'Haonan Yu', '940205', NULL, 'male', NULL, 'student', NULL, 0, 0),
(13, 'Haonan', '940205', NULL, 'male', NULL, 'instuctor', NULL, 0, 0),
(14, 'Hao', '940205', NULL, 'male', NULL, 'instuctor', NULL, 0, 0);

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
(1, 1);

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
  ADD KEY `fk_document_class` (`c_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

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

