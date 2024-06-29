package com.tahsin.medi_vault.dto.register;

import com.tahsin.medi_vault.enums.Status;

public record RegisterResponse (
        Status status,
        String message
) {}
