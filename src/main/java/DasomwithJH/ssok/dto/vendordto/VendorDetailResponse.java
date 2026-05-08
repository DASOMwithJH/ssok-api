package DasomwithJH.ssok.dto.vendordto;

import DasomwithJH.ssok.entity.Vendor;
import java.util.List;
import lombok.Getter;

@Getter
public class VendorDetailResponse {
    private final Integer vendorId;
    private final String companyName;
    private final Integer shippingFee;
    private final List<VendorProductDto> products;

    public VendorDetailResponse(Vendor vendor, List<VendorProductDto> products) {
        this.vendorId = vendor.getVendorId();
        this.companyName = vendor.getCompanyName();
        this.shippingFee = vendor.getShippingFee();
        this.products = products;
    }
}
