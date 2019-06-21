package btrade.payfort;

/**
 * Created by Unknown.
 */

public interface IPaymentRequestCallBack {
    void onPaymentRequestResponse(int responseType, PayFortData responseData);
}
