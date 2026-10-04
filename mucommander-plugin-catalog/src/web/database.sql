-- phpMyAdmin SQL Dump
-- version 5.2.3deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 04, 2026 at 12:23 PM
-- Server version: 11.8.6-MariaDB-5ubuntu0.1 from Ubuntu
-- PHP Version: 8.5.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mucommander`
--

-- --------------------------------------------------------

--
-- Table structure for table `addon`
--

CREATE TABLE `addon` (
  `id` int(11) NOT NULL,
  `module_id` varchar(120) NOT NULL,
  `name` varchar(80) NOT NULL,
  `description` varchar(120) DEFAULT NULL,
  `homepage` varchar(80) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp(),
  `owner_id` int(11) NOT NULL,
  `icon` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `addon_dep`
--

CREATE TABLE `addon_dep` (
  `id` int(11) NOT NULL,
  `addon_rel_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `value` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `addon_rel`
--

CREATE TABLE `addon_rel` (
  `id` int(11) NOT NULL,
  `addon_id` int(11) NOT NULL,
  `sequence` int(11) NOT NULL,
  `created` datetime NOT NULL DEFAULT current_timestamp(),
  `file` varchar(80) NOT NULL,
  `license_id` int(11) DEFAULT NULL,
  `version` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `license`
--

CREATE TABLE `license` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `spdx` varchar(64) DEFAULT NULL,
  `file` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `license`
--

INSERT INTO `license` (`id`, `name`, `spdx`, `file`) VALUES
(1, 'Apache License, Version 2.0', 'Apache-2.0', 'Apache-2.0.html'),
(2, 'GNU General Public License v1.0 only', 'GPL-1.0-only', 'GPL-1.0-only.html'),
(3, 'GNU General Public License v1.0 or later', 'GPL-1.0-or-later', 'GPL-1.0-or-later.html'),
(4, 'GNU General Public License v2.0 only', 'GPL-2.0-only', 'GPL-2.0-only.html'),
(5, 'GNU General Public License v2.0 or later', 'GPL-2.0-or-later', 'GPL-2.0-or-later.html'),
(6, 'GNU General Public License v3.0 only', 'GPL-3.0-only', 'GPL-3.0-only.html'),
(7, 'GNU General Public License v3.0 or later', 'GPL-3.0-or-later', 'GPL-3.0-or-later.html'),
(8, 'GNU Lesser General Public License v2.1 only', 'LGPL-2.1-only', 'LGPL-2.1-only.html');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`) VALUES
(1, 'muCommander');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addon`
--
ALTER TABLE `addon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_addon_owner` (`owner_id`);

--
-- Indexes for table `addon_dep`
--
ALTER TABLE `addon_dep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_addon_rel_dep_addon` (`addon_rel_id`);

--
-- Indexes for table `addon_rel`
--
ALTER TABLE `addon_rel`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ui_addon_rel_sequence` (`addon_id`,`sequence`),
  ADD KEY `fk_addon_rel_addon` (`addon_id`),
  ADD KEY `fk_addon_rel_license` (`license_id`);

--
-- Indexes for table `license`
--
ALTER TABLE `license`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `license`
--
ALTER TABLE `license`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `addon`
--
ALTER TABLE `addon`
  ADD CONSTRAINT `fk_addon_owner` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `addon_dep`
--
ALTER TABLE `addon_dep`
  ADD CONSTRAINT `fk_addon_rel_dep_addon` FOREIGN KEY (`addon_rel_id`) REFERENCES `addon_rel` (`id`);

--
-- Constraints for table `addon_rel`
--
ALTER TABLE `addon_rel`
  ADD CONSTRAINT `fk_addon_rel_addon` FOREIGN KEY (`addon_id`) REFERENCES `addon` (`id`),
  ADD CONSTRAINT `fk_addon_rel_license` FOREIGN KEY (`license_id`) REFERENCES `license` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
