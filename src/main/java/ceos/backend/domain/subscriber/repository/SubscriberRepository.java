package ceos.backend.domain.subscriber.repository;


import ceos.backend.domain.subscriber.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByEmail(String email);
}
