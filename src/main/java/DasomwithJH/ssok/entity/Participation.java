package DasomwithJH.ssok.entity;

import DasomwithJH.ssok.entity.enums.ParticipationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "participations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Integer participationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private FundingProject project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_paid", nullable = false)
    private Integer totalPaid;

    @Column(name = "refund_amount")
    private Integer refundAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ParticipationStatus status;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @CreationTimestamp
    @Column(name = "participated_at", updatable = false)
    private LocalDateTime participatedAt;
}