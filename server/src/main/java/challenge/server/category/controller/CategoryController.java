package challenge.server.category.controller;

import challenge.server.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    // 카테고리 등록
    @PostMapping
    public ResponseEntity createCategory(){
        CategoryDto.Response response = createCategoryResponseDto();
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    // 카테고리 수정
    @PatchMapping("/{category-id}")
    public ResponseEntity changeCategory(@PathVariable("category-id") @Positive Long categoryId) {
        CategoryDto.Response response = createCategoryResponseDto();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // 카테고리 전체 조회
    @GetMapping
    public ResponseEntity findCategories(@RequestParam @Positive int page,
                                         @RequestParam @Positive int size) {
        List<CategoryDto.Response> responses = List.of(createCategoryResponseDto(),createCategoryResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 카테고리 삭제
    @DeleteMapping("/{category-id}")
    public ResponseEntity deleteCategory(@PathVariable("category-id") @Positive Long categoryId) {

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 응답 더미데이터 - 카테고리 DTO
    public CategoryDto.Response createCategoryResponseDto() {
        return CategoryDto.Response.builder()
                .categoryId(1L)
                .type("자기계발")
                .build();
    }

}

