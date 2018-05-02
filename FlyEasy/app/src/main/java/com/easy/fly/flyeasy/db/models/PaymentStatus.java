package com.easy.fly.flyeasy.db.models;

public class PaymentStatus {

    private boolean confirmed;

    private boolean paymentStatusFromFlightBook;

    public boolean isConfirmed() {
        return this.confirmed;
    }

    public static void setConfirmed(PaymentStatus paymentStatus, boolean confirmed) {
        paymentStatus.confirmed = confirmed;
    }

    public boolean isPaymentStatusFromFlightBook() {
        return this.paymentStatusFromFlightBook;
    }

    public static void setPaymentStatusFromFlightBook(PaymentStatus paymentStatus, boolean paymentStatusFromFlightBook) {
      paymentStatus.paymentStatusFromFlightBook = paymentStatusFromFlightBook;
    }
}
