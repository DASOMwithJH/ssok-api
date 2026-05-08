package DasomwithJH.ssok.dto.vendordto;

import DasomwithJH.ssok.dto.projectdto.PriceTierDto;
import DasomwithJH.ssok.entity.VendorProduct;
import java.util.List;
import lombok.Getter;

@Getter
public class VendorProductDto {
    private final Integer vProdId;
    private final String categoryName;
    private final Integer minOrderQuantity;
    private final Integer weeklyMinCapacity;
    private final Integer weeklyMaxCapacity;
    private final List<PriceTierDto> priceTiers;

    public VendorProductDto(VendorProduct product, List<PriceTierDto> priceTiers) {
        this.vProdId = product.getVProdId();
        this.categoryName = product.getCategoryName();
        this.minOrderQuantity = product.getMinOrderQuantity();
        this.weeklyMinCapacity = product.getWeeklyMinCapacity();
        this.weeklyMaxCapacity = product.getWeeklyMaxCapacity();
        this.priceTiers = priceTiers;
    }
}
