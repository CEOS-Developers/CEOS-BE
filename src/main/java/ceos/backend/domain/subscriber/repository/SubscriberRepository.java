package ceos.backend.domain.subscriber.repository;


import ceos.backend.domain.subscriber.domain.Subscriber;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByEmail(String email);
}
