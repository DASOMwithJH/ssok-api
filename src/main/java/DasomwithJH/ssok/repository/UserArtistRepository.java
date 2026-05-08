package DasomwithJH.ssok.repository;

import DasomwithJH.ssok.entity.Artist;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.entity.UserArtist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserArtistRepository extends JpaRepository<UserArtist, Integer> {
    boolean existsByUserAndArtist(User user, Artist artist);
    Optional<UserArtist> findByUserAndArtist(User user, Artist artist);
    List<UserArtist> findByUser(User user);
}
