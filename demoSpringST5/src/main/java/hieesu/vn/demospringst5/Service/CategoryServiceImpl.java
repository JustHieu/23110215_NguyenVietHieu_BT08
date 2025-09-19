package hieesu.vn.demospringst5.Service;

import hieesu.vn.demospringst5.Entity.CategoryEntity;
import hieesu.vn.demospringst5.Repository.CategoryRepository;
import hieesu.vn.demospringst5.Service.Impl.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public <S extends CategoryEntity> S save(S entity) {
        if (entity.getCategoryId() == null) {
            return categoryRepository.save(entity);
        }
        Optional<CategoryEntity> opt = findById(entity.getCategoryId());
        if (opt.isPresent()) {
            if (StringUtils.isBlank(entity.getIcon())) {
                entity.setIcon(opt.get().getIcon());
            }
        }
        return categoryRepository.save(entity);
    }

    @Override public List<CategoryEntity> findAll(){ return categoryRepository.findAll(); }
    @Override public Page<CategoryEntity> findAll(Pageable pageable){ return categoryRepository.findAll(pageable); }
    @Override public List<CategoryEntity> findAll(Sort sort){ return categoryRepository.findAll(sort); }
    @Override public List<CategoryEntity> findAllById(Iterable<Long> ids){ return categoryRepository.findAllById(ids); }
    @Override public Optional<CategoryEntity> findById(Long id){ return categoryRepository.findById(id); }
    @Override public <S extends CategoryEntity> Optional<S> findOne(Example<S> example){ return categoryRepository.findOne(example); }
    @Override public long count(){ return categoryRepository.count(); }
    @Override public void deleteById(Long id){ categoryRepository.deleteById(id); }
    @Override public void delete(CategoryEntity entity){ categoryRepository.delete(entity); }
    @Override public void deleteAll(){ categoryRepository.deleteAll(); }

    // ===== Impl mới theo CategoryName =====
    @Override public Optional<CategoryEntity> findByCategoryName(String categoryName){
        return categoryRepository.findByCategoryName(categoryName);
    }
    @Override public List<CategoryEntity> findByCategoryNameContaining(String name){
        return categoryRepository.findByCategoryNameContaining(name);
    }
    @Override public Page<CategoryEntity> findByCategoryNameContaining(String name, Pageable pageable){
        return categoryRepository.findByCategoryNameContaining(name, pageable);
    }
}
