package com._tm93243.hms.patient_service.respository;

import com._tm93243.hms.patient_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNameContainingIgnoreCase(String name);
    List<Patient> findByPhone(String phone);
}
