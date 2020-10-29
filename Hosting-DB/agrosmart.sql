-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 29, 2020 at 01:08 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `agrosmart`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `User_Id` int(6) NOT NULL,
  `Name` varchar(60) NOT NULL,
  `PhoneNumber` varchar(15) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`User_Id`, `Name`, `PhoneNumber`, `Email`, `Password`) VALUES
(1, 'Yazmin Martinez', '4494767293', 'reynosoyorch@gmail.com', 'yazMAR123$'),
(2, 'Jorge Luis Reynoso', '4492233232', 'jorgereynosovelasco@gmail.com', 'jorgeREY123$'),
(3, 'Alberto Vargas', '4492233232', 'alberto@gmail.com', '$2y$10$RhZcGutAAk8aNoA1sVzP.eU'),
(4, 'aaa', '444', 'aaa', '111'),
(5, 'Agro Smart', '4491234567', 'agrosmart@gmail.com', 'agro123'),
(6, 'Agro Smart', '4491234567', 'agrosmart@gmail.com', 'agro123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`User_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `User_Id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
