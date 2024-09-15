package com.example.repo;

import com.example.file.dto.PaymentEntryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<PaymentEntryDto, Long> {

}
