package hieesu.vn.demospringst5.Controller.api;

import hieesu.vn.demospringst5.Entity.CategoryEntity;
import hieesu.vn.demospringst5.Model.Response;
import hieesu.vn.demospringst5.Service.Impl.ICategoryService;
import hieesu.vn.demospringst5.Service.Impl.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryAPIController {
    @Autowired private ICategoryService categoryService;
    @Autowired private IStorageService storageService;

    @GetMapping
    public ResponseEntity<Response> getAllCategory() {
        return ResponseEntity.ok(new Response(true, "Thành công", categoryService.findAll()));
    }

    @PostMapping(path = "/getCategory")
    public ResponseEntity<Response> getCategory(@RequestParam("id") Long id) {
        Optional<CategoryEntity> category = categoryService.findById(id);
        return category
                .map(c -> ResponseEntity.ok(new Response(true, "Thành công", c)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, "Thất bại", null)));
    }

    @PostMapping(path = "/addCategory")
    public ResponseEntity<?> addCategory(@RequestParam("categoryName") String categoryName,
                                         @RequestParam("icon") MultipartFile icon) {
        if (categoryService.findByCategoryName(categoryName).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Category đã tồn tại trong hệ thống");
        }
        CategoryEntity category = new CategoryEntity();

        if (icon != null && !icon.isEmpty()) {
            String id = UUID.randomUUID().toString();
            category.setIcon(storageService.getStorageFilename(icon, id));
            storageService.store(icon, category.getIcon());
        }

        category.setCategoryName(categoryName);
        categoryService.save(category);
        return ResponseEntity.ok(new Response(true, "Thêm Thành công", category));
    }

    @PutMapping(path = "/updateCategory")
    public ResponseEntity<Response> updateCategory(@RequestParam("categoryId") Long categoryId,
                                                   @RequestParam("categoryName") String categoryName,
                                                   @RequestParam(value = "icon", required = false) MultipartFile icon) {
        var opt = categoryService.findById(categoryId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response(false, "Không tìm thấy Category", null));
        }
        CategoryEntity c = opt.get();

        if (icon != null && !icon.isEmpty()) {
            String id = UUID.randomUUID().toString();
            c.setIcon(storageService.getStorageFilename(icon, id)); // <-- sửa tên hàm
            storageService.store(icon, c.getIcon());
        }

        c.setCategoryName(categoryName);
        categoryService.save(c);
        return ResponseEntity.ok(new Response(true, "Cập nhật Thành công", c));
    }

    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<Response> deleteCategory(@RequestParam("categoryId") Long categoryId){
        var opt = categoryService.findById(categoryId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response(false, "Không tìm thấy Category", null));
        }
        categoryService.delete(opt.get());
        return ResponseEntity.ok(new Response(true, "Xóa Thành công", opt.get()));
    }
}
