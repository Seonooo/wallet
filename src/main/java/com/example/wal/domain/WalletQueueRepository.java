package com.example.wal.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletQueueRepository extends JpaRepository<WalletQueue, Long>, QuerydslPredicateExecutor<WalletQueue>, WalletQueueCustomRepository {


}
