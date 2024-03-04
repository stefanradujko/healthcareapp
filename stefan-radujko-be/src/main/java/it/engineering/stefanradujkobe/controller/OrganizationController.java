package it.engineering.stefanradujkobe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.engineering.stefanradujkobe.dto.OrganizationDto;
import it.engineering.stefanradujkobe.service.OrganizationService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("organizations")
public class OrganizationController {
	private final OrganizationService organizationService;
	
	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	@PostMapping()
	public ResponseEntity<Object> save(@Valid @RequestBody OrganizationDto organizationDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(organizationService.save(organizationDto));
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("filter")
	public ResponseEntity<Page<OrganizationDto>> findAll(@RequestParam(defaultValue = "0")
	Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize,
	@RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortOrder,
	@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String orgType) {
		return new ResponseEntity<Page<OrganizationDto>>(organizationService.findAll(pageNo, pageSize, sortBy, sortOrder, name, orgType), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody OrganizationDto organizationDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(organizationService.update(organizationDto));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<OrganizationDto> organization = organizationService.findById(id);
		if (organization.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(organization.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization id is invalid!");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityExistsException {
			try{
				organizationService.delete(id);
			} catch(RuntimeException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
			return ResponseEntity.ok("Deleted organization!");
	}
	
	@GetMapping
	public List<OrganizationDto> findAll() {
		return organizationService.findAll();
	}
	
	@GetMapping("find/{name}")
	public ResponseEntity<Object> findByName(@PathVariable String name) {
		Optional<OrganizationDto> organization = organizationService.findByName(name);
		if (organization.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(organization.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization name is invalid!");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
	
	
