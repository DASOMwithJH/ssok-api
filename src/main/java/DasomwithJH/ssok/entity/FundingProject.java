package DasomwithJH.ssok.entity;

import DasomwithJH.ssok.entity.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "funding_projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FundingProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "v_prod_id")
    private VendorProduct vendorProduct;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "ai_image_url", nullable = false, columnDefinition = "TEXT")
    private String aiImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProjectStatus status;

    @Column(name = "max_unit_price", nullable = false)
    private Integer maxUnitPrice;

    @Column(name = "target_count", nullable = false)
    private Integer targetCount;

    @Column(name = "current_count", nullable = false)
    private Integer currentCount;

    @Column(name = "target_date")
    private LocalDateTime targetDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void changeStatus(ProjectStatus status) {
        this.status = status;
    }

    public void assignVendorAndProduct(Vendor vendor, VendorProduct vendorProduct) {
        this.vendor = vendor;
        this.vendorProduct = vendorProduct;
    }

    public void incrementCurrentCount() {
        this.currentCount++;
    }
}