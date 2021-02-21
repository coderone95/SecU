package com.coderone95.secu.repository;

import com.coderone95.secu.entity.SecBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SecBankDetailsRepository extends JpaRepository<SecBankDetails,Long> {

    @Query(value = "select s from SecBankDetails s Where s.secName = ?1")
    SecBankDetails getBKDetailsBySecName(String secName);
}
