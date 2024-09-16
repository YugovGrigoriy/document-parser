package com.example.repo;

import com.example.file.dto.PaymentEntryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<PaymentEntryDto, Long> {
    @Query(value = "INSERT INTO payment_table_error (name) VALUES (:payment)", nativeQuery = true)
    void insertExample(@Param("payment") String name);

}
