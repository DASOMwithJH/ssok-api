package DasomwithJH.ssok.repository;

import DasomwithJH.ssok.entity.FundingProject;
import DasomwithJH.ssok.entity.Vendor;
import DasomwithJH.ssok.entity.VendorProposal;
import DasomwithJH.ssok.entity.enums.ProposalStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorProposalRepository extends JpaRepository<VendorProposal, Integer> {
    List<VendorProposal> findByVendorAndStatus(Vendor vendor, ProposalStatus status);
    Optional<VendorProposal> findByProjectAndStatus(FundingProject project, ProposalStatus status);
}
