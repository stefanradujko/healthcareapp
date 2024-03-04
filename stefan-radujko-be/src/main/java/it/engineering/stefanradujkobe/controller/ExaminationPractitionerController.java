package it.engineering.stefanradujkobe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.engineering.stefanradujkobe.dto.ExaminationPractitionerDto;
import it.engineering.stefanradujkobe.service.ExaminationPractitionerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("examinations/practitioners")
public class ExaminationPractitionerController {
	private final ExaminationPractitionerService examPractService;

	public ExaminationPractitionerController(ExaminationPractitionerService examPractService) {
		super();
		this.examPractService = examPractService;
	}
	
	@PostMapping()
	public ResponseEntity<Object> save(@Valid @RequestBody ExaminationPractitionerDto examPractDto) {
		return ResponseEntity.status(HttpStatus.OK).body(examPractService.save(examPractDto));
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<ExaminationPractitionerDto> examination = examPractService.findById(id);
		if (examination.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(examination.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ExaminationPractitioner id is invalid!");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityExistsException {
		examPractService.delete(id);
		return ResponseEntity.ok("Deleted examination-practitioner!");
	}
	
	@GetMapping("find/{id}")
	public List<ExaminationPractitionerDto> findByExamination(@PathVariable Long id){
		return examPractService.findByExamination(id);
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
