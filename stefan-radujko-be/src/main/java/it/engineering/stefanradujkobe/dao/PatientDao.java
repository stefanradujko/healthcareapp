package it.engineering.stefanradujkobe.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.engineering.stefanradujkobe.entity.PatientEntity;

@Repository
public interface PatientDao extends JpaRepository<PatientEntity, Long> {

	Optional<PatientEntity> findByNameAndSurname(String name, String surname);

	@Query("SELECT p FROM PatientEntity p LEFT JOIN OrganizationEntity o ON p.organization.id = o.id "
			+ "LEFT JOIN PractitionerEntity pr ON p.practitioner.id = pr.id "
			+ "WHERE p.name LIKE CONCAT('%',:name ,'%') AND p.surname LIKE CONCAT('%',:surname ,'%') "
			+ "AND (o.name LIKE CONCAT('%',:organization ,'%') OR p.organization = NULL) AND "
			+ "(pr.name LIKE CONCAT('%',:practName ,'%') OR p.practitioner = NULL) AND "
			+ "(pr.surname LIKE CONCAT('%',:practSurname ,'%') OR p.practitioner = NULL)")
	Page<PatientEntity> findByUnassigned(Pageable pageable, String name, String surname, String organization,
			String practName, String practSurname);
	
	@Query("SELECT p FROM PatientEntity p JOIN OrganizationEntity o ON p.organization.id = o.id JOIN "
			+ "PractitionerEntity pr ON p.practitioner.id = pr.id "
			+ "WHERE p.name LIKE CONCAT('%',:name ,'%') AND p.surname LIKE CONCAT('%',:surname ,'%') "
			+ "AND o.name LIKE CONCAT('%',:organization ,'%') AND "
			+ "pr.name LIKE CONCAT('%',:practName ,'%') AND pr.surname LIKE CONCAT('%',:practSurname ,'%')")
	Page<PatientEntity> findByFilter(Pageable pageable, String name, String surname, String organization,
			String practName, String practSurname);

	
	@Modifying
	@Query("UPDATE PatientEntity p SET p.practitioner.id = NULL WHERE p.practitioner.id = ?1")
	void deletePractitioner(Long practitionerId);

	@Modifying
	@Query("UPDATE PatientEntity p SET p.organization.id = NULL WHERE p.organization.id = ?1")
	void deleteOrganization(Long organizationId);
	
	

}
