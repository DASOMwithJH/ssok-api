package DasomwithJH.ssok.controller;

import DasomwithJH.ssok.dto.vendordto.VendorDetailResponse;
import DasomwithJH.ssok.global.common.BaseResponse;
import DasomwithJH.ssok.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vendors")
public class VendorController {

    private final VendorService vendorService;

    @Operation(
        summary = "전체 업체 목록 조회",
        description = "모든 업체의 취급 물품, 최소 주문 수량, 주간 생산 가능 수량, 수량 구간별 가격을 반환합니다"
    )
    @GetMapping
    public ResponseEntity<BaseResponse<List<VendorDetailResponse>>> getAllVendors() {
        return BaseResponse.ok("업체 목록 조회 성공", vendorService.getAllVendors());
    }
}
