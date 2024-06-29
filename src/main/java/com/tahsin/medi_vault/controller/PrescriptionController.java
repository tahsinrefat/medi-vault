package com.tahsin.medi_vault.controller;

import com.tahsin.medi_vault.dto.prescription.PrescriptionDetailsResponse;
import com.tahsin.medi_vault.dto.prescription.PrescriptionRequest;
import com.tahsin.medi_vault.dto.prescription.PrescriptionResponse;
import com.tahsin.medi_vault.service.PrescriptionDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/prescription")
public class PrescriptionController {
    private final PrescriptionDetailsService prescriptionDetailsService;

    @PostMapping("/create")
    ResponseEntity<PrescriptionResponse> createPrescription(@RequestBody PrescriptionRequest prescriptionRequest) {
        return ResponseEntity.ok(prescriptionDetailsService.createPrescription(prescriptionRequest));
    }

    @PutMapping("/update/{prescriptionId}")
    ResponseEntity<PrescriptionResponse> updatePrescription(@PathVariable Long prescriptionId, @RequestBody PrescriptionRequest prescriptionRequest) {
        return ResponseEntity.ok(prescriptionDetailsService.updatePrescription(prescriptionId, prescriptionRequest));
    }

    @DeleteMapping("/delete/{prescriptionId}")
    ResponseEntity<PrescriptionResponse> deletePrescription(@PathVariable Long prescriptionId) {
        return ResponseEntity.ok(prescriptionDetailsService.deletePrescription(prescriptionId));
    }

    @GetMapping("/get")
    ResponseEntity<Page<PrescriptionDetailsResponse>> getPrescriptionDetails(@RequestParam(required = false) String fromDate,
                                                                             @RequestParam(required = false) String toDate,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDateValue = null;
        Date toDateValue = null;
        Pageable pageable = PageRequest.of(page, size);
        try {
            fromDateValue = dateFormat.parse(fromDate);
            toDateValue = dateFormat.parse(toDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(prescriptionDetailsService.getPaginatedPrescription(pageable, fromDateValue, toDateValue));
    }

}
