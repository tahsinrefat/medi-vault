package com.tahsin.medi_vault.entity;

import com.tahsin.medi_vault.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "PRESCRIPTION_DETAILS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDetails {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRESCRIPTION_DATE", nullable = false)
    private Date prescriptionDate;

    @Column(name = "PATIENT_NAME", nullable = false)
    private String patientName;

    @Column(name = "PATIENT_AGE", nullable = false)
    private Long patientAge;

    @Column(name = "PATIENT_GENDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender patientGender;

    @Column(name = "DIAGNOSIS", nullable = false)
    private String diagnosis;

    @Column(name = "MEDICINE", nullable = false)
    private String medicine;

    @Column(name = "NEXT_VISIT_DATE")
    private Date nextVisitDate;

    @JoinColumn(name = "USER_INFO")
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
