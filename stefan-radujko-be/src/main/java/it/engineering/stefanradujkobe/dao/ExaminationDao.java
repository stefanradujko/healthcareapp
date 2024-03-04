package it.engineering.stefanradujkobe.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.engineering.stefanradujkobe.entity.ExaminationEntity;

@Repository
public interface ExaminationDao extends JpaRepository<ExaminationEntity, Long>  {
	
	@Query("SELECT CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END FROM ExaminationEntity e WHERE e.status = 'in-progress' AND e.organization.id = ?1")
	boolean existsByOrganization(Long organizationId);

	@Query("SELECT CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END FROM ExaminationEntity e WHERE e.status = 'in-progress' AND e.patient.id = ?1")
	boolean existsByPatient(Long patientId);

	@Query("SELECT CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END FROM ExaminationEntity e JOIN ExaminationPractitionerEntity ep "
			+ "ON e.id = ep.examination.id WHERE e.status = 'in-progress' AND ep.practitioner.id = ?1")
	boolean existsByPractitioner(Long practitionerId);

	@Query("SELECT COUNT(*) FROM ExaminationEntity e WHERE e.organization.id=?1")
	int countByOrganization(Long organizationId);

	@Query("SELECT COUNT(*) FROM ExaminationEntity e WHERE e.organization.id=?1 AND e.status = 'in-progress'")
	int countActiveByOrganization(Long organizationId);


	@Query("SELECT e FROM ExaminationEntity e JOIN PatientEntity p on e.patient.id = p.id "
			+ "JOIN OrganizationEntity o ON e.organization.id = o.id JOIN "
			+ "ServiceTypeEntity st on e.serviceType.id = st.id "
			+ "WHERE p.name LIKE CONCAT('%',:name ,'%') AND p.surname LIKE CONCAT('%',:surname ,'%') "
			+ "AND o.name LIKE CONCAT('%',:organization ,'%') AND "
			+ "st.name LIKE CONCAT('%',:serviceType ,'%') AND e.status LIKE CONCAT('%',:status ,'%') "
			+ "AND (e.priority LIKE CONCAT('%',:priority ,'%') OR e.priority = NULL)")
	Page<ExaminationEntity> findByFilter(Pageable pageable, String organization, String name, String surname,
			String serviceType, String status, String priority);

	@Modifying
	@Query("UPDATE ExaminationEntity e SET e.status = 'entered-in-error' WHERE e.patient.id = ?1")
	void deleteByPatient(Long id);

	@Modifying
	@Query("UPDATE ExaminationEntity e SET e.status = 'entered-in-error' WHERE e.organization.id = ?1")
	void deletByOrganization(Long id);

}
