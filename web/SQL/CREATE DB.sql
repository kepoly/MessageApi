
-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 18, 2016 at 05:19 AM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 7.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `messages`
--

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `contents` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `senttime` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`id`, `title`, `contents`, `author`, `senttime`) VALUES
(1, 'afucaaaakyeah', 'gggggg', 'gggggg', '2018-01-05'),
(28, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(29, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(30, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(31, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(32, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(33, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(34, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(35, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(36, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(37, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(38, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(39, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(40, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(41, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(42, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(43, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(44, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(45, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(46, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(47, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(48, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(49, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(50, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12'),
(51, 'ws', 'asdfasdasdfasdfadsfasdf', 'Matt', '2016-04-12');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
