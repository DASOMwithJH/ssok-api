package DasomwithJH.ssok.controller;

import DasomwithJH.ssok.dto.projectdto.CreateProjectRequest;
import DasomwithJH.ssok.dto.projectdto.FundingProjectDetailResponse;
import DasomwithJH.ssok.dto.projectdto.ReproposalRequest;
import DasomwithJH.ssok.entity.FundingProject;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.global.common.BaseResponse;
import DasomwithJH.ssok.global.security.CustomUserDetails;
import DasomwithJH.ssok.service.FundingProjectService;
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
@RequestMapping("/projects")
public class FundingProjectController {

    private final FundingProjectService fundingProjectService;


    @Operation(summary = "펀딩 프로젝트 생성", description = "AI 이미지와 업체를 선택해 프로젝트를 생성하고 업체에 제안을 발송합니다")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "프로젝트 생성 및 업체 제안 발송 완료"),
        @ApiResponse(responseCode = "404", description = "아티스트 또는 업체 상품을 찾을 수 없습니다")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<Integer>> createProject(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody CreateProjectRequest request
    ) {
        User user = userDetails.getUser();
        FundingProject project = fundingProjectService.createProject(user, request);
        return BaseResponse.created("프로젝트 생성 완료. 업체에 제안을 발송했습니다.", project.getProjectId());
    }

    @Operation(summary = "타 업체 재제안", description = "업체가 거절한 후 다른 업체 상품으로 재제안합니다")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "재제안 발송 완료"),
        @ApiResponse(responseCode = "403", description = "본인 프로젝트가 아닙니다"),
        @ApiResponse(responseCode = "409", description = "이미 진행 중인 제안이 있거나 잘못된 상태입니다")
    })
    @PostMapping("/{projectId}/repropose")
    public ResponseEntity<BaseResponse<Void>> repropose(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Integer projectId,
        @RequestBody ReproposalRequest request
    ) {
        User user = userDetails.getUser();
        fundingProjectService.repropose(user, projectId, request.getVendorProductId());
        return BaseResponse.ok("재제안 발송 완료", null);
    }

    @Operation(summary = "펀딩 프로젝트 상세 조회", description = "굿즈 이미지, 업체 정보, 가격 구간, 달성률 등 전체 정보를 반환합니다")
    @GetMapping("/{projectId}")
    public ResponseEntity<BaseResponse<FundingProjectDetailResponse>> getDetail(
        @PathVariable Integer projectId
    ) {
        return BaseResponse.ok("조회 성공", fundingProjectService.getDetail(projectId));
    }

    @Operation(summary = "모집 중인 펀딩 목록 조회")
    @GetMapping
    public ResponseEntity<BaseResponse<List<FundingProjectDetailResponse>>> getRecruitingProjects() {
        return BaseResponse.ok("조회 성공", fundingProjectService.getRecruitingProjects());
    }

    @Operation(summary = "나의 펀딩 목록 조회", description = "본인이 생성한 모든 펀딩 프로젝트를 상태 무관하게 반환합니다")
    @GetMapping("/my")
    public ResponseEntity<BaseResponse<List<FundingProjectDetailResponse>>> getMyProjects(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return BaseResponse.ok("나의 펀딩 목록 조회 성공", fundingProjectService.getMyProjects(userDetails.getUser()));
    }
}
