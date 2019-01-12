package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Payment;
import de.othr.cryptopal.exception.PaymentException;
import de.othr.cryptopal.service.dto.PaymentDTO;

import javax.enterprise.context.SessionScoped;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@WebService
@SessionScoped
public class PaymentService extends TransactionService<Payment> {

    @WebMethod
    @Transactional
    public PaymentDTO pay(@WebParam(name = "senderEmail") String senderEmail,
                          @WebParam(name = "senderPassword") String senderPassword,
                          @WebParam(name = "receiverEmail") String receiverEmail,
                          @WebParam(name = "amount") BigDecimal amount,
                          @WebParam(name = "currencyId") String currencyCode,
                          @WebParam(name = "paymentComment") String comment,
                          @WebParam(name = "taxInPercentage") double tax) throws PaymentException {

        Account sender = accountService.getAccountByCredintials(senderEmail, senderPassword);

        if(sender == null) {
            // Return account not found
            System.out.println("Sender not found");
            throw new PaymentException("No sender with email and password combination found");
        }

        Account receiver = accountService.getAccountByEmail(receiverEmail);

        if(receiver == null) {
            System.out.println("Receiver not found");
            throw new PaymentException("No receiver with email found");
        }

        // Check currency
        Currency paymentCurrency = currencyInformationService.getCurrencyFromMap(currencyCode);

        if(paymentCurrency == null) {
            System.out.println("Currency not found");
            throw new PaymentException("No currency with id " + currencyCode + " found");
        }

        // Check sender wallet
        if(sender.getWalletByCurrency(paymentCurrency) == null) {
            System.out.print("Sender has no currency wallet");
            throw new PaymentException("Sender has no wallet with currency " + currencyCode);
        }

        // Check amount
        BigDecimal newCredit = sender.getWalletByCurrency(paymentCurrency).getCredit().subtract(amount);
        if(newCredit.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Not enough credit");
            throw new PaymentException("Not enough credit to pay");
        }

        // TODO tax and paymentFee
        Payment payment = new Payment(sender.getWalletByCurrency(paymentCurrency), receiver.getWalletByCurrency(paymentCurrency),
                amount, paymentCurrency, new Date(System.currentTimeMillis()), comment, 0, 0);

        executeTransaction(payment, receiver);

        // Assign newly created wallet if necessary
        if(payment.getReceiverWallet() == null) {
            payment.setReceiverWallet(receiver.getWalletByCurrency(paymentCurrency));
        }

        return new PaymentDTO(payment);
    }
}