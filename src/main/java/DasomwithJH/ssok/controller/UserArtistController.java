package DasomwithJH.ssok.controller;

import DasomwithJH.ssok.dto.artistdto.MyArtistResponse;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.global.common.BaseResponse;
import DasomwithJH.ssok.global.security.CustomUserDetails;
import DasomwithJH.ssok.service.UserArtistService;
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
@RequestMapping("/user/artists")
public class UserArtistController {

    private final UserArtistService userArtistService;

    @Operation(summary = "아티스트 하트 등록/해제", description = "이미 등록된 아티스트면 해제, 아니면 등록")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "등록 또는 해제 성공"),
        @ApiResponse(responseCode = "404", description = "아티스트를 찾을 수 없습니다")
    })
    @PostMapping("/{artistId}/heart")
    public ResponseEntity<BaseResponse<String>> toggleArtist(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Integer artistId
    ) {
        User user = userDetails.getUser();
        boolean registered = userArtistService.toggleArtist(user, artistId);
        String message = registered ? "아티스트 등록 완료" : "아티스트 등록 해제 완료";
        return BaseResponse.ok(message, null);
    }

    @Operation(summary = "내 아티스트 목록 조회", description = "하트 등록한 아티스트 목록 반환")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<BaseResponse<List<MyArtistResponse>>> getMyArtists(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();
        List<MyArtistResponse> result = userArtistService.getMyArtists(user);
        return BaseResponse.ok("내 아티스트 목록 조회 성공", result);
    }
}
