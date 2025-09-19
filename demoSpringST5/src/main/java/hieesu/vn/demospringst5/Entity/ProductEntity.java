package hieesu.vn.demospringst5.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 500, columnDefinition = "nvarchar(500) not null")
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    @Column(length = 200)
    private String images;

    @Column(columnDefinition = "nvarchar(500) not null")
    private String description;

    @Column(nullable = false)
    private double discount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(nullable = false)
    private short status;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity category;
}
