package DasomwithJH.ssok.service;

import DasomwithJH.ssok.config.OpenAiProperties;
import DasomwithJH.ssok.dto.ImageGenerationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageGenerationService {

    private static final String BASE_PROMPT = """
            You are a merchandise concept designer for "ssok", a fan-led goods production platform for indie artists.

            The artist has granted permission to use their official materials for this project.
            Use the provided reference image, artist materials, and user prompt to create a merchandise design concept that can be reviewed by a workshop or goods manufacturer.

            The output should look like a clean production-ready concept image for fan goods.
            It should be suitable as a design draft, mockup, or visual direction for small-batch goods production.

            Important context:
            - This is an artist-approved fan goods project.
            - Official artist logos, symbols, album visuals, and provided images may be used if included in the reference material.
            - The user is providing an idea sketch or reference image, not a final design file.
            - The generated image should help a workshop understand the desired design direction.
            - Make the design clear, printable, and easy to manufacture.
            - Keep the layout clean and product-focused.
            - Avoid overly complex details that would be difficult to print or produce.
            - Do not create unrelated visuals beyond the provided artist materials and user request.

            Brand style:
            - warm
            - friendly
            - fan-made but polished
            - indie music
            - small concert atmosphere
            - clean white base
            - warm orange accent
            - cute but not childish

            Product type: %s
            Artist name: %s
            Artist context: %s
            User prompt: %s
            Fan message text: %s
            Reference image description: %s

            Create a merchandise design concept suitable for production review.
            """;

    private final RestClient openAiRestClient;
    private final OpenAiProperties openAiProperties;

    public ImageGenerationResponse generateGoodsImage(MultipartFile image, String userPrompt) throws IOException {
        String fullPrompt = BASE_PROMPT.formatted("", "", "", userPrompt, "", "");
        MultiValueMap<String, Object> body = buildRequestBody(image, fullPrompt);

        OpenAiImageEditResponse response = openAiRestClient.post()
                .uri("/images/edits")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(OpenAiImageEditResponse.class);

        String base64 = extractBase64(response);
        return new ImageGenerationResponse(fullPrompt, base64);
    }

    private MultiValueMap<String, Object> buildRequestBody(MultipartFile image, String prompt) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        ByteArrayResource imageResource = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                String original = image.getOriginalFilename();
                return (original != null && !original.isBlank()) ? original : "image.png";
            }
        };

        body.add("image[]", imageResource);
        body.add("model", openAiProperties.getImage().getModel());
        body.add("prompt", prompt);
        body.add("n", "1");
        body.add("size", openAiProperties.getImage().getSize());

        return body;
    }

    private String extractBase64(OpenAiImageEditResponse response) {
        if (response == null || response.data() == null || response.data().isEmpty()) {
            throw new IllegalStateException("OpenAI API로부터 이미지를 받지 못했습니다.");
        }
        return response.data().getFirst().b64_json();
    }

    record OpenAiImageEditResponse(List<ImageData> data) {}
    record ImageData(String b64_json) {}
}
