package it.engineering.stefanradujkobe.dao;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.engineering.stefanradujkobe.entity.OrganizationEntity;

@Repository
public interface OrganizationDao extends JpaRepository<OrganizationEntity, Long> {

	Optional<OrganizationEntity> findByName(String name);

	@Query("SELECT o FROM OrganizationEntity o JOIN OrganizationTypeEntity ot ON o.organizationType.id = ot.id "
			+ "WHERE o.name LIKE CONCAT('%',:name ,'%') AND ot.name LIKE CONCAT('%',:orgType ,'%') ")
	Page<OrganizationEntity> findByFilters(Pageable pageable, String name, String orgType);

	
	
	
}
