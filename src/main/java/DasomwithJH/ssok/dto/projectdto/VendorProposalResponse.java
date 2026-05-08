package DasomwithJH.ssok.dto.projectdto;

import DasomwithJH.ssok.entity.VendorProposal;
import DasomwithJH.ssok.entity.enums.ProposalStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class VendorProposalResponse {
    private final Integer proposalId;
    private final Integer projectId;
    private final String projectTitle;
    private final String aiImageUrl;
    private final String categoryName;
    private final Integer minOrderQuantity;
    private final ProposalStatus status;
    private final LocalDateTime proposedAt;

    public VendorProposalResponse(VendorProposal proposal) {
        this.proposalId = proposal.getProposalId();
        this.projectId = proposal.getProject().getProjectId();
        this.projectTitle = proposal.getProject().getTitle();
        this.aiImageUrl = proposal.getProject().getAiImageUrl();
        this.categoryName = proposal.getVendorProduct().getCategoryName();
        this.minOrderQuantity = proposal.getVendorProduct().getMinOrderQuantity();
        this.status = proposal.getStatus();
        this.proposedAt = proposal.getProposedAt();
    }
}
