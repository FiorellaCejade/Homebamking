package com.mindhub.homebaking.repositories;

import com.mindhub.homebaking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.Set;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {



}
