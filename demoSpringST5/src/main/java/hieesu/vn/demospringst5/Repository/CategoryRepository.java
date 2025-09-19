package hieesu.vn.demospringst5.Repository;

import hieesu.vn.demospringst5.Entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    // Tìm chính xác để check trùng
    Optional<CategoryEntity> findByCategoryName(String categoryName);

    // Tìm gần đúng
    List<CategoryEntity> findByCategoryNameContaining(String name);

    Page<CategoryEntity> findByCategoryNameContaining(String name, Pageable pageable);
}
