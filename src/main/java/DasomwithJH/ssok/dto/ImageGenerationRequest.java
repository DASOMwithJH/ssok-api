package DasomwithJH.ssok.dto;

public record ImageGenerationRequest(
        String productType,
        String artistName,
        String artistContext,
        String userPrompt,
        String messageText,
        String referenceImageDescription
) {}
