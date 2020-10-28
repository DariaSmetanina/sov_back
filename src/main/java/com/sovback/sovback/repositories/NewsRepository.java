package com.sovback.sovback.repositories;

import com.sovback.sovback.model.News;
import org.apache.catalina.connector.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News findOneById(Long ID);
}
