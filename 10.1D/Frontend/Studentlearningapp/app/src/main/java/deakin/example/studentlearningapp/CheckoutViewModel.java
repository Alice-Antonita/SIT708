package deakin.example.studentlearningapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.Wallet.WalletOptions;
import org.json.JSONObject;

public class CheckoutViewModel extends AndroidViewModel {
    private final PaymentsClient paymentsClient;
    public final MutableLiveData<Boolean> canUseGooglePay = new MutableLiveData<>();

    public CheckoutViewModel(@NonNull Application application) {
        super(application);
        WalletOptions options = new WalletOptions.Builder()
                .setEnvironment(Constants.PAYMENTS_ENVIRONMENT)
                .build();
        paymentsClient = Wallet.getPaymentsClient(application, options);

        JSONObject isReadyJson = PaymentsUtil.getIsReadyToPayRequest();
        if (isReadyJson != null) {
            Task<Boolean> readyTask = paymentsClient.isReadyToPay(
                    IsReadyToPayRequest.fromJson(isReadyJson.toString())
            );
            readyTask.addOnCompleteListener(task ->
                    canUseGooglePay.setValue(task.isSuccessful() && Boolean.TRUE.equals(task.getResult()))
            );
        }
    }

    public Task<PaymentData> getLoadPaymentDataTask(long priceCents) {
        JSONObject payDataJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        PaymentDataRequest request = PaymentDataRequest.fromJson(payDataJson.toString());
        return paymentsClient.loadPaymentData(request);
    }
}