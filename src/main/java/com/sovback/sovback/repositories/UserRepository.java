package com.sovback.sovback.repositories;

import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneById(long i);
}
