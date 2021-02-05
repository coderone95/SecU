CREATE TABLE `tbl_user` (
  `user_id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255),
  `email_id` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT null,
  `password` varchar(255),
  `profile_pic_key_data` longtext
);

CREATE TABLE `mst_secu_types` (
  `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `type_code` varchar(255),
  `description` varchar(255)
);

CREATE TABLE `tbl_sec_cred` (
  `sec_cred_id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `sec_name` varchar(255),
  `url` varchar(255),
  `username` varchar(255),
  `password` varchar(255),
  `other_data` longtext
);

CREATE TABLE `tbl_sec_bank_details` (
  `sec_bk_id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `sec_name` varchar(255),
  `ifsc_code` varchar(255),
  `account_number` varchar(255),
  `branch_name` varchar(255),
  `icmr_code` varchar(255),
  `banking_url` varchar(255),
  `net_banking_username` varchar(255),
  `net_banking_password` varchar(255),
  `other_data` longtext
);

CREATE TABLE `tbl_sec_gen_info` (
  `sec_gen_id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `data` longtext
);

ALTER TABLE `tbl_sec_cred` ADD FOREIGN KEY (`sec_cred_id`) REFERENCES `mst_secu_types` (`id`);

ALTER TABLE `tbl_sec_cred` ADD FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`);

ALTER TABLE `tbl_sec_bank_details` ADD FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`);

ALTER TABLE `tbl_sec_gen_info` ADD FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`);
