package DasomwithJH.ssok.controller;

import DasomwithJH.ssok.dto.projectdto.FundingProjectDetailResponse;
import DasomwithJH.ssok.global.common.BaseResponse;
import DasomwithJH.ssok.global.security.CustomUserDetails;
import DasomwithJH.ssok.service.FundingProjectService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final FundingProjectService fundingProjectService;

    @Operation(summary = "나의 펀딩 목록 조회", description = "본인이 생성한 모든 펀딩 프로젝트를 상태 무관하게 반환합니다")
    @GetMapping("/my")
    public ResponseEntity<BaseResponse<List<FundingProjectDetailResponse>>> getMyProjects(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return BaseResponse.ok("나의 펀딩 목록 조회 성공", fundingProjectService.getMyProjects(userDetails.getUser()));
    }

}
