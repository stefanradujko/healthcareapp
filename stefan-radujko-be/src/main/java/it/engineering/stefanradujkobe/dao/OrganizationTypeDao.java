package it.engineering.stefanradujkobe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.engineering.stefanradujkobe.entity.OrganizationTypeEntity;

@Repository
public interface OrganizationTypeDao extends JpaRepository<OrganizationTypeEntity, Long> {
	
}
