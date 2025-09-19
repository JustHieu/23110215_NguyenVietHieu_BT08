package hieesu.vn.demospringst5.Controller.api;

import hieesu.vn.demospringst5.Entity.CategoryEntity;
import hieesu.vn.demospringst5.Entity.ProductEntity;
import hieesu.vn.demospringst5.Model.Response;
import hieesu.vn.demospringst5.Service.Impl.ICategoryService;
import hieesu.vn.demospringst5.Service.Impl.IProductService;
import hieesu.vn.demospringst5.Service.Impl.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductAPIController {

    @Autowired private IProductService productService;
    @Autowired private ICategoryService categoryService;
    @Autowired private IStorageService storageService;

    // GET all
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(new Response(true, "Thành công", productService.findAll()));
    }

    // POST get by id
    @PostMapping("/getProduct")
    public ResponseEntity<Response> getById(@RequestParam("id") Long id) {
        var opt = productService.findById(id);
        return opt.map(p -> ResponseEntity.ok(new Response(true, "Thành công", p)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, "Thất bại", null)));
    }

    // POST add (multipart)
    @PostMapping("/addProduct")
    public ResponseEntity<?> add(
            @RequestParam String productName,
            @RequestParam int quantity,
            @RequestParam double unitPrice,
            @RequestParam String description,
            @RequestParam double discount,
            @RequestParam short status,
            @RequestParam Long categoryId,
            @RequestParam(value = "images", required = false) MultipartFile images
    ) {
        // check trùng tên (tuỳ ý)
        if (productService.findByProductName(productName).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product đã tồn tại");
        }
        Optional<CategoryEntity> cat = categoryService.findById(categoryId);
        if (cat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CategoryId không tồn tại");
        }

        ProductEntity p = new ProductEntity();
        p.setProductName(productName);
        p.setQuantity(quantity);
        p.setUnitPrice(unitPrice);
        p.setDescription(description);
        p.setDiscount(discount);
        p.setStatus(status);
        p.setCreateDate(new Date());
        p.setCategory(cat.get());

        if (images != null && !images.isEmpty()) {
            String uid = UUID.randomUUID().toString();
            String store = storageService.getStorageFilename(images, uid);
            storageService.store(images, store);
            p.setImages(store);
        }

        productService.save(p);
        return ResponseEntity.ok(new Response(true, "Thêm thành công", p));
    }

    // PUT update (multipart)
    @PutMapping("/updateProduct")
    public ResponseEntity<Response> update(
            @RequestParam Long productId,
            @RequestParam String productName,
            @RequestParam int quantity,
            @RequestParam double unitPrice,
            @RequestParam String description,
            @RequestParam double discount,
            @RequestParam short status,
            @RequestParam Long categoryId,
            @RequestParam(value = "images", required = false) MultipartFile images
    ) {
        var opt = productService.findById(productId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response(false, "Không tìm thấy Product", null));
        }
        var p = opt.get();

        var cat = categoryService.findById(categoryId);
        if (cat.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response(false, "CategoryId không tồn tại", null));
        }

        if (images != null && !images.isEmpty()) {
            String uid = UUID.randomUUID().toString();
            String store = storageService.getStorageFilename(images, uid);
            storageService.store(images, store);
            p.setImages(store);
        }

        p.setProductName(productName);
        p.setQuantity(quantity);
        p.setUnitPrice(unitPrice);
        p.setDescription(description);
        p.setDiscount(discount);
        p.setStatus(status);
        p.setCategory(cat.get());

        productService.save(p);
        return ResponseEntity.ok(new Response(true, "Cập nhật thành công", p));
    }

    // DELETE
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Response> delete(@RequestParam Long productId) {
        var opt = productService.findById(productId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response(false, "Không tìm thấy Product", null));
        }
        productService.delete(opt.get());
        return ResponseEntity.ok(new Response(true, "Xoá thành công", opt.get()));
    }
}
