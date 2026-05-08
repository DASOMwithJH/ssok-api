package DasomwithJH.ssok.entity;

import DasomwithJH.ssok.entity.enums.ProposalStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "vendor_proposals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VendorProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proposal_id")
    private Integer proposalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private FundingProject project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "v_prod_id", nullable = false)
    private VendorProduct vendorProduct;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProposalStatus status;

    @CreationTimestamp
    @Column(name = "proposed_at", updatable = false)
    private LocalDateTime proposedAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    public void approve() {
        this.status = ProposalStatus.APPROVED;
        this.respondedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = ProposalStatus.REJECTED;
        this.respondedAt = LocalDateTime.now();
    }
}
