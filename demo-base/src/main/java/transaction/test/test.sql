CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountName` varchar(50) DEFAULT NULL,
  `user` varchar(50) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;


CREATE TABLE `user2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;