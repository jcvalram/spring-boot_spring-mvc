--
-- Table structure for table `Albums`
--
CREATE TABLE `Albums` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(200) NOT NULL,
  `lanzado` date NOT NULL,
  `precio` double NOT NULL,
  `genero` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;