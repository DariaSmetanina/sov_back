package com.sovback.sovback.repositories;

import com.sovback.sovback.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
        List<Notification> findAllByOrganizationInOrderByDateDesc(List<Long> list2);
        List<Notification> findFirst2ByOrganizationOrderByDateDesc(Long idOrg);
}
