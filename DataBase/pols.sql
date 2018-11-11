-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 11, 2018 at 09:24 PM
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
  `idAssignment` int(11) NOT NULL,
  `Assignment name` varchar(45) NOT NULL,
  `Upload date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Classes`
--

CREATE TABLE `Classes` (
  `idClass` int(11) NOT NULL,
  `Class name` varchar(45) NOT NULL,
  `Start date` date NOT NULL,
  `End date` date NOT NULL,
  `Syllabus_idSyllabus` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Class_Video`
--

CREATE TABLE `Class_Video` (
  `Classes_idClass` int(11) NOT NULL,
  `Videos_idVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Files`
--

CREATE TABLE `Files` (
  `idFile` int(11) NOT NULL,
  `File name` varchar(45) NOT NULL,
  `Upload date` datetime NOT NULL,
  `Classes_idClass` int(11) NOT NULL,
  `Classes_Syllabus_idSyllabus` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Grades`
--

CREATE TABLE `Grades` (
  `idUser` int(11) NOT NULL,
  `idAssignment` int(11) NOT NULL,
  `Grade` int(11) DEFAULT NULL,
  `Assignments_idAssignment` int(11) NOT NULL,
  `Users_idUser` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Syllabus`
--

CREATE TABLE `Syllabus` (
  `idSyllabus` int(11) NOT NULL,
  `Syllabus name` varchar(45) NOT NULL,
  `Upload date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `gender` varchar(6) DEFAULT 'male',
  `birth` date DEFAULT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'student',
  `addr` varchar(45) DEFAULT NULL,
  `points` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`id`, `username`, `password`, `email`, `gender`, `birth`, `role`, `addr`, `points`) VALUES
(1, 'frank', '940205', 'frank.yuhaonan@gmail.com', 'male', '1994-02-05', 'student', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `User_Class`
--

CREATE TABLE `User_Class` (
  `Users_idUser` int(11) NOT NULL,
  `Classes_idClass` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Videos`
--

CREATE TABLE `Videos` (
  `idVideo` int(11) NOT NULL,
  `Video name` varchar(45) NOT NULL,
  `Upload date` datetime NOT NULL,
  `Youtube url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Assignments`
--
ALTER TABLE `Assignments`
  ADD PRIMARY KEY (`idAssignment`);

--
-- Indexes for table `Classes`
--
ALTER TABLE `Classes`
  ADD PRIMARY KEY (`idClass`,`Syllabus_idSyllabus`),
  ADD KEY `fk_Classes_Syllabus1` (`Syllabus_idSyllabus`);

--
-- Indexes for table `Class_Video`
--
ALTER TABLE `Class_Video`
  ADD PRIMARY KEY (`Classes_idClass`,`Videos_idVideo`),
  ADD KEY `fk_Classes_has_Videos_Videos1` (`Videos_idVideo`);

--
-- Indexes for table `Files`
--
ALTER TABLE `Files`
  ADD PRIMARY KEY (`idFile`,`Classes_idClass`,`Classes_Syllabus_idSyllabus`),
  ADD KEY `fk_Files_Classes1` (`Classes_idClass`,`Classes_Syllabus_idSyllabus`);

--
-- Indexes for table `Grades`
--
ALTER TABLE `Grades`
  ADD PRIMARY KEY (`idUser`,`idAssignment`,`Assignments_idAssignment`,`Users_idUser`),
  ADD KEY `fk_Grades_Assignments1` (`Assignments_idAssignment`),
  ADD KEY `fk_Grades_Users1` (`Users_idUser`);

--
-- Indexes for table `Syllabus`
--
ALTER TABLE `Syllabus`
  ADD PRIMARY KEY (`idSyllabus`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `User_Class`
--
ALTER TABLE `User_Class`
  ADD PRIMARY KEY (`Users_idUser`,`Classes_idClass`),
  ADD KEY `fk_Users_has_Classes_Classes1` (`Classes_idClass`);

--
-- Indexes for table `Videos`
--
ALTER TABLE `Videos`
  ADD PRIMARY KEY (`idVideo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Classes`
--
ALTER TABLE `Classes`
  ADD CONSTRAINT `fk_Classes_Syllabus1` FOREIGN KEY (`Syllabus_idSyllabus`) REFERENCES `Syllabus` (`idSyllabus`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Class_Video`
--
ALTER TABLE `Class_Video`
  ADD CONSTRAINT `fk_Classes_has_Videos_Classes1` FOREIGN KEY (`Classes_idClass`) REFERENCES `Classes` (`idClass`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Classes_has_Videos_Videos1` FOREIGN KEY (`Videos_idVideo`) REFERENCES `Videos` (`idVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Files`
--
ALTER TABLE `Files`
  ADD CONSTRAINT `fk_Files_Classes1` FOREIGN KEY (`Classes_idClass`,`Classes_Syllabus_idSyllabus`) REFERENCES `Classes` (`idClass`, `Syllabus_idSyllabus`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Grades`
--
ALTER TABLE `Grades`
  ADD CONSTRAINT `fk_Grades_Assignments1` FOREIGN KEY (`Assignments_idAssignment`) REFERENCES `Assignments` (`idAssignment`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Grades_Users1` FOREIGN KEY (`Users_idUser`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `User_Class`
--
ALTER TABLE `User_Class`
  ADD CONSTRAINT `fk_Users_has_Classes_Classes1` FOREIGN KEY (`Classes_idClass`) REFERENCES `Classes` (`idClass`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Users_has_Classes_Users` FOREIGN KEY (`Users_idUser`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

