package DasomwithJH.ssok.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vendor_price_tiers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VendorPriceTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Integer tierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "v_prod_id", nullable = false)
    private VendorProduct vendorProduct;

    @Column(name = "min_quantity", nullable = false)
    private Integer minQuantity;

    @Column(name = "max_quantity", nullable = false)
    private Integer maxQuantity;

    @Column(name = "price_per_unit", nullable = false)
    private Integer pricePerUnit;
}