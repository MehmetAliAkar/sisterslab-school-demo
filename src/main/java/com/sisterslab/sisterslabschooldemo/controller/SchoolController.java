package com.sisterslab.sisterslabschooldemo.controller;

import com.sisterslab.sisterslabschooldemo.dto.request.SchoolRequest;
import com.sisterslab.sisterslabschooldemo.dto.response.SchoolCreateResponse;
import com.sisterslab.sisterslabschooldemo.exception.SchoolAlreadyExistsException;
import com.sisterslab.sisterslabschooldemo.exception.SchoolNotFoundException;
import com.sisterslab.sisterslabschooldemo.repository.SchoolRepository;
import com.sisterslab.sisterslabschooldemo.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public SchoolCreateResponse createSchool(@RequestBody SchoolRequest request){
        return schoolService.createSchool(request);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable Long id){
        schoolService.deleteSchool(id);
    }

    @GetMapping("/{id}")
    public SchoolCreateResponse getSchoolById(@PathVariable Long id){
        return schoolService.getSchoolById(id);
    }

    @PostMapping("/{id}")
    public void updateSchool(@PathVariable Long id,@RequestBody SchoolRequest request){
        schoolService.updateSchool(id, request);
    }

    @ExceptionHandler(SchoolAlreadyExistsException.class)
    public ResponseEntity<String> handleSchoolAlreadyExistsException(SchoolAlreadyExistsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SchoolNotFoundException.class)
    public ResponseEntity<String> handleSchoolNotFoundException(SchoolAlreadyExistsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
