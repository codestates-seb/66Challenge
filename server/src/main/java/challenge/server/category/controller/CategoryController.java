package challenge.server.category.controller;

import challenge.server.category.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Api
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

//    TODO 카테고리 CRUD 관리자 페이지 구현 시 필요
//    @ApiOperation(value = "카테고리 생성", notes = "관리자가 카테고리를 등록합니다.")
//    @PostMapping
//    public ResponseEntity createCategory(@RequestBody @Valid CategoryDto.Post categoryPostDto){
//        CategoryDto.Response response = createCategoryResponseDto();
//        return new ResponseEntity(response, HttpStatus.CREATED);
//    }
//
//    @ApiOperation(value = "카테고리 수정", notes="카테고리의 이름을 수정합니다.")
//    @PatchMapping("/{category-id}")
//    public ResponseEntity changeCategory(@PathVariable("category-id") @Positive Long categoryId) {
//        CategoryDto.Response response = createCategoryResponseDto();
//        return new ResponseEntity(response, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "모든 카테고리 조회")
//    @GetMapping
//    public ResponseEntity findCategories(@RequestParam @Positive int page,
//                                         @RequestParam @Positive int size) {
//        List<CategoryDto.Response> responses = List.of(createCategoryResponseDto(),createCategoryResponseDto());
//        return new ResponseEntity(responses, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "카테고리 삭제")
//    @DeleteMapping("/{category-id}")
//    public ResponseEntity deleteCategory(@PathVariable("category-id") @Positive Long categoryId) {
//
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    // 응답 더미데이터 - 카테고리 DTO
//    public CategoryDto.Response createCategoryResponseDto() {
//        return CategoryDto.Response.builder()
//                .categoryId(1L)
//                .type("자기계발")
//                .build();
//    }

}

