package it.engineering.stefanradujkobe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.engineering.stefanradujkobe.entity.ExaminationEntity;
import it.engineering.stefanradujkobe.entity.ExaminationPractitionerEntity;

@Repository
public interface ExaminationPractitionerDao extends JpaRepository<ExaminationPractitionerEntity, Long> {

	List<ExaminationPractitionerEntity> findByExamination(ExaminationEntity examination);

	void deleteByExamination(ExaminationEntity examination);


}
