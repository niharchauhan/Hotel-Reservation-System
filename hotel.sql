-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 07, 2023 at 04:45 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `roomID` int(10) NOT NULL,
  `room_Type` varchar(15) NOT NULL,
  `room_capacity` varchar(15) NOT NULL,
  `Check_In_Date` date NOT NULL,
  `Check_Out_Date` date NOT NULL,
  `isEmpty` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`roomID`, `room_Type`, `room_capacity`, `Check_In_Date`, `Check_Out_Date`, `isEmpty`) VALUES
(1, 'Economy', 'Single', '2023-12-04', '2023-12-06', 1),
(2, 'Economy', 'Single', '2019-08-13', '2023-10-29', 1),
(3, 'Economy', 'Single', '2020-06-08', '2023-10-29', 1),
(4, 'Economy', 'Single', '2023-10-31', '2023-11-01', 1),
(5, 'Economy', 'Single', '2023-10-31', '2023-11-02', 1),
(6, 'Economy', 'Single', '2023-10-31', '2023-10-29', 1),
(7, 'Economy', 'Single', '2019-04-14', '2019-04-14', 1),
(8, 'Economy', 'Single', '2019-04-14', '2019-04-19', 1),
(9, 'Economy', 'Single', '2019-04-14', '2019-04-22', 1),
(10, 'Economy', 'Single', '2019-04-14', '2019-04-22', 1),
(11, 'Economy', 'Double', '2023-12-01', '2023-12-02', 1),
(12, 'Economy', 'Double', '2019-04-14', '2019-04-18', 1),
(13, 'Economy', 'Double', '2019-04-14', '2019-04-14', 1),
(14, 'Economy', 'Double', '2019-04-14', '2019-04-15', 1),
(15, 'Economy', 'Double', '2019-04-14', '2019-04-14', 1),
(16, 'Economy', 'Double', '2019-04-14', '2019-04-16', 1),
(17, 'Economy', 'Double', '2019-04-14', '2019-04-14', 1),
(18, 'Economy', 'Double', '2019-04-14', '2019-04-18', 1),
(19, 'Economy', 'Double', '2019-04-14', '2019-04-15', 1),
(20, 'Economy', 'Double', '2019-04-14', '2019-04-18', 1),
(21, 'Economy', 'Triple', '2023-12-06', '2023-12-10', 0),
(22, 'Economy', 'Triple', '2019-04-16', '2019-04-21', 1),
(23, 'Economy', 'Triple', '2019-04-18', '2019-04-18', 1),
(24, 'Economy', 'Triple', '2019-04-15', '2019-04-15', 1),
(25, 'Economy', 'Triple', '2019-04-14', '2019-04-15', 1),
(26, 'Economy', 'Triple', '2019-04-14', '2019-04-15', 1),
(27, 'Economy', 'Triple', '2019-04-14', '2019-04-16', 1),
(28, 'Economy', 'Triple', '2019-04-14', '2019-04-14', 1),
(29, 'Economy', 'Triple', '2019-04-14', '2019-04-14', 1),
(30, 'Economy', 'Triple', '2019-04-14', '2019-04-14', 1),
(31, 'Normal', 'Single', '2023-12-04', '2023-12-13', 0),
(32, 'Normal', 'Single', '2023-10-16', '2023-10-29', 1),
(33, 'Normal', 'Single', '2019-04-14', '2019-04-14', 1),
(34, 'Normal', 'Single', '2019-04-14', '2019-04-14', 1),
(35, 'Normal', 'Single', '2019-04-14', '2019-04-14', 1),
(36, 'Normal', 'Single', '2019-04-14', '2019-04-14', 1),
(37, 'Normal', 'Single', '2019-04-14', '2019-04-14', 1),
(38, 'Normal', 'Single', '2019-04-14', '2019-04-14', 1),
(39, 'Normal', 'Single', '2019-04-14', '2019-04-14', 1),
(40, 'Normal', 'Single', '2019-04-14', '2019-04-14', 1),
(41, 'Normal', 'Double', '2023-12-05', '2023-12-11', 0),
(42, 'Normal', 'Double', '2019-04-23', '2019-04-22', 1),
(43, 'Normal', 'Double', '2019-04-14', '2019-04-14', 1),
(44, 'Normal', 'Double', '2019-04-18', '2019-04-21', 1),
(45, 'Normal', 'Double', '2019-04-14', '2019-04-21', 1),
(46, 'Normal', 'Double', '2019-04-14', '2019-04-14', 1),
(47, 'Normal', 'Double', '2019-04-14', '2019-04-14', 1),
(48, 'Normal', 'Double', '2019-04-14', '2019-04-16', 1),
(49, 'Normal', 'Double', '2019-04-14', '2019-04-14', 1),
(50, 'Normal', 'Double', '2019-04-14', '2019-04-15', 1),
(51, 'Normal', 'Triple', '2023-10-31', '2023-10-29', 1),
(52, 'Normal', 'Triple', '2019-04-30', '2019-04-30', 1),
(53, 'Normal', 'Triple', '2019-04-17', '2019-04-30', 1),
(54, 'Normal', 'Triple', '2019-04-14', '2019-05-04', 1),
(55, 'Normal', 'Triple', '2019-04-14', '2019-04-18', 1),
(56, 'Normal', 'Triple', '2019-04-14', '2019-04-14', 1),
(57, 'Normal', 'Triple', '2019-04-14', '2019-04-16', 1),
(58, 'Normal', 'Triple', '2019-04-14', '2019-04-16', 1),
(59, 'Normal', 'Triple', '2019-04-14', '2019-04-16', 1),
(60, 'Normal', 'Triple', '2019-04-14', '2019-04-14', 1),
(61, 'Vip', 'Single', '2023-12-04', '2023-12-02', 1),
(62, 'Vip', 'Single', '2019-04-14', '2019-04-14', 1),
(63, 'Vip', 'Single', '2019-04-14', '2019-04-14', 1),
(64, 'Vip', 'Single', '2019-04-14', '2019-04-14', 1),
(65, 'Vip', 'Single', '2019-04-14', '2019-04-18', 1),
(66, 'Vip', 'Single', '2019-04-15', '2019-04-16', 1),
(67, 'Vip', 'Single', '2019-04-14', '2019-04-14', 1),
(68, 'Vip', 'Single', '2019-04-14', '2019-04-14', 1),
(69, 'Vip', 'Single', '2019-04-14', '2019-04-14', 1),
(70, 'Vip', 'Single', '2019-04-14', '2019-04-14', 1),
(71, 'Vip', 'Double', '2019-04-24', '2023-10-29', 1),
(72, 'Vip', 'Double', '2019-05-04', '2023-10-28', 1),
(73, 'Vip', 'Double', '2019-04-14', '2019-04-14', 1),
(74, 'Vip', 'Double', '2019-04-14', '2019-04-14', 1),
(75, 'Vip', 'Double', '2019-04-14', '2019-04-14', 1),
(76, 'Vip', 'Double', '2019-04-14', '2019-04-22', 1),
(77, 'Vip', 'Double', '2019-04-01', '2019-04-21', 1),
(78, 'Vip', 'Double', '2019-04-14', '2019-04-14', 1),
(79, 'Vip', 'Double', '2019-04-14', '2019-04-14', 1),
(80, 'Vip', 'Double', '2019-04-14', '2019-04-14', 1),
(81, 'Vip', 'Triple', '2023-12-04', '2023-12-04', 1),
(82, 'Vip', 'Triple', '2019-04-14', '2019-04-14', 1),
(83, 'Vip', 'Triple', '2019-04-14', '2019-04-14', 1),
(84, 'Vip', 'Triple', '2019-04-14', '2019-04-14', 1),
(85, 'Vip', 'Triple', '2019-04-19', '2019-04-21', 1),
(86, 'Vip', 'Triple', '2019-04-18', '2019-04-19', 1),
(87, 'Vip', 'Triple', '2019-04-16', '2019-04-19', 1),
(88, 'Vip', 'Triple', '2019-04-14', '2019-04-19', 1),
(89, 'Vip', 'Triple', '2019-04-14', '2019-04-19', 1),
(90, 'Vip', 'Triple', '2019-04-14', '2019-04-15', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`roomID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `roomID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
