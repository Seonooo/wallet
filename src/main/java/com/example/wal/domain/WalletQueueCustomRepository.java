package com.example.wal.domain;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletQueueCustomRepository {

    List<WalletQueue> findWalletQueue100(long walletQueueId);
}
