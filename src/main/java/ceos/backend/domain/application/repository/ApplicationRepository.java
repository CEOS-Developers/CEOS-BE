package ceos.backend.domain.application.repository;

import ceos.backend.domain.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select distinct a from Application a" +
            " where a.applicantInfo.name = :name" +
            " and a.applicantInfo.phoneNumber = :phoneNumber")
    Optional<Application> findByNameAndPhoneNumber(@Param("name") String name,
                                                   @Param("phoneNumber") String phoneNumber);

    @Query("select distinct a from Application a" +
            " where a.applicantInfo.uuid = :uuid")
    Optional<Application> findByUuid(@Param("uuid") String uuid);

    @Query("select distinct a from Application a" +
            " where a.applicantInfo.uuid = :uuid" +
            " and a.applicantInfo.email = :email")
    Optional<Application> findByUuidAndEmail(@Param("uuid") String uuid,
                                             @Param("email") String email);
}
