package ceos.backend.infra.ses.repository;


import ceos.backend.infra.ses.domain.EmailSendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendHistoryRepository extends JpaRepository<EmailSendHistory, Long> {}
