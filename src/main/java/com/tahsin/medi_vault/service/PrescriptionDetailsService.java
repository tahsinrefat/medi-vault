package com.tahsin.medi_vault.service;

import com.tahsin.medi_vault.dto.prescription.PrescriptionDetailsResponse;
import com.tahsin.medi_vault.dto.prescription.PrescriptionRequest;
import com.tahsin.medi_vault.dto.prescription.PrescriptionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface PrescriptionDetailsService {
    PrescriptionResponse createPrescription(PrescriptionRequest prescriptionRequest);

    PrescriptionResponse updatePrescription(Long prescriptionId, PrescriptionRequest prescriptionRequest);

    PrescriptionResponse deletePrescription(Long prescriptionId);

    Page<PrescriptionDetailsResponse> getPaginatedPrescription(Pageable pageable, Date fromDate, Date toDate);
}
