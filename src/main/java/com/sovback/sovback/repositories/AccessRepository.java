package com.sovback.sovback.repositories;

import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
    List<Access> findAllByUser(long l);
}
