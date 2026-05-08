package DasomwithJH.ssok.dto.projectdto;

import DasomwithJH.ssok.entity.FundingProject;
import DasomwithJH.ssok.entity.Vendor;
import DasomwithJH.ssok.entity.VendorProduct;
import DasomwithJH.ssok.entity.enums.ProjectStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class FundingProjectDetailResponse {

    private final Integer projectId;
    private final String title;
    private final String description;
    private final String aiImageUrl;
    private final ProjectStatus status;

    // 아티스트
    private final Integer artistId;
    private final String artistProfileImg;
    private final String artistBio;

    // 업체
    private final Integer vendorId;
    private final String companyName;
    private final Integer shippingFee;

    // 제품
    private final String categoryName;
    private final Integer minOrderQuantity;
    private final Integer weeklyMaxCapacity;
    private final List<PriceTierDto> priceTiers;

    // 펀딩 현황
    private final Integer targetCount;
    private final Integer currentCount;
    private final Integer maxUnitPrice;
    private final double achievementRate;
    private final LocalDateTime targetDate;
    private final LocalDateTime createdAt;

    public FundingProjectDetailResponse(
        FundingProject project,
        Vendor vendor,
        VendorProduct vendorProduct,
        List<PriceTierDto> priceTiers
    ) {
        this.projectId = project.getProjectId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.aiImageUrl = project.getAiImageUrl();
        this.status = project.getStatus();

        this.artistId = project.getArtist().getArtistId();
        this.artistProfileImg = project.getArtist().getProfileImg();
        this.artistBio = project.getArtist().getBio();

        this.vendorId = vendor != null ? vendor.getVendorId() : null;
        this.companyName = vendor != null ? vendor.getCompanyName() : null;
        this.shippingFee = vendor != null ? vendor.getShippingFee() : null;

        this.categoryName = vendorProduct != null ? vendorProduct.getCategoryName() : null;
        this.minOrderQuantity = vendorProduct != null ? vendorProduct.getMinOrderQuantity() : null;
        this.weeklyMaxCapacity = vendorProduct != null ? vendorProduct.getWeeklyMaxCapacity() : null;
        this.priceTiers = priceTiers;

        this.targetCount = project.getTargetCount();
        this.currentCount = project.getCurrentCount();
        this.maxUnitPrice = project.getMaxUnitPrice();
        this.achievementRate = project.getTargetCount() > 0
            ? (double) project.getCurrentCount() / project.getTargetCount() * 100 : 0;
        this.targetDate = project.getTargetDate();
        this.createdAt = project.getCreatedAt();
    }
}
