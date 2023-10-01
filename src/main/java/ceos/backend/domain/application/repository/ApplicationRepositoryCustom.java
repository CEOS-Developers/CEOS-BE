package ceos.backend.domain.application.repository;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Pass;
import ceos.backend.global.common.entity.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ApplicationRepositoryCustom {
    Page<Application> findApplications(
            Part part, Pass docPass, Pass finalPass, String applicantName, PageRequest pageRequest);
}
