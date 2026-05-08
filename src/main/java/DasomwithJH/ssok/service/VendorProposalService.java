package DasomwithJH.ssok.service;

import DasomwithJH.ssok.dto.projectdto.VendorProposalResponse;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.entity.Vendor;
import DasomwithJH.ssok.entity.VendorProposal;
import DasomwithJH.ssok.entity.enums.ProjectStatus;
import DasomwithJH.ssok.entity.enums.ProposalStatus;
import DasomwithJH.ssok.global.exception.CoreException;
import DasomwithJH.ssok.global.exception.code.BusinessErrorCode;
import DasomwithJH.ssok.global.exception.code.CommonErrorCode;
import DasomwithJH.ssok.repository.VendorProposalRepository;
import DasomwithJH.ssok.repository.VendorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendorProposalService {

    private final VendorProposalRepository vendorProposalRepository;
    private final VendorRepository vendorRepository;

    public List<VendorProposalResponse> getMyProposals(User user) {
        Vendor vendor = findVendor(user);
        return vendorProposalRepository.findByVendorAndStatus(vendor, ProposalStatus.PENDING)
            .stream().map(VendorProposalResponse::new).toList();
    }

    @Transactional
    public void approve(User user, Integer proposalId) {
        VendorProposal proposal = findProposalForVendor(user, proposalId);

        proposal.approve();

        proposal.getProject().assignVendorAndProduct(
            proposal.getVendor(),
            proposal.getVendorProduct()
        );
        proposal.getProject().changeStatus(ProjectStatus.RECRUITING);
    }

    @Transactional
    public void reject(User user, Integer proposalId) {
        VendorProposal proposal = findProposalForVendor(user, proposalId);
        proposal.reject();
    }

    private VendorProposal findProposalForVendor(User user, Integer proposalId) {
        Vendor vendor = findVendor(user);

        VendorProposal proposal = vendorProposalRepository.findById(proposalId)
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));

        if (!proposal.getVendor().getVendorId().equals(vendor.getVendorId())) {
            throw new CoreException(BusinessErrorCode.OPERATION_NOT_ALLOWED);
        }
        if (proposal.getStatus() != ProposalStatus.PENDING) {
            throw new CoreException(BusinessErrorCode.INVALID_STATE);
        }
        return proposal;
    }

    private Vendor findVendor(User user) {
        return vendorRepository.findByUser(user)
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
