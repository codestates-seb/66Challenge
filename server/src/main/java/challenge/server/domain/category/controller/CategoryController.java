package challenge.server.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

//    TODO 카테고리 CRUD 관리자 페이지 구현 시 필요
//    @PostMapping
//    public ResponseEntity createCategory(@RequestBody @Valid CategoryDto.Post categoryPostDto){
//        CategoryDto.Response response = createCategoryResponseDto();
//        return new ResponseEntity(response, HttpStatus.CREATED);
//    }
//
//    @PatchMapping("/{category-id}")
//    public ResponseEntity changeCategory(@PathVariable("category-id") @Positive Long categoryId) {
//        CategoryDto.Response response = createCategoryResponseDto();
//        return new ResponseEntity(response, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity findCategories(@RequestParam @Positive int page,
//                                         @RequestParam @Positive int size) {
//        List<CategoryDto.Response> responses = List.of(createCategoryResponseDto(),createCategoryResponseDto());
//        return new ResponseEntity(responses, HttpStatus.OK);
//    }
//
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

