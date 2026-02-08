package ceos.backend.domain.application.repository;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Pass;
import ceos.backend.global.common.entity.Part;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicationRepository
        extends JpaRepository<Application, Long>, ApplicationRepositoryCustom {
    @Query("select distinct a from Application a" + " where a.applicantInfo.email = :email")
    Optional<Application> findByEmail(@Param("email") String email);

    @Query("select distinct a from Application a" + " where a.applicantInfo.uuid = :uuid")
    Optional<Application> findByUuid(@Param("uuid") String uuid);


    @Query(
            "select a from Application a"
                    + " where a.applicantInfo.uuid = :uuid"
                    + " and a.applicantInfo.email = :email")
    Optional<Application> findByUuidAndEmail(
            @Param("uuid") String uuid, @Param("email") String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
            "select a from Application a"
                    + " where a.applicantInfo.uuid = :uuid"
                    + " and a.applicantInfo.email = :email")
    Optional<Application> findByUuidAndEmailWithPessimisticLock(
            @Param("uuid") String uuid, @Param("email") String email);


    @Query("select count(a) > 0 from Application a" + " where a.applicantInfo.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("select a from Application a" + " where a.applicationDetail.part = :part")
    Page<Application> findAllByPart(@Param("part") Part part, PageRequest pageRequest);

    Page<Application> findAllByFinalPass(Pass fail, PageRequest pageRequest);

    Page<Application> findAllByDocumentPass(Pass fail, PageRequest pageRequest);

    @Query(
            "select a from Application a"
                    + " where a.applicationDetail.part = :part"
                    + " and a.documentPass = :pass")
    Page<Application> findAllByPartAndDocumentPass(
            @Param("part") Part backend, @Param("pass") Pass pass, PageRequest pageRequest);

    @Query(
            "select a from Application a"
                    + " where a.applicationDetail.part = :part"
                    + " and a.finalPass = :pass")
    Page<Application> findAllByPartAndFinalPass(
            @Param("part") Part product, @Param("pass") Pass pass, PageRequest pageRequest);

    Page<Application> findAllByDocumentPassAndFinalPass(
            Pass documentPass, Pass finalPass, PageRequest pageRequest);

    @Query(
            "select a from Application a"
                    + " where a.applicationDetail.part = :part"
                    + " and a.documentPass = :convertedDocPass"
                    + " and a.finalPass = :convertedFinalPass")
    Page<Application> findAllByPartAndDocumentPassAndFinalPass(
            @Param("part") Part backend,
            @Param("convertedDocPass") Pass convertedDocPass,
            @Param("convertedFinalPass") Pass convertedFinalPass,
            PageRequest pageRequest);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Application a where a.id = :id")
    Optional<Application> findByIdWithPessimisticLock(@Param("id") Long id);

}
