package DasomwithJH.ssok.service;

import DasomwithJH.ssok.dto.artistdto.MyArtistResponse;
import DasomwithJH.ssok.entity.Artist;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.entity.UserArtist;
import DasomwithJH.ssok.global.exception.CoreException;
import DasomwithJH.ssok.global.exception.code.CommonErrorCode;
import DasomwithJH.ssok.repository.ArtistRepository;
import DasomwithJH.ssok.repository.UserArtistRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserArtistService {

    private final UserArtistRepository userArtistRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public boolean toggleArtist(User user, Integer artistId) {
        Artist artist = artistRepository.findById(artistId)
            .orElseThrow(() -> new CoreException(CommonErrorCode.RESOURCE_NOT_FOUND));

        return userArtistRepository.findByUserAndArtist(user, artist)
            .map(existing -> {
                userArtistRepository.delete(existing);
                return false; // 등록 해제
            })
            .orElseGet(() -> {
                userArtistRepository.save(UserArtist.builder()
                    .user(user)
                    .artist(artist)
                    .build());
                return true; // 등록
            });
    }

    public List<MyArtistResponse> getMyArtists(User user) {
        return userArtistRepository.findByUser(user).stream()
            .map(MyArtistResponse::new)
            .toList();
    }
}
