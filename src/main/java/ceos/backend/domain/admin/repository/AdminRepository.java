package ceos.backend.domain.admin.repository;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.global.common.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByNameAndEmail(String name, String email);
    Optional<Admin> findByUsername(String Username);
    //Optional<Admin> findByFindIdRequest(FindIdRequest findIdRequest);
    @Query("SELECT distinct a FROM Admin a " +
            "WHERE a.name = :name and a.email = :email and a.part = :part"
    )
    Optional<Admin> findIdBy(@Param(value = "name") String name, @Param(value = "email") String email, @Param(value = "part") Part part);
}
