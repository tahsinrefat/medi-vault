package com.tahsin.medi_vault.dto.authentication;


import com.tahsin.medi_vault.enums.Status;

public record AuthenticationResponse(Status status, String token) {}
