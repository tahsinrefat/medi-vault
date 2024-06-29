package com.tahsin.medi_vault.dto.prescription;

import com.tahsin.medi_vault.enums.Status;

public record PrescriptionResponse(
        Status status,
        String message
) {
}
