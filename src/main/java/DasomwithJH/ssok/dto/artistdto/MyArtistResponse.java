package DasomwithJH.ssok.dto.artistdto;

import DasomwithJH.ssok.entity.UserArtist;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MyArtistResponse {
    private final Integer artistId;
    private final String profileImg;
    private final String bio;
    private final Boolean isVerified;
    private final LocalDateTime registeredAt;

    public MyArtistResponse(UserArtist userArtist) {
        this.artistId = userArtist.getArtist().getArtistId();
        this.profileImg = userArtist.getArtist().getProfileImg();
        this.bio = userArtist.getArtist().getBio();
        this.isVerified = userArtist.getArtist().getIsVerified();
        this.registeredAt = userArtist.getCreatedAt();
    }
}
