package com.sovback.sovback.repositories;

import com.sovback.sovback.model.Account;
import com.sovback.sovback.model.News;
import com.sovback.sovback.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
//    @Query("select org from organization org where org.id_organization in :ids")
//    List<Organization> findById(@Param("ids") Iterable<Long> ids);


    Organization findOneById(long id);
    Organization findOneByInn(String inn);

    @Query(value = "select id_organization from organization where organization.inn in :inns", nativeQuery = true)
    List<Long> getOrgIds(@Param("inns") List<String> inns);

}
