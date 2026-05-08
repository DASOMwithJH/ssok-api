package DasomwithJH.ssok.repository;

import DasomwithJH.ssok.entity.Vendor;
import DasomwithJH.ssok.entity.VendorProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorProductRepository extends JpaRepository<VendorProduct, Integer> {
    List<VendorProduct> findByVendor(Vendor vendor);
}
