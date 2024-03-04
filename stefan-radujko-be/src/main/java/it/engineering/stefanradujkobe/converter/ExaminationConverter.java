package it.engineering.stefanradujkobe.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.engineering.stefanradujkobe.dto.ExaminationDto;
import it.engineering.stefanradujkobe.entity.ExaminationEntity;

@Component
public class ExaminationConverter implements GenericConverter<ExaminationDto, ExaminationEntity> {
	@Autowired
	private PatientConverter patientConverter;
	@Autowired
	private OrganizationConverter orgConverter;
	@Autowired
	private ServiceTypeConverter serviceTypeConverter;
	
	
	@Override
	public ExaminationEntity toEntity(ExaminationDto dto) {
		return new ExaminationEntity(dto.getId(), dto.getIdentifier(), patientConverter.toEntity(dto.getPatient()), 
				orgConverter.toEntity(dto.getOrganization()),
				dto.getStatus(), serviceTypeConverter.toEntity(dto.getServiceType()), dto.getPriority(),
				dto.getStartDate(), dto.getEndDate(), dto.getDiagnosis());
	}

	@Override
	public ExaminationDto toDto(ExaminationEntity entity) {
		return new ExaminationDto(entity.getId(), entity.getIdentifier(), patientConverter.toDto(entity.getPatient()),
				orgConverter.toDto(entity.getOrganization()),
				entity.getStatus(), serviceTypeConverter.toDto(entity.getServiceType()), entity.getPriority(),
				entity.getStartDate(), entity.getEndDate(), entity.getDiagnosis());
	}
}
