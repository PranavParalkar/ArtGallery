package com.RESTAPI.ArtGalleryProject.service.PaymentService;

import com.RESTAPI.ArtGalleryProject.DTO.Payment.InitiatePaymentRequest;
import com.RESTAPI.ArtGalleryProject.Entity.Transaction;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.Enum.TransactionStatus;
import com.RESTAPI.ArtGalleryProject.repository.TransactionRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionRepo transactionRepo;
    private final WalletRepo walletRepo;
    private final UserRepo userRepo;

    @Override
    public String initiatePayment(InitiatePaymentRequest request) {
        Transaction txn = Transaction.builder()
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status(TransactionStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepo.save(txn);

        // Simulate Razorpay ID generation
        String paymentGatewayTxnId = "txn_" + System.currentTimeMillis();
        txn.setPaymentGatewayTxnId(paymentGatewayTxnId);
        transactionRepo.save(txn);

        return paymentGatewayTxnId;
    }

    @Override
    public void updateTransactionStatus(String paymentGatewayTxnId, boolean success) {
        Transaction txn = transactionRepo.findByPaymentGatewayTxnId(paymentGatewayTxnId);
        if (txn == null) throw new RuntimeException("Transaction not found");

        if (success) {
            txn.setStatus(TransactionStatus.SUCCESS);

            Wallet wallet = userRepo.findById(txn.getUserId())
                    .orElseThrow(() -> new RuntimeException("Wallet not found")).getWallet();

            wallet.setBalance(wallet.getBalance() + txn.getAmount());
            walletRepo.save(wallet);
        } else {
            txn.setStatus(TransactionStatus.FAILED);
        }

        transactionRepo.save(txn);
    }

    @Override
    public String confirmPayment(String paymentGatewayTxnId) {
        // You can implement a status check or polling confirmation here if needed
        return null;
    }
}
