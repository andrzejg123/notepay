package pl.polsl.notepay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.notepay.model.entity.GroupInvitation;

@Repository
public interface GroupInvitationRepository extends JpaRepository<GroupInvitation, Long> {
}
