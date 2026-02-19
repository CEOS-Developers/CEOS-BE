package ceos.backend.infra.ses.repository;


import ceos.backend.infra.ses.domain.EmailSendHistory;
import ceos.backend.infra.ses.domain.EmailType;
import ceos.backend.infra.ses.domain.SendStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmailSendHistoryRepository extends JpaRepository<EmailSendHistory, Long> {

    @Query(
            "select e from EmailSendHistory e "
                    + "where e.sendStatus = :sendStatus "
                    + "and e.emailType = :emailType "
                    + "and coalesce(e.retryCount, 0) < :maxRetryCount "
                    + "order by e.id asc")
    List<EmailSendHistory> findRetryTargets(
            @Param("sendStatus") SendStatus sendStatus,
            @Param("emailType") EmailType emailType,
            @Param("maxRetryCount") int maxRetryCount,
            Pageable pageable);
}
