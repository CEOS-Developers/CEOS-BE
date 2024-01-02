package ceos.backend.domain.retrospect.service;


import ceos.backend.domain.retrospect.domain.Retrospect;
import ceos.backend.domain.retrospect.dto.request.CreateRetrospectRequest;
import ceos.backend.domain.retrospect.dto.response.GetRetrospectResponse;
import ceos.backend.domain.retrospect.dto.response.GetRetrospectsResponse;
import ceos.backend.domain.retrospect.exception.RetrospectNotFound;
import ceos.backend.domain.retrospect.repository.RetrospectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RetrospectService {
    private final RetrospectRepository retrospectRepository;

    // 1. 회고 작성 메서드
    @Transactional
    public void createRetrospect(CreateRetrospectRequest createRetrospectRequest) {
        retrospectRepository.save(createRetrospectRequest.toEntity());
    }

    // 2. 회고 리스트 메서드(페이징)
    @Transactional(readOnly = true)
    public GetRetrospectsResponse getRetrospects(Integer pageNum, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNum, limit, sort);

        Page<Retrospect> data = retrospectRepository.findAll(pageRequest);

        return GetRetrospectsResponse.fromPageable(data);
    }

    // 3. 회고 수정 메서드
    @Transactional
    public GetRetrospectResponse updateRetrospect(
            Long id, CreateRetrospectRequest createRetrospectRequest) {
        Retrospect retrospect =
                retrospectRepository.findById(id).orElseThrow(() -> new RetrospectNotFound());

        retrospect.update(createRetrospectRequest);

        return GetRetrospectResponse.fromEntity(retrospect);
    }

    // 4. 회고 삭제 메서드
    @Transactional
    public void deleteRetrospect(Long id) {
        Retrospect retrospect =
                retrospectRepository.findById(id).orElseThrow(() -> new RetrospectNotFound());

        retrospectRepository.delete(retrospect);
    }

    // 5. 회고 상세 조회 메서드
    @Transactional(readOnly = true)
    public GetRetrospectResponse getRetrospect(Long id) {
        Retrospect retrospect =
                retrospectRepository.findById(id).orElseThrow(() -> new RetrospectNotFound());

        return GetRetrospectResponse.fromEntity(retrospect);
    }
}
