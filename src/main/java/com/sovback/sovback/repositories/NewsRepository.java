package com.sovback.sovback.repositories;

import com.sovback.sovback.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News findOneById(Long ID);
    List<News> findFirst3ByOrderByDateDesc();
    List<News> findFirst30ByOrderByDateDesc();
}
