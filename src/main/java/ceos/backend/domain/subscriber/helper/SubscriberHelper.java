package ceos.backend.domain.subscriber.helper;

import ceos.backend.domain.subscriber.exception.DuplicateData;
import ceos.backend.domain.subscriber.exception.InvalidAction;
import ceos.backend.domain.subscriber.repository.SubscriberRepository;
import ceos.backend.global.common.dto.AwsSESRecruitMail;
import ceos.backend.global.common.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SubscriberHelper {

    private final SubscriberRepository subscriberRepository;

    public void validateEmail(String email) {
        if (subscriberRepository.findByEmail(email).isPresent()) {
            throw DuplicateData.EXCEPTION;
        }
    }

    public void validateDate(LocalDate date,  LocalDate now) {
        if (!date.equals(now)) {
            throw InvalidAction.EXCEPTION;
        }
    }

    public void sendRecruitEmail(String email) {
        Event.raise(AwsSESRecruitMail.from(email));
    }

}
