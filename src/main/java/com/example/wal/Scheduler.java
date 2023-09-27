package com.example.wal;

import com.example.wal.application.WalletService;
import com.example.wal.domain.Wallet;
import com.example.wal.domain.WalletQueueRepository;
import com.example.wal.domain.WalletQueue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final WalletQueueRepository walletQueueRepository;
    private final WalletService walletService;

    private static long currentWalletQueueId = 0L;
    private final static int TreadPoolSize = 10;

    @Scheduled(fixedRate = 100)
    public void scheduleFixedRateTask() {
        startLog();
        ExecutorService executorService = Executors.newFixedThreadPool(TreadPoolSize);

        List<WalletQueue> walletQueues = walletQueueRepository.findWalletQueue100(currentWalletQueueId);
        if(!walletQueues.isEmpty()){
            currentWalletQueueId = walletQueues.get(walletQueues.size() - 1).getId();

            CompletableFuture.runAsync(() -> {
                        walletQueues.parallelStream().forEach(walletService::update);
                    }, executorService)
                    .exceptionally((e) -> {
                        log.debug("runAsync Exception : {}", e.getMessage());
                        return null;
                    });
        }
        executorService.shutdown();
        endLog();
    }

    private void startLog() {
        log.info("Fixed rate task start");
    }

    private void endLog() {
        log.info("Fixed rate task end");
    }
}
