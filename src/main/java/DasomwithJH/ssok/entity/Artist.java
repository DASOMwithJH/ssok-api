package DasomwithJH.ssok.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "artists")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Integer artistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "profile_img", columnDefinition = "TEXT")
    private String profileImg;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "royalty_rate", precision = 5, scale = 2)
    private BigDecimal royaltyRate;
}