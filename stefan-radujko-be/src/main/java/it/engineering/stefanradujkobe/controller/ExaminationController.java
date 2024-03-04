package it.engineering.stefanradujkobe.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import it.engineering.stefanradujkobe.dto.ExaminationDto;
import it.engineering.stefanradujkobe.service.ExaminationService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("examinations")
public class ExaminationController {
	private final  ExaminationService examinationService;

	public ExaminationController(ExaminationService examinationService) {
		super();
		this.examinationService = examinationService;
	}

	@PostMapping()
	public ResponseEntity<Object> save(@Valid @RequestBody ExaminationDto examinationDto) {
		return ResponseEntity.status(HttpStatus.OK).body(examinationService.save(examinationDto));
	}
	
	@GetMapping("filter")
	public ResponseEntity<Page<ExaminationDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize,
			@RequestParam(defaultValue = "status") String sortBy, @RequestParam(defaultValue = "asc") String sortOrder,
			@RequestParam(defaultValue = "") String organization,
			@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String surname,
			@RequestParam(defaultValue = "") String serviceType,
			@RequestParam(defaultValue = "") String status,
			@RequestParam(defaultValue = "") String priority) {
		return new ResponseEntity<Page<ExaminationDto>>(examinationService.findAll(pageNo, pageSize, sortBy, sortOrder, organization, name, surname, serviceType, status, priority), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ExaminationDto examinationDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(examinationService.update(examinationDto));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<ExaminationDto> examination = examinationService.findById(id);
		if (examination.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(examination.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Examination id is invalid!");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
			try{
				examinationService.delete(id);
			} catch(RuntimeException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
			return ResponseEntity.ok("Deleted examination!");
	}
	
	@GetMapping("count/{id}")
	public int countByOrganization(@PathVariable Long id) {
		return examinationService.countByOrganization(id);
	}
	
	@GetMapping("count-active/{id}")
	public int countActiveByOrganization(@PathVariable Long id) {
		return examinationService.countActiveByOrganization(id);
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
