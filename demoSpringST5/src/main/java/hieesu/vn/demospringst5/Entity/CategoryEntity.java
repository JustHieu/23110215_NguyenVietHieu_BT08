package hieesu.vn.demospringst5.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Categories")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    // map đúng cột DB có sẵn
    @Column(name = "category_name", length = 200, columnDefinition = "nvarchar(200) not null")
    private String categoryName;

    @Column(columnDefinition = "nvarchar(255)")
    private String icon;

    // ⛔ Không serialize products để tránh vòng lặp + ConcurrentModification
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductEntity> products = new HashSet<>();
}
