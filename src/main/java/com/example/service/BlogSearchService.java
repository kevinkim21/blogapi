package com.example.service;

import com.example.common.Provider;
import com.example.common.SortType;
import com.example.dto.response.BlogResultDto;
import com.example.dto.response.TopKeyword;
import java.util.List;

public interface BlogSearchService {
  List<TopKeyword> findTop10ByOrderByCountDesc();
  BlogResultDto searchBlog(Provider provider,String query, SortType sort, int page, int size);
}
