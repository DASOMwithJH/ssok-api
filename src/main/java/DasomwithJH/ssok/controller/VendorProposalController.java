package DasomwithJH.ssok.controller;

import DasomwithJH.ssok.dto.projectdto.VendorProposalResponse;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.global.common.BaseResponse;
import DasomwithJH.ssok.global.security.CustomUserDetails;
import DasomwithJH.ssok.service.VendorProposalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vendor/proposals")
public class VendorProposalController {

    private final VendorProposalService vendorProposalService;

    @Operation(summary = "받은 제안 목록 조회", description = "업체에게 들어온 PENDING 상태의 제안 목록을 반환합니다")
    @GetMapping
    public ResponseEntity<BaseResponse<List<VendorProposalResponse>>> getMyProposals(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();
        return BaseResponse.ok("제안 목록 조회 성공", vendorProposalService.getMyProposals(user));
    }

    @Operation(summary = "제안 승인", description = "제안을 승인하면 펀딩이 시작됩니다 (status: RECRUITING)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "승인 완료, 펀딩 시작"),
        @ApiResponse(responseCode = "403", description = "본인 업체의 제안이 아닙니다"),
        @ApiResponse(responseCode = "409", description = "이미 처리된 제안입니다")
    })
    @PostMapping("/{proposalId}/approve")
    public ResponseEntity<BaseResponse<Void>> approve(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Integer proposalId
    ) {
        User user = userDetails.getUser();
        vendorProposalService.approve(user, proposalId);
        return BaseResponse.ok("승인 완료. 펀딩이 시작됩니다.", null);
    }

    @Operation(summary = "제안 거절", description = "제안을 거절합니다. 프로젝트 생성자가 타 업체로 재제안할 수 있습니다")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "거절 완료"),
        @ApiResponse(responseCode = "403", description = "본인 업체의 제안이 아닙니다"),
        @ApiResponse(responseCode = "409", description = "이미 처리된 제안입니다")
    })
    @PostMapping("/{proposalId}/reject")
    public ResponseEntity<BaseResponse<Void>> reject(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Integer proposalId
    ) {
        User user = userDetails.getUser();
        vendorProposalService.reject(user, proposalId);
        return BaseResponse.ok("거절 완료.", null);
    }
}
