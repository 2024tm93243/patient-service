package com._tm93243.hms.patient_service.controller;

import com._tm93243.hms.patient_service.dto.PatientRequest;
import com._tm93243.hms.patient_service.dto.PatientResponse;
import com._tm93243.hms.patient_service.model.Patient;
import com._tm93243.hms.patient_service.service.PatientServcie;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/patients")
public class PatientController {

    @Autowired
    private PatientServcie patientServcie;

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest patientRequest) {
        PatientResponse createdPatient = patientServcie.createPatient(patientRequest);
        return new ResponseEntity<PatientResponse>(createdPatient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientServcie.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
//Update patient details by id
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @RequestBody PatientRequest patientRequest) {
      PatientResponse updatedPatient = patientServcie.updatePatient(id, patientRequest);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }
    //delete patient by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientServcie.deletePatient(id);
        String message = id+" is deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    //  Search by Name or Phone
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchByNameOrPhone(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String phone) {
        List<Patient> patients = patientServcie.searchByNameOrPhone(name, phone);
        return ResponseEntity.ok(patients);
    }

}
