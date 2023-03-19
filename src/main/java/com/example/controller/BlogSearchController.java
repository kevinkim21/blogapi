package com.example.controller;

import com.example.common.Provider;
import com.example.common.SortType;
import com.example.dto.response.BlogResultDto;
import com.example.dto.response.ErrorDto;
import com.example.dto.response.TopKeyword;
import com.example.service.BlogSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Blog API", description = "blog api 입니다.")
@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogSearchController {

  private final BlogSearchService blogSearchService;

  @Operation(summary = "인기 키워드 조회", description = "인기 키워드 조회")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공",
          content = @Content(array = @ArraySchema(schema= @Schema(implementation = TopKeyword.class)))),
      @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping("/topKeywords")
  public List<TopKeyword> getTopKeywords() {
    return blogSearchService.findTop10ByOrderByCountDesc();
  }

  @Operation(summary = "블로그 조회", description = "블로그 검색")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공",
          content = @Content(array = @ArraySchema(schema= @Schema(implementation = BlogResultDto.class)))),
      @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping("/search")
  public BlogResultDto getBlogs(

      @Parameter(description = "검색 제공 소스", required = false)
      @RequestParam(required = false, defaultValue = "KAKAO") Provider provider,

      @Parameter(description = "검색어", required = false)
      @RequestParam @NotBlank String query,

      @Parameter(description = "정렬 기준", required = false)
      @RequestParam(required = false, defaultValue = "ACCURACY") SortType sort,

      @Parameter(description = "페이지", required = false)
      @RequestParam(required = false,defaultValue = "1") @Min(1) int page,

      @Parameter(description = "사이즈", required = false)
      @RequestParam(required = false,defaultValue = "10") @Min(1)  int size) {
    return blogSearchService.searchBlog(provider,query, sort, page, size);
  }

}
