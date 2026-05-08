package DasomwithJH.ssok.repository;

import DasomwithJH.ssok.entity.FundingProject;
import DasomwithJH.ssok.entity.User;
import DasomwithJH.ssok.entity.enums.ProjectStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingProjectRepository extends JpaRepository<FundingProject, Integer> {
    List<FundingProject> findByStatus(ProjectStatus status);
    List<FundingProject> findByCreator(User creator);
}
