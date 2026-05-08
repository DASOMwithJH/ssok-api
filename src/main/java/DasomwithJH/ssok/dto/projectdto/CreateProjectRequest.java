package DasomwithJH.ssok.dto.projectdto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateProjectRequest {
    private Integer artistId;       // 선택한 아티스트
    private String title;           // 굿즈 제목
    private String description;     // 굿즈 설명
    private String aiImageUrl;      // 생성된 AI 이미지 URL
    private Integer vendorProductId; // 선택한 업체 상품 카테고리 (가격/수량은 여기서 자동 반영)
    private LocalDateTime targetDate; // 펀딩 마감일
}
