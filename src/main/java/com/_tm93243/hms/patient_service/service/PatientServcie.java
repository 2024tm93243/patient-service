package com._tm93243.hms.patient_service.service;


import com._tm93243.hms.patient_service.dto.PatientRequest;
import com._tm93243.hms.patient_service.dto.PatientResponse;
import com._tm93243.hms.patient_service.model.Patient;
import com._tm93243.hms.patient_service.respository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServcie {
    private final PatientRepository patientRepository;
    public PatientServcie(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    public PatientResponse createPatient(PatientRequest patientRequest) {
        Patient patient = new Patient();
        updatePatientDetailsFromRequest(patient, patientRequest);
        Patient savedPatient = patientRepository.save(patient);
        return mapToPatientResponse(savedPatient);
    }

    private PatientResponse mapToPatientResponse(Patient savedPatient) {
        PatientResponse response = new PatientResponse();
        response.setName(savedPatient.getName());
        response.setEmail(savedPatient.getEmail());
        response.setPhone(savedPatient.getPhone());
        response.setDob(savedPatient.getDob());
        response.setStatus(savedPatient.getStatus());
        return response;
    }

    private void updatePatientDetailsFromRequest(Patient patient, PatientRequest patientRequest) {
        patient.setName(patientRequest.getName());
        patient.setEmail(patientRequest.getEmail());
        patient.setPhone(patientRequest.getPhone());
        patient.setDob(patientRequest.getDob());
        patient.setStatus(patientRequest.getStatus());
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public PatientResponse updatePatient(Long id, PatientRequest patientRequest) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        updatePatientDetailsFromRequest(patient, patientRequest);
        Patient updatedPatient = patientRepository.save(patient);
        return mapToPatientResponse(updatedPatient);
    }

    public void deletePatient(Long id) {
        if(!patientRepository.existsById(id)){
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    public List<Patient> searchByNameOrPhone(String name, String phone) {
        if (name != null && !name.isEmpty() && phone != null && !phone.isEmpty()) {
            // Search by both name and phone
            return patientRepository.findByNameContainingIgnoreCase(name)
                    .stream()
                    .filter(patient -> patient.getPhone().equals(phone))
                    .toList();
        } else if (name != null && !name.isEmpty()) {
            // Search by name only
            return patientRepository.findByNameContainingIgnoreCase(name);
        } else if (phone != null && !phone.isEmpty()) {
            // Search by phone only
            return patientRepository.findByPhone(phone);
        } else {
            // Return an empty list if no criteria are provided
            return List.of();
        }
    }
}
