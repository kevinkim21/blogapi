package com.example.repository;

import com.example.domain.SearchQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchQueryJpaRepository extends JpaRepository<SearchQuery, Long> {
  Optional<SearchQuery> findByQuery(String query);
  List<SearchQuery> findTop10ByOrderByCountDesc();
}
