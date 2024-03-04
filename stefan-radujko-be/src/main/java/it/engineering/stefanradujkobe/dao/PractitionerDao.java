package it.engineering.stefanradujkobe.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.engineering.stefanradujkobe.entity.PractitionerEntity;


@Repository
public interface PractitionerDao extends JpaRepository<PractitionerEntity, Long> {
	
	@Query("SELECT p FROM PractitionerEntity p WHERE p.id = ?1 AND p.active = true")
	Optional<PractitionerEntity> findById(Long id);
	
	@Query("SELECT p FROM PractitionerEntity p WHERE p.name = ?1 AND p.surname = ?2 AND p.organization.id = ?3 AND p.active = true")
	Optional<PractitionerEntity> findByNameAndSurnameAndOrganization(String name, String surname, Long id);

	@Query("SELECT COUNT(*) FROM PractitionerEntity p WHERE p.organization.id=?1")
	int countByOrganization(Long organizationId);
	
	@Query("SELECT p FROM PractitionerEntity p JOIN OrganizationEntity o ON p.organization.id = o.id "
			+ "WHERE p.name LIKE CONCAT('%',:name ,'%') AND p.surname LIKE CONCAT('%',:surname ,'%') "
			+ "AND p.qualification LIKE CONCAT('%',:qualification ,'%') AND o.name LIKE CONCAT('%',:organization ,'%') AND p.active = true")
	Page<PractitionerEntity> findByFilter(Pageable pageable, String name, String surname, String qualification, String organization);

	@Modifying
	@Query("UPDATE PractitionerEntity p SET p.organization.id = NULL WHERE p.organization.id = ?1")
	void deleteOrganization(Long organizationId);

	@Query("SELECT p FROM PractitionerEntity p "
			+ "WHERE p.name LIKE CONCAT('%',:name ,'%') AND p.surname LIKE CONCAT('%',:surname ,'%') "
			+ "AND p.qualification LIKE CONCAT('%',:qualification ,'%') "
			+ "AND p.organization = NULL AND p.active = true")
	Page<PractitionerEntity>  findByFilterUnassigned(Pageable pageable, String name, String surname, String qualification);

	Optional<PractitionerEntity> findByUsername(String username);

	@Query("SELECT p FROM PractitionerEntity p WHERE p.organization.id = ?1")
	List<PractitionerEntity> findByOrganization(Long id);

}
