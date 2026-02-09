package ceos.backend.domain.subscriber.service;


import ceos.backend.domain.recruitment.repository.RecruitmentRepository;
import ceos.backend.domain.subscriber.domain.Subscriber;
import ceos.backend.domain.subscriber.dto.request.SubscribeRequest;
import ceos.backend.domain.subscriber.helper.SubscriberHelper;
import ceos.backend.domain.subscriber.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberHelper subscriberHelper;
    private final RecruitmentRepository recruitmentRepository;
    private final SubscriberRepository subscriberRepository;

    @Transactional
    public void subscribeMail(SubscribeRequest subscribeRequest) {

        //이메일 중복 검증
        subscriberHelper.validateEmail(subscribeRequest.getEmail());

        Subscriber subscriber = Subscriber.from(subscribeRequest.getEmail(), subscribeRequest.getPhoneNum());
        subscriberRepository.save(subscriber);
    }

    @Transactional(readOnly = true)
    public void sendRecruitingMail() {
        LocalDate startDate = recruitmentRepository.findAll().get(0).getStartDateDoc().toLocalDate();
        LocalDate endDate = recruitmentRepository.findAll().get(0).getEndDateDoc().toLocalDate();
        LocalDate now = LocalDate.now();
        List<Subscriber> subscribers = subscriberRepository.findAll();

        //리쿠르팅 기간 검증
        subscriberHelper.validateDate(startDate, endDate, now);

        // 메일 보내기
        for (Subscriber subscriber : subscribers) {
            subscriberHelper.sendRecruitEmail(subscriber.getEmail());
        }
    }
}
