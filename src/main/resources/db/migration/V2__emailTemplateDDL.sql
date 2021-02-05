CREATE TABLE IF NOT EXISTS  `tbl_email_template_config` (
  `template_id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255),
  `template_for` varchar(255),
  `template` longtext
);
