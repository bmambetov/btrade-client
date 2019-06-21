package btrade.payfort;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Config.BaseURL;
import btrade.tcc.AppController;
import btrade.tcc.OrderFail;
import btrade.tcc.R;
import btrade.tcc.ThanksOrder;
import util.ConnectivityReceiver;
import util.CustomVolleyJsonRequest;
import util.DatabaseHandler;
import util.Session_management;

import static com.android.volley.VolleyLog.TAG;

public class Payforpayment extends AppCompatActivity implements IPaymentRequestCallBack{
    private Session_management sessionManagement;
    RelativeLayout confirm;
    private DatabaseHandler db_cart;
    private String getlocation_id = "";
    private String getstore_id = "";
    private String getvalue = "";
    private String get_wallet_ammount = "";
    private String gettime = "";
    private String getdate = "";
    private String getuser_id = "";
    String text;
    String email,mobile,total_rs,name;
    private String user_id = "";
    public FortCallBackManager fortCallback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instamojo_payment);
        sessionManagement = new Session_management(Payforpayment.this);
         email = sessionManagement.getUserDetails().get(BaseURL.KEY_EMAIL);
         mobile = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
         name = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
         total_rs = getIntent().getStringExtra("total");
        getlocation_id = getIntent().getStringExtra("getlocationid");
        getstore_id = getIntent().getStringExtra("getstoreid");
        gettime = getIntent().getStringExtra("gettime");
        getvalue = getIntent().getStringExtra("getpaymentmethod");
        getdate = getIntent().getStringExtra("getdate");
        get_wallet_ammount = getIntent().getStringExtra("wallet_ammount");

        sessionManagement = new Session_management(Payforpayment.this);
        db_cart = new DatabaseHandler(Payforpayment.this);
        initilizePayFortSDK();
        requestForPayfortPayment();
    }


    private void initilizePayFortSDK() {
        fortCallback = FortCallback.Factory.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayFortPayment.RESPONSE_PURCHASE) {
            fortCallback.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void requestForPayfortPayment() {
        PayFortData payFortData = new PayFortData();

//            payFortData.amount = String.valueOf((int) (Float.parseFloat(total_rs) * 100));// Multiplying with 100, bcz amount should not be in decimal format
            payFortData.command = PayFortPayment.PURCHASE;
            payFortData.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData.customerEmail = email;
            payFortData.language = PayFortPayment.LANGUAGE_TYPE;
            payFortData.merchantReference = String.valueOf(System.currentTimeMillis());

            PayFortPayment payFortPayment = new PayFortPayment(this, this.fortCallback, this);
            payFortPayment.requestForPayment(payFortData);

    }

    @Override
    public void onPaymentRequestResponse(int responseType, final PayFortData responseData) {
        if (responseType == PayFortPayment.RESPONSE_GET_TOKEN) {
            Toast.makeText(this, "Token not generated", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Token not generated");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_CANCEL) {
            Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment cancelled");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_FAILURE) {
            Intent intent = new Intent(Payforpayment.this, OrderFail.class);
            startActivity(intent);
            finish();

            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment failed");
        } else {
            Intent intent = new Intent(Payforpayment.this, ThanksOrder.class);
            startActivity(intent);

            finish();
            attemptOrder();
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
             Log.e("onPaymentResponse", "Payment successful");
        }
    }


    private void attemptOrder() {
        ArrayList<HashMap<String, String>> items = db_cart.getCartAll();
        if (items.size() > 0) {
            JSONArray passArray = new JSONArray();
            for (int i = 0; i < items.size(); i++) {
                HashMap<String, String> map = items.get(i);
                JSONObject jObjP = new JSONObject();
                try {
                    jObjP.put("product_id", map.get("product_id"));
                    jObjP.put("qty", map.get("qty"));
                    jObjP.put("unit_value", map.get("unit_value"));
                    jObjP.put("unit", map.get("unit"));
                    jObjP.put("price", map.get("price"));
                    passArray.put(jObjP);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

            if (ConnectivityReceiver.isConnected()) {

                Log.e(TAG, "from:" + gettime + "\ndate:" + getdate +
                        "\n" + "\nuser_id:" + getuser_id + "\n" + getlocation_id + getstore_id + "\ndata:" + passArray.toString());

                makeAddOrderRequest(getdate, gettime, getuser_id, getlocation_id, getstore_id, passArray);
            }
        }
    }

    private void makeAddOrderRequest(String date, String gettime, String userid, String location, String store_id, JSONArray passArray) {
        String tag_json_obj = "json_add_order_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("date", date);
        params.put("time", gettime);
        params.put("user_id", userid);
        params.put("location", location);
        params.put("store_id", store_id);
        params.put("payment_method", getvalue);
        params.put("data", passArray.toString());
        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.ADD_ORDER_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {
                        db_cart.clearCart();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(Payforpayment.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


}