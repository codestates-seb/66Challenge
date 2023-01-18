package challenge.server.category.service;

import challenge.server.category.entity.Category;
import challenge.server.category.repository.CategoryRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Category category) {
        Category findCategory = findVerifiedCategory(category.getCategoryId());
        findCategory.changeType(category.getType());
        return categoryRepository.save(findCategory);
    }

    public Category findCategory(Long categoryId) {
        return findVerifiedCategory(categoryId);
    }

    // 전체 카테고리 조회
    @Transactional
    public List<String> findAllTypes() {
        List<Category> categories = categoryRepository.findAll(Sort.by("categoryId").descending());
        return categories.stream()
                .map(category -> category.getType())
                .collect(Collectors.toList());
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public Category findVerifiedCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND));
    }
}
