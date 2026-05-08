package DasomwithJH.ssok.service;

import DasomwithJH.ssok.dto.projectdto.CreateProjectRequest;
import DasomwithJH.ssok.dto.projectdto.FundingProjectDetailResponse;
import DasomwithJH.ssok.dto.projectdto.PriceTierDto;
import DasomwithJH.ssok.entity.Artist;
import DasomwithJH.ssok.entity.FundingProject;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.entity.Vendor;
import DasomwithJH.ssok.entity.VendorProduct;
import DasomwithJH.ssok.entity.VendorProposal;
import DasomwithJH.ssok.entity.enums.ProjectStatus;
import DasomwithJH.ssok.entity.enums.ProposalStatus;
import java.util.Optional;
import DasomwithJH.ssok.global.exception.CoreException;
import DasomwithJH.ssok.global.exception.code.BusinessErrorCode;
import DasomwithJH.ssok.global.exception.code.CommonErrorCode;
import DasomwithJH.ssok.repository.ArtistRepository;
import DasomwithJH.ssok.repository.FundingProjectRepository;
import DasomwithJH.ssok.repository.VendorPriceTierRepository;
import DasomwithJH.ssok.repository.VendorProductRepository;
import DasomwithJH.ssok.repository.VendorProposalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FundingProjectService {

    private final FundingProjectRepository fundingProjectRepository;
    private final ArtistRepository artistRepository;
    private final VendorProductRepository vendorProductRepository;
    private final VendorPriceTierRepository vendorPriceTierRepository;
    private final VendorProposalRepository vendorProposalRepository;

    @Transactional
    public FundingProject createProject(User creator, CreateProjectRequest request) {
        Artist artist = artistRepository.findById(request.getArtistId())
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));

        VendorProduct vendorProduct = vendorProductRepository.findById(request.getVendorProductId())
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));

        // targetCount: 업체 최소 주문 수량
        int targetCount = vendorProduct.getMinOrderQuantity();

        // maxUnitPrice: 최소 수량 구간 단가 (참여자 수가 적을수록 단가가 높은 첫 번째 구간)
        int maxUnitPrice = vendorPriceTierRepository
            .findByVendorProductOrderByMinQuantityAsc(vendorProduct)
            .stream().findFirst()
            .map(tier -> tier.getPricePerUnit())
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));

        FundingProject project = fundingProjectRepository.save(
            FundingProject.builder()
                .creator(creator)
                .artist(artist)
                .title(request.getTitle())
                .description(request.getDescription())
                .aiImageUrl(request.getAiImageUrl())
                .status(ProjectStatus.PENDING_VENDOR)
                .maxUnitPrice(maxUnitPrice)
                .targetCount(targetCount)
                .currentCount(0)
                .targetDate(request.getTargetDate())
                .build()
        );

        vendorProposalRepository.save(
            VendorProposal.builder()
                .project(project)
                .vendor(vendorProduct.getVendor())
                .vendorProduct(vendorProduct)
                .status(ProposalStatus.PENDING)
                .build()
        );

        return project;
    }

    @Transactional
    public void repropose(User creator, Integer projectId, Integer vendorProductId) {
        FundingProject project = fundingProjectRepository.findById(projectId)
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));

        if (!project.getCreator().getUserId().equals(creator.getUserId())) {
            throw new CoreException(BusinessErrorCode.OPERATION_NOT_ALLOWED);
        }
        if (project.getStatus() != ProjectStatus.PENDING_VENDOR) {
            throw new CoreException(BusinessErrorCode.INVALID_STATE);
        }
        // 진행 중인 PENDING 제안이 없어야 재제안 가능 (이전 제안이 REJECTED 상태여야 함)
        vendorProposalRepository.findByProjectAndStatus(project, ProposalStatus.PENDING)
            .ifPresent(p -> { throw new CoreException(BusinessErrorCode.OPERATION_NOT_ALLOWED); });

        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId)
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));

        vendorProposalRepository.save(
            VendorProposal.builder()
                .project(project)
                .vendor(vendorProduct.getVendor())
                .vendorProduct(vendorProduct)
                .status(ProposalStatus.PENDING)
                .build()
        );
    }

    public FundingProjectDetailResponse getDetail(Integer projectId) {
        FundingProject project = fundingProjectRepository.findById(projectId)
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));

        return buildDetailResponse(project);
    }

    public List<FundingProjectDetailResponse> getRecruitingProjects() {
        return fundingProjectRepository.findByStatus(ProjectStatus.RECRUITING).stream()
            .map(this::buildDetailResponse)
            .toList();
    }

    public List<FundingProjectDetailResponse> getMyProjects(User user) {
        return fundingProjectRepository.findByCreator(user).stream()
            .map(this::buildDetailResponse)
            .toList();
    }

    private FundingProjectDetailResponse buildDetailResponse(FundingProject project) {
        Vendor vendor = project.getVendor();
        VendorProduct vendorProduct = project.getVendorProduct();

        // PENDING_VENDOR 상태: 프로젝트에 vendor가 아직 assign되지 않았으므로
        // 진행 중인 VendorProposal에서 업체/상품 정보를 가져옴
        if (vendorProduct == null) {
            Optional<VendorProposal> pending = vendorProposalRepository
                .findByProjectAndStatus(project, ProposalStatus.PENDING);
            if (pending.isPresent()) {
                vendor = pending.get().getVendor();
                vendorProduct = pending.get().getVendorProduct();
            }
        }

        List<PriceTierDto> priceTiers = vendorProduct != null
            ? vendorPriceTierRepository
                .findByVendorProductOrderByMinQuantityAsc(vendorProduct)
                .stream().map(PriceTierDto::new).toList()
            : List.of();

        return new FundingProjectDetailResponse(project, vendor, vendorProduct, priceTiers);
    }
}
