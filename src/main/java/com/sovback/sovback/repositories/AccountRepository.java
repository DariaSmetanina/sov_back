package com.sovback.sovback.repositories;

import com.sovback.sovback.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllById(long l);

    List<Account> findAllByOrganizationInOrderByDateDesc(List<Long> list2);


//    @Query("select acc from account acc where acc.id_org in :ids")
//    List<Account> findByidOrganization(@Param("ids") Iterable<Long> ids);
}
