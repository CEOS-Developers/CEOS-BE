package ceos.backend.domain.application.repository;

import static ceos.backend.domain.application.domain.QApplication.application;

import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Pass;
import ceos.backend.global.common.entity.Part;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ApplicationRepositoryCustomImpl implements ApplicationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ApplicationRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Application> findApplications(
            Part part,
            Pass docPass,
            Pass finalPass,
            String applicantName,
            PageRequest pageRequest) {

        // ApplicantInfo 에 있는 이름 검색

        List<Application> data = queryFactory
                .select(application)
                .from(application)
                .where(
                        partEq(part),
                        applicantNameEq(applicantName),
                        docPassEq(docPass),
                        finalPassEq(finalPass)
                )
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        return new PageImpl<>(data, pageRequest, data.size());
    }

    private BooleanExpression partEq(Part part) {
        return part == null ? null : application.applicationDetail.part.eq(part);
    }

    private BooleanExpression applicantNameEq(String applicantName) {
        return applicantName == null ? null : application.applicantInfo.name.eq(applicantName);
    }

    private BooleanExpression finalPassEq(Pass finalPass) {
        return finalPass == null ? null : application.finalPass.eq(finalPass);
    }

    private BooleanExpression docPassEq(Pass docPass) {
        return docPass == null ? null : application.documentPass.eq(docPass);
    }
}
