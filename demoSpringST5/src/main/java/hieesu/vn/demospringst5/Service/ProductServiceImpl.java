// ProductServiceImpl
package hieesu.vn.demospringst5.Service;

import hieesu.vn.demospringst5.Entity.ProductEntity;
import hieesu.vn.demospringst5.Repository.ProductRepository;
import hieesu.vn.demospringst5.Service.Impl.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired ProductRepository repo;

    @Override public <S extends ProductEntity> S save(S entity) {
        if (entity.getProductId() != null) {
            var old = findById(entity.getProductId());
            if (old.isPresent() && StringUtils.isBlank(entity.getImages())) {
                entity.setImages(old.get().getImages());
            }
        }
        return repo.save(entity);
    }

    @Override public Optional<ProductEntity> findById(Long id){ return repo.findById(id); }
    @Override public void delete(ProductEntity entity){ repo.delete(entity); }
    @Override public List<ProductEntity> findAll(){ return repo.findAll(); }
    @Override public Page<ProductEntity> findAll(Pageable p){ return repo.findAll(p); }
    @Override public List<ProductEntity> findByProductNameContaining(String name){ return repo.findByProductNameContaining(name); }
    @Override public Page<ProductEntity> findByProductNameContaining(String name, Pageable p){ return repo.findByProductNameContaining(name,p); }
    @Override public Optional<ProductEntity> findByProductName(String name){ return repo.findByProductName(name); }
}
