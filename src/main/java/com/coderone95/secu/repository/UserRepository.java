package com.coderone95.secu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coderone95.secu.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    @Query(nativeQuery = true, value = "SELECT * from tbl_user u where u.email_id=:emailId")
    User findByEmailId(@Param("emailId") String emailId);

    @Query(nativeQuery = true, value = "SELECT * from tbl_user u where u.email_id=:emailId and password=:password")
    User findByEmailIdAndPassword(@Param("emailId") String emailId,
                                            @Param("password") String password);
}
