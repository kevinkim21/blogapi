package com.example.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.domain.SearchQuery;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SearchQueryJpaRepositoryTest {

  @Autowired
  private SearchQueryJpaRepository searchQueryJpaRepository;

  @Test
  public void testSaveAndFindAll() {
    //객체 생성 및 저장
    SearchQuery searchQuery = SearchQuery.builder()
        .query("example query")
        .count(0)
        .build();
    searchQueryJpaRepository.save(searchQuery);

    // 조회
    List<SearchQuery> searchQueries = searchQueryJpaRepository.findAll();

    //검증
    assertThat(searchQueries).isNotEmpty();
    assertThat(searchQueries.get(0).getQuery()).isEqualTo("example query");
    assertThat(searchQueries.get(0).getCount()).isEqualTo(0);
  }

  @Test
  public void testUpdateQueryCount() {
    // 검색어 생성 및 저장
    SearchQuery searchQuery = SearchQuery.builder()
        .query("example query")
        .count(0)
        .build();
    searchQueryJpaRepository.save(searchQuery);

    // 조회
    Optional<SearchQuery> optionalSearchQuery = searchQueryJpaRepository.findByQuery("example query");

    // 검증
    assertThat(optionalSearchQuery).isPresent();
    SearchQuery retrievedSearchQuery = optionalSearchQuery.get();
    assertThat(retrievedSearchQuery.getCount()).isEqualTo(0);

    // 업데이트
    retrievedSearchQuery.setCount(retrievedSearchQuery.getCount() + 1);
    searchQueryJpaRepository.save(retrievedSearchQuery);

    //조회
    Optional<SearchQuery> optionalUpdatedSearchQuery = searchQueryJpaRepository.findByQuery("example query");

    // 검증
    assertThat(optionalUpdatedSearchQuery).isPresent();
    SearchQuery updatedSearchQuery = optionalUpdatedSearchQuery.get();
    assertThat(updatedSearchQuery.getCount()).isEqualTo(1);
  }

  @Test
  public void testFindTop10ByOrderByCountDesc() {
    // 15개 샘플 데이터 생성
    for (int i = 1; i <= 15; i++) {
      SearchQuery searchQuery = SearchQuery.builder()
          .query("example query " + i)
          .count(i)
          .build();
      searchQueryJpaRepository.save(searchQuery);
    }

    // top10 조회
    List<SearchQuery> top10SearchQueries = searchQueryJpaRepository.findTop10ByOrderByCountDesc();

    // 검증
    assertThat(top10SearchQueries).isNotEmpty();
    assertThat(top10SearchQueries.size()).isEqualTo(10);
    assertThat(top10SearchQueries.get(0).getQuery()).isEqualTo("example query 15");
    assertThat(top10SearchQueries.get(0).getCount()).isEqualTo(15);
  }
}