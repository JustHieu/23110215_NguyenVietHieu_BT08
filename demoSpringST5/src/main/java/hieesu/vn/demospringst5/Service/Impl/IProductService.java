package hieesu.vn.demospringst5.Service.Impl;

import hieesu.vn.demospringst5.Entity.ProductEntity;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    <S extends ProductEntity> S save(S entity);
    Optional<ProductEntity> findById(Long id);
    void delete(ProductEntity entity);

    List<ProductEntity> findAll();
    Page<ProductEntity> findAll(Pageable pageable);
    List<ProductEntity> findByProductNameContaining(String name);
    Page<ProductEntity> findByProductNameContaining(String name, Pageable pageable);
    Optional<ProductEntity> findByProductName(String name);
}
