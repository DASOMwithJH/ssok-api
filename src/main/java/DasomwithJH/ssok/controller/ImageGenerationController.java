package DasomwithJH.ssok.controller;

import DasomwithJH.ssok.dto.ImageGenerationResponse;
import DasomwithJH.ssok.service.ImageGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ai/image")
@RequiredArgsConstructor
@Tag(name = "AI 굿즈 이미지 생성")
public class ImageGenerationController {

    private final ImageGenerationService imageGenerationService;

    @Operation(summary = "레퍼런스 이미지 + 프롬프트로 굿즈 AI 이미지 생성")
    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageGenerationResponse> editImage(
            @RequestPart("image") MultipartFile image,
            @RequestParam("userPrompt") String userPrompt
    ) throws Exception {
        ImageGenerationResponse response = imageGenerationService.generateGoodsImage(image, userPrompt);
        return ResponseEntity.ok(response);
    }
}
