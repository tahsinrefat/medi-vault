package com.tahsin.medi_vault.repository;

import com.tahsin.medi_vault.entity.PrescriptionDetails;
import com.tahsin.medi_vault.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PrescriptionDetailsRepository extends JpaRepository<PrescriptionDetails, Long> {
    @Query("SELECT p FROM PrescriptionDetails p WHERE MONTH(p.prescriptionDate) = MONTH(:currentDate) AND YEAR(p.prescriptionDate) = YEAR(:currentDate) AND p.user = :user")
    Page<PrescriptionDetails> findAllByCurrentMonth(@Param("currentDate") Date currentDate, Pageable pageable,@Param("user") User user);

    @Query("SELECT p FROM PrescriptionDetails p WHERE p.prescriptionDate BETWEEN :fromDate AND :toDate AND p.user = :user")
    Page<PrescriptionDetails> findAllByPrescriptionDateRange(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable, User user);
}
