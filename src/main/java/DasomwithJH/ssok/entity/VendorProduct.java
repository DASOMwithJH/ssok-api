package DasomwithJH.ssok.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vendor_products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VendorProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "v_prod_id")
    private Integer vProdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    @Column(name = "min_order_quantity", nullable = false)
    private Integer minOrderQuantity;

    @Column(name = "weekly_min_capacity", nullable = false)
    private Integer weeklyMinCapacity;

    @Column(name = "weekly_max_capacity", nullable = false)
    private Integer weeklyMaxCapacity;
}