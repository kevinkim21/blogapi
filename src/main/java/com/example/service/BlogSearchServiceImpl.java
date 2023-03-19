package com.example.service;

import com.example.common.ErrorCode;
import com.example.common.Provider;
import com.example.common.SortType;
import com.example.components.kakao.KakaoComponent;
import com.example.components.kakao.dto.KakaoBlogResultDto;
import com.example.components.kakao.dto.KakaoBlogSearchDto;
import com.example.components.naver.NaverComponent;
import com.example.components.naver.dto.NaverBlogResultDto;
import com.example.components.naver.dto.NaverBlogSearchDto;
import com.example.domain.SearchQuery;
import com.example.dto.response.BlogDto;
import com.example.dto.response.BlogResultDto;
import com.example.dto.response.PageDto;
import com.example.dto.response.TopKeyword;
import com.example.exception.BlogSearchException;
import com.example.repository.SearchQueryJpaRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogSearchServiceImpl implements BlogSearchService {

  private final SearchQueryJpaRepository searchQueryJpaRepository;
  private final KakaoComponent kakaoComponent;
  private final NaverComponent naverComponent;

  @Override
  public List<TopKeyword> findTop10ByOrderByCountDesc() {
    List<SearchQuery> searchQueries = searchQueryJpaRepository.findTop10ByOrderByCountDesc();
    return searchQueries.stream()
        .map(searchQuery -> TopKeyword.builder()
            .query(searchQuery.getQuery())
            .count(searchQuery.getCount())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public BlogResultDto searchBlog(Provider provider, String query, SortType sort, int p, int s) {
    validateQuery(query);
    int page = Math.min(Math.max(p, 1), 50);
    int size = Math.min(Math.max(s, 1), 50);
    BlogResultDto result = getBlogResult(provider, query, sort, page, size);
    updateQueryCount(query, result);
    return result;
  }

  @Transactional
  void updateQueryCount(String query, BlogResultDto result) {
    if (ObjectUtils.isNotEmpty(result.getItems())) {
      Optional<SearchQuery> searchQuery = searchQueryJpaRepository.findByQuery(query);
      if (searchQuery.isPresent()) {
        searchQuery.get().setCount(1);
        searchQueryJpaRepository.save(searchQuery.get());
      } else {
        SearchQuery newSearchQuery = SearchQuery.builder()
            .query(query)
            .count(1)
            .build();
        searchQueryJpaRepository.save(newSearchQuery);
      }
    }
  }

  private BlogResultDto getBlogResult(Provider provider, String query, SortType sort, int page,
      int size) {
    int total = 0;
    int pages = 0;
    List<BlogDto> blogDtos = new ArrayList<>();
    switch (provider) {
      case KAKAO -> {
        KakaoBlogResultDto kakaoBlogResultDto = getBlogFromKakao(query, sort, page, size);
        List<BlogDto> kakaoBlogs = getBlogDtosFromKakao(kakaoBlogResultDto);
        total = kakaoBlogResultDto.getMeta().getTotalCount();
        pages = kakaoBlogResultDto.getMeta().getPageableCount();
        blogDtos.addAll(kakaoBlogs);
      }
      case NAVER -> {
        NaverBlogResultDto naverBlogResultDto = getBlogFromNaver(query, sort, page, size);
        List<BlogDto> naverBlogs = getBlogDtosFromNaver(naverBlogResultDto);
        total = naverBlogResultDto.getTotal();
        pages = naverBlogResultDto.getStart();
        blogDtos.addAll(naverBlogs);
      }
    }
    if(ObjectUtils.isEmpty(blogDtos)){
      throw new BlogSearchException(ErrorCode.NOT_FOUND_ERROR);
    }
    return BlogResultDto.builder()
        .page(PageDto.builder()
            .total(total)
            .pages(pages)
            .build())
        .items(blogDtos)
        .build();
  }

  private static List<BlogDto> getBlogDtosFromNaver(NaverBlogResultDto naverBlogResultDto) {
    if (naverBlogResultDto.getItems() != null) {
      return naverBlogResultDto.getItems().stream().map(n -> {
        BlogDto blogDto = BlogDto.builder()
            .blogName(n.getBloggername())
            .title(n.getTitle())
            .url(n.getBloggerlink())
            .posted(n.getPostdate())
            .contents(n.getDescription())
            .build();
        return blogDto;
      }).collect(Collectors.toList());
    }
    return Collections.EMPTY_LIST;
  }

  private static List<BlogDto> getBlogDtosFromKakao(KakaoBlogResultDto kakaoBlogResultDto) {
    if (kakaoBlogResultDto.getDocuments() != null) {
      return kakaoBlogResultDto.getDocuments().stream().map(k -> {
        BlogDto blogDto = BlogDto.builder()
            .blogName(k.getBlogname())
            .title(k.getTitle())
            .url(k.getUrl())
            .contents(k.getContents())
            .posted(k.getDatetime())
            .build();
        return blogDto;
      }).collect(Collectors.toList());
    }
    return Collections.EMPTY_LIST;
  }

  private KakaoBlogResultDto getBlogFromKakao(String query, SortType sort, int page, int size) {
    String sortType = getKakaoSortType(sort);
    KakaoBlogSearchDto kakaoBlogSearchDto = KakaoBlogSearchDto.builder()
        .query(query)
        .sort(sortType)
        .page(page)
        .size(size)
        .build();
    return kakaoComponent.searchBlogs(kakaoBlogSearchDto);
  }

  private NaverBlogResultDto getBlogFromNaver(String query, SortType sort, int page, int size) {
    String sortType = getNaverSortType(sort);
    NaverBlogSearchDto naverBlogSearchDto = NaverBlogSearchDto.builder()
        .sort(sortType)
        .start(page)
        .display(size)
        .query(query)
        .build();
    return naverComponent.searchBlogs(naverBlogSearchDto);
  }

  private static String getKakaoSortType(SortType sortType) {
    String result = "";
    switch (sortType) {
      case RECENCY:
        result = "recency";
        break;
      case ACCURACY:
        result = "accuracy";
        break;
    }
    return result;
  }

  private static String getNaverSortType(SortType sortType) {
    String result = "";
    switch (sortType) {
      case RECENCY:
        result = "sim";
        break;
      case ACCURACY:
        result = "date";
        break;
    }
    return result;
  }

  private void validateQuery(String query) {
    if(StringUtils.isAllEmpty(query) || StringUtils.isAllBlank(query)) {
      throw new BlogSearchException(ErrorCode.BAD_REQUEST);
    }
  }

  @PostConstruct
  void dummyKeywords() {
    List<SearchQuery> searchQueries = List.of(
        SearchQuery.builder()
            .query("kakao")
            .count(1)
            .build(),
        SearchQuery.builder()
            .query("naver")
            .count(1)
            .build()
    );
    searchQueryJpaRepository.saveAll(searchQueries);
  }
}
