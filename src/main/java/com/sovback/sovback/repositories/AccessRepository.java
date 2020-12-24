package com.sovback.sovback.repositories;

import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.Account;
import com.sovback.sovback.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
    List<Access> findAllByUser(long l);
    Optional<Access> findByOrganizationAndAndUser(long org, long us);

    @Query(value = "select DISTINCT user from access where access.organization in :ids" , nativeQuery = true)
    List<Long> getUsrIds(@Param("ids") List<Long> ids);


}


