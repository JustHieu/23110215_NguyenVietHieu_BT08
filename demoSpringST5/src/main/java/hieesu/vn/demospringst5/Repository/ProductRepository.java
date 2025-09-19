package hieesu.vn.demospringst5.Repository;

import hieesu.vn.demospringst5.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByProductNameContaining(String name);
    Page<ProductEntity> findByProductNameContaining(String name, Pageable pageable);
    Optional<ProductEntity> findByProductName(String name);

    // ⚠️ Ít dùng vì khó trùng chính xác:
    // Optional<ProductEntity> findByCreateDate(Date createAt);


    List<ProductEntity> findByCreateDateBetween(Date from, Date to);


    boolean existsByProductName(String productName);
    List<ProductEntity> findAllByCategory_CategoryId(Long categoryId);
    Page<ProductEntity> findAllByCategory_CategoryId(Long categoryId, Pageable pageable);
    Page<ProductEntity> findByProductNameContainingAndCategory_CategoryId(String name, Long categoryId, Pageable pageable);
    Page<ProductEntity> findByUnitPriceBetween(double min, double max, Pageable pageable);
    Page<ProductEntity> findByStatus(short status, Pageable pageable);
}
