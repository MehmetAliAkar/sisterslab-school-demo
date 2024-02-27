package com.sisterslab.sisterslabschooldemo.service;

import com.sisterslab.sisterslabschooldemo.converter.SchoolConverter;
import com.sisterslab.sisterslabschooldemo.dto.request.SchoolRequest;
import com.sisterslab.sisterslabschooldemo.dto.response.SchoolCreateResponse;
import com.sisterslab.sisterslabschooldemo.exception.SchoolAlreadyExistsException;
import com.sisterslab.sisterslabschooldemo.exception.SchoolNotFoundException;
import com.sisterslab.sisterslabschooldemo.model.School;
import com.sisterslab.sisterslabschooldemo.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolCreateResponse createSchool(SchoolRequest request) {
        Optional<School> schoolByName = schoolRepository.findBySchoolName(request.getSchoolName());
        if (schoolByName.isPresent()){
            throw new SchoolAlreadyExistsException("School name already exists with this name:"
                    + request.getSchoolName());
        }
        return SchoolConverter.convertToSchoolCreateResponse(
                schoolRepository.save(SchoolConverter.convertToSchool(request)));
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }

    public SchoolCreateResponse getSchoolById(Long id) {
        return SchoolConverter.convertToSchoolCreateResponse(findById(id));
    }

    private School findById(Long id){
        return schoolRepository.findById(id)
                .orElseThrow(()->new SchoolNotFoundException("School Not Found!"+id));
    }


    public void updateSchool(Long id, SchoolRequest request) {
        School oldSchool = findById(id);
        oldSchool.setSchoolName(request.getSchoolName());
        schoolRepository.save(oldSchool);
    }

    public List<School> getSchool(String name) {
        if (name == null){
            return schoolRepository.findAll();
        }
        else{
            return schoolRepository.findAllBySchoolName(name);
        }
    }
}
