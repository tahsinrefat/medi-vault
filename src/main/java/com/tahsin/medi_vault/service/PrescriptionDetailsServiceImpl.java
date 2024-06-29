package com.tahsin.medi_vault.service;

import com.tahsin.medi_vault.dto.prescription.PrescriptionDetailsResponse;
import com.tahsin.medi_vault.dto.prescription.PrescriptionRequest;
import com.tahsin.medi_vault.dto.prescription.PrescriptionResponse;
import com.tahsin.medi_vault.entity.PrescriptionDetails;
import com.tahsin.medi_vault.enums.Status;
import com.tahsin.medi_vault.repository.PrescriptionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PrescriptionDetailsServiceImpl implements PrescriptionDetailsService {
    private final PrescriptionDetailsRepository prescriptionDetailsRepository;
    private final UserService userService;

    @Override
    public PrescriptionResponse createPrescription(PrescriptionRequest prescriptionRequest) {
        PrescriptionDetails prescriptionDetails = PrescriptionDetails
                .builder()
                .prescriptionDate(prescriptionRequest.prescriptionDate())
                .patientName(prescriptionRequest.patientName())
                .patientAge(prescriptionRequest.patientAge())
                .patientGender(prescriptionRequest.patientGender())
                .diagnosis(prescriptionRequest.diagnosis())
                .medicine(prescriptionRequest.medicine())
                .nextVisitDate(prescriptionRequest.nextVisitDate())
                .user(userService.getCurrentUser())
                .build();

        prescriptionDetailsRepository.save(prescriptionDetails);

        return new PrescriptionResponse(
                Status.SUCCESSFUL,
                "Prescription created successfully"
        );
    }

    @Override
    public PrescriptionResponse updatePrescription(Long prescriptionId, PrescriptionRequest prescriptionRequest) {
        PrescriptionDetails prevPrescriptionDetails = prescriptionDetailsRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException(STR."Prescription not found with id: \{prescriptionId}"));

        if (prevPrescriptionDetails.getUser() != userService.getCurrentUser()) {
            throw new RuntimeException("You are trying to access a prescription belonging to a different user");
        }

        PrescriptionDetails prescriptionDetails = PrescriptionDetails
                .builder()
                .id(prevPrescriptionDetails.getId())
                .prescriptionDate(prescriptionRequest.prescriptionDate())
                .patientName(prescriptionRequest.patientName())
                .patientAge(prescriptionRequest.patientAge())
                .patientGender(prescriptionRequest.patientGender())
                .diagnosis(prescriptionRequest.diagnosis())
                .medicine(prescriptionRequest.medicine())
                .nextVisitDate(prescriptionRequest.nextVisitDate())
                .user(prevPrescriptionDetails.getUser())
                .build();

        prescriptionDetailsRepository.save(prescriptionDetails);

        return new PrescriptionResponse(
                Status.SUCCESSFUL,
                "Prescription updated successfully");
    }

    @Override
    public PrescriptionResponse deletePrescription(Long prescriptionId) {
        PrescriptionDetails prevPrescriptionDetails = prescriptionDetailsRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException(STR."Prescription not found with id: \{prescriptionId}"));

        if (prevPrescriptionDetails.getUser() != userService.getCurrentUser()) {
            throw new RuntimeException("You are trying to access a prescription belonging to a different user");
        }

        prescriptionDetailsRepository.deleteById(prescriptionId);

        return new PrescriptionResponse(
                Status.SUCCESSFUL,
                "Prescription deleted successfully");
    }

    @Override
    public Page<PrescriptionDetailsResponse> getPaginatedPrescription(Pageable pageable, Date fromDate, Date toDate) {
        if (Objects.isNull(fromDate) && Objects.isNull(toDate)) {
            Page<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findAllByCurrentMonth(new Date(), pageable, userService.getCurrentUser());
            return prescriptionDetailsList.map(prescriptionDetails -> new PrescriptionDetailsResponse(
                    prescriptionDetails.getPrescriptionDate(),
                    prescriptionDetails.getPatientName(),
                    prescriptionDetails.getPatientAge(),
                    prescriptionDetails.getPatientGender(),
                    prescriptionDetails.getDiagnosis(),
                    prescriptionDetails.getMedicine(),
                    prescriptionDetails.getNextVisitDate()
            ));
        } else {
            Page<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findAllByPrescriptionDateRange(fromDate, toDate, pageable, userService.getCurrentUser());
            return prescriptionDetailsList.map(prescriptionDetails -> new PrescriptionDetailsResponse(
                    prescriptionDetails.getPrescriptionDate(),
                    prescriptionDetails.getPatientName(),
                    prescriptionDetails.getPatientAge(),
                    prescriptionDetails.getPatientGender(),
                    prescriptionDetails.getDiagnosis(),
                    prescriptionDetails.getMedicine(),
                    prescriptionDetails.getNextVisitDate()
            ));
        }
    }
}
