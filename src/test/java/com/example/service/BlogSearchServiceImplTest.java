package com.example.service;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.dto.response.TopKeyword;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class BlogSearchServiceImplTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BlogSearchService blogSearchService;

  @Test
  void testGetTopKeywords() throws Exception {
    // Given
    List<TopKeyword> topKeywords = new ArrayList<>();
    topKeywords.add(new TopKeyword("example query 1", 10));
    topKeywords.add(new TopKeyword("example query 2", 9));
    topKeywords.add(new TopKeyword("example query 3", 8));
    when(blogSearchService.findTop10ByOrderByCountDesc()).thenReturn(topKeywords);

    // When
    ResultActions resultActions = mockMvc.perform(get("/api/blog/topKeywords"));

    // Then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].query", is("example query 1")))
        .andExpect(jsonPath("$[0].count", is(10)))
        .andExpect(jsonPath("$[1].query", is("example query 2")))
        .andExpect(jsonPath("$[1].count", is(9)))
        .andExpect(jsonPath("$[2].query", is("example query 3")))
        .andExpect(jsonPath("$[2].count", is(8)));
  }
}




