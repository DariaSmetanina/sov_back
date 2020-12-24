package com.sovback.sovback.repositories;

import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneById(long i);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query(value = "select email from user where user.id_user in :ids and user.role='client'", nativeQuery = true)
    List<String> getUsrs(@Param("ids") List<Long> ids);

}
