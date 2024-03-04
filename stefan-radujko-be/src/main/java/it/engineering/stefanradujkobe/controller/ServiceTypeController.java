package it.engineering.stefanradujkobe.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.engineering.stefanradujkobe.dto.ServiceTypeDto;
import it.engineering.stefanradujkobe.service.ServiceTypeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("services/type")
public class ServiceTypeController {
	private final ServiceTypeService serviceTypeService;

	public ServiceTypeController(ServiceTypeService serviceTypeService) {
		super();
		this.serviceTypeService = serviceTypeService;
	}
	
	@GetMapping
	public List<ServiceTypeDto> findAll() {
		return serviceTypeService.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<ServiceTypeDto> serviceType = serviceTypeService.findById(id);
		if (serviceType.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(serviceType.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service type id is invalid!");
	}
	
	@PostMapping()
	public void saveAll(@Valid @RequestBody List<ServiceTypeDto> list) {
		serviceTypeService.saveAll(list);
	}
}
