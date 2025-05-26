package deakin.example.studentlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import deakin.example.studentlearningapp.databinding.ActivityCheckoutBinding;

import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckoutActivity extends AppCompatActivity {
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private CheckoutViewModel model;
    private ActivityCheckoutBinding binding;
    private View googlePayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(CheckoutViewModel.class);
        model.canUseGooglePay.observe(this, this::setGooglePayAvailable);

        googlePayButton = binding.googlePayButton.getRoot();
        googlePayButton.setOnClickListener(this::requestPayment);
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this,
                    "Google Pay unavailable on this device",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void requestPayment(View view) {
        view.setClickable(false);
        long dummyPriceCents   = 100;
        long shippingCents     = 900;
        long totalPriceCents   = dummyPriceCents + shippingCents;

        Task<PaymentData> task = model.getLoadPaymentDataTask(totalPriceCents);
        AutoResolveHelper.resolveTask(task, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    PaymentData paymentData = PaymentData.getFromIntent(data);
                    handlePaymentSuccess(paymentData);
                    break;
                case RESULT_CANCELED:
                    // user canceled
                    break;
                case AutoResolveHelper.RESULT_ERROR:
                    Status status = AutoResolveHelper.getStatusFromIntent(data);
                    handleError(status);
                    break;
            }
            googlePayButton.setClickable(true);
        }
    }

    private void handlePaymentSuccess(@Nullable PaymentData paymentData) {
        if (paymentData == null) return;
        String paymentInfo = paymentData.toJson();
        try {
            JSONObject methodData = new JSONObject(paymentInfo)
                    .getJSONObject("paymentMethodData");
            JSONObject tokenData  = methodData.getJSONObject("tokenizationData");
            String token          = tokenData.getString("token");
            String billingName = methodData
                    .getJSONObject("info")
                    .getJSONObject("billingAddress")
                    .getString("name");

            Toast.makeText(this,
                    "Thanks, " + billingName + "!",
                    Toast.LENGTH_LONG).show();
            Log.d("GooglePay", "Token: " + token);

            // TODO: unlock your premium features here

        } catch (JSONException e) {
            throw new RuntimeException("Payment parsing error", e);
        }
    }

    private void handleError(@Nullable Status status) {
        String msg = (status != null)
                ? String.format(Locale.getDefault(),
                "Error code: %d", status.getStatusCode())
                : "Unknown error";
        Log.e("GooglePay", msg);
    }
}
