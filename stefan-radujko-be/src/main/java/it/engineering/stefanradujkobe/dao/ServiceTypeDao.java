package it.engineering.stefanradujkobe.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.engineering.stefanradujkobe.entity.ServiceTypeEntity;

@Repository
public interface ServiceTypeDao extends JpaRepository<ServiceTypeEntity, Long>  {

}
