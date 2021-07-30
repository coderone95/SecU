alter table tbl_user add column secret_key varchar(255);

INSERT IGNORE INTO mst_secu_types(`id`,`type_code`) VALUES(1,'BANK_ACCOUNT_DETAILS');
INSERT IGNORE INTO mst_secu_types(`id`,`type_code`) VALUES(2,'CREDENTIALS');