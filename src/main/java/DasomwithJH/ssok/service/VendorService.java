package DasomwithJH.ssok.service;

import DasomwithJH.ssok.dto.projectdto.PriceTierDto;
import DasomwithJH.ssok.dto.vendordto.VendorDetailResponse;
import DasomwithJH.ssok.dto.vendordto.VendorProductDto;
import DasomwithJH.ssok.repository.VendorPriceTierRepository;
import DasomwithJH.ssok.repository.VendorProductRepository;
import DasomwithJH.ssok.repository.VendorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendorService {

    private final VendorRepository vendorRepository;
    private final VendorProductRepository vendorProductRepository;
    private final VendorPriceTierRepository vendorPriceTierRepository;

    public List<VendorDetailResponse> getAllVendors() {
        return vendorRepository.findAll().stream()
            .map(vendor -> {
                List<VendorProductDto> products = vendorProductRepository.findByVendor(vendor)
                    .stream()
                    .map(product -> {
                        List<PriceTierDto> tiers = vendorPriceTierRepository
                            .findByVendorProductOrderByMinQuantityAsc(product)
                            .stream().map(PriceTierDto::new).toList();
                        return new VendorProductDto(product, tiers);
                    })
                    .toList();
                return new VendorDetailResponse(vendor, products);
            })
            .toList();
    }
}
