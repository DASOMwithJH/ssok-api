package DasomwithJH.ssok.dto.projectdto;

import DasomwithJH.ssok.entity.VendorPriceTier;
import lombok.Getter;

@Getter
public class PriceTierDto {
    private final Integer minQuantity;
    private final Integer maxQuantity;
    private final Integer pricePerUnit;

    public PriceTierDto(VendorPriceTier tier) {
        this.minQuantity = tier.getMinQuantity();
        this.maxQuantity = tier.getMaxQuantity();
        this.pricePerUnit = tier.getPricePerUnit();
    }
}
