package com.example.wal.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "wallet_queues")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class WalletQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Wallet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private BigDecimal balances;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public WalletQueue(Long id, Wallet wallet, BigDecimal balances, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.wallet = wallet;
        this.balances = balances;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateBalance(BigDecimal balance) {
        this.balances = this.balances.add(balance);
    }
}
