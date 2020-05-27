package Domain;

import java.sql.Timestamp;

public class Payment {
    public int paymentID;
    public int orderID;
    public Timestamp paymentDate;
    public boolean paymentStatus;
    public int paymentInvoiceTotal;

    public Payment(int paymentId, int orderId, Timestamp paymentDt, boolean paymentStat, int paymentInvTotal) {
        paymentID = paymentId;
        orderID = orderId;
        paymentDate = paymentDt;
        paymentStatus = paymentStat;
        paymentInvoiceTotal = paymentInvTotal;
    }

}
