package com.example.wal.application;

import com.example.wal.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService {

    private final WalletQueueRepository walletQueueRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public void update(WalletQueue walletQueue) {
        Wallet wallet = getWallet(walletQueue.getWallet().getId());
        wallet.update(walletQueue.getBalances());
        deleteWalletQueue(walletQueue.getId());
    }

    private Wallet getWallet(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(RuntimeException::new);
    }

    private void deleteWalletQueue(Long queueId) {
        walletQueueRepository.deleteById(queueId);
    }
}
