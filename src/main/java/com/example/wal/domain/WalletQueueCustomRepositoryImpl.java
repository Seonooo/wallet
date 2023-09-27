package com.example.wal.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.wal.domain.QWallet.*;
import static com.example.wal.domain.QWalletQueue.*;

// 클래스명 바꿀것 생각해보기
public class WalletQueueCustomRepositoryImpl implements WalletQueueCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final long limitSize = 100L;

    public WalletQueueCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<WalletQueue> findWalletQueue100(long walletQueueId) {
        return jpaQueryFactory.selectFrom(walletQueue).
                innerJoin(walletQueue.wallet, wallet)
                .fetchJoin()
                .where(walletQueue.id.gt(walletQueueId))
                .limit(limitSize)
                .fetch();
    }
}
