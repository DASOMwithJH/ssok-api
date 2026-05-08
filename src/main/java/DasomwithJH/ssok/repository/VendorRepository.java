package DasomwithJH.ssok.repository;

import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.entity.Vendor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    Optional<Vendor> findByUser(User user);
}
