package com.tahsin.medi_vault.dto.prescription;

import com.tahsin.medi_vault.enums.Gender;

import java.util.Date;

public record PrescriptionRequest(
        Date prescriptionDate,
        String patientName,
        Long patientAge,
        Gender patientGender,
        String diagnosis,
        String medicine,
        Date nextVisitDate
) {
}
