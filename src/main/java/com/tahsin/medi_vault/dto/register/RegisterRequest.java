package com.tahsin.medi_vault.dto.register;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {}
