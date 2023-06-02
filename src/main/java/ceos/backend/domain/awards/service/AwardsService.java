package ceos.backend.domain.awards.service;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.CreateAwardsRequest;
import ceos.backend.domain.awards.repository.AwardsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwardsService {

    private final AwardsRepository awardsRepository;

    @Transactional
    public void createAwards(CreateAwardsRequest createAwardsRequest) {
        Awards awards = Awards.from(createAwardsRequest);
        awardsRepository.save(awards);
    }
}
