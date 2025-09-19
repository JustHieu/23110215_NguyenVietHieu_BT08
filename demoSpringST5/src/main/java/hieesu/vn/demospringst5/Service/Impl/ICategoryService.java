package hieesu.vn.demospringst5.Service.Impl;

import hieesu.vn.demospringst5.Entity.CategoryEntity;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    <S extends CategoryEntity> S save(S entity);

    List<CategoryEntity> findAll();
    Page<CategoryEntity> findAll(Pageable pageable);
    List<CategoryEntity> findAll(Sort sort);
    List<CategoryEntity> findAllById(Iterable<Long> ids);

    Optional<CategoryEntity> findById(Long id);
    <S extends CategoryEntity> Optional<S> findOne(Example<S> example);

    long count();
    void deleteById(Long id);
    void delete(CategoryEntity entity);
    void deleteAll();

    Optional<CategoryEntity> findByCategoryName(String categoryName);
    List<CategoryEntity> findByCategoryNameContaining(String name);
    Page<CategoryEntity> findByCategoryNameContaining(String name, Pageable pageable);
}
