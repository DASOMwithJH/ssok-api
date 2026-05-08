package DasomwithJH.ssok.repository;

import DasomwithJH.ssok.entity.VendorPriceTier;
import DasomwithJH.ssok.entity.VendorProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorPriceTierRepository extends JpaRepository<VendorPriceTier, Integer> {
    List<VendorPriceTier> findByVendorProductOrderByMinQuantityAsc(VendorProduct vendorProduct);
}
