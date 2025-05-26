package deakin.example.studentlearningapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentsUtil {
    private PaymentsUtil() {}

    public static JSONObject getBaseRequest() {
        try {
            JSONObject baseRequest = new JSONObject();
            baseRequest.put("apiVersion", 2);
            baseRequest.put("apiVersionMinor", 0);
            return baseRequest;
        } catch (JSONException e) {
            throw new RuntimeException("Failed to create base request", e);
        }
    }

    public static JSONObject getIsReadyToPayRequest() {
        try {
            JSONObject cardPaymentMethod = new JSONObject();
            cardPaymentMethod.put("type", "CARD");

            JSONObject parameters = new JSONObject();
            parameters.put("allowedAuthMethods", new JSONArray(Constants.SUPPORTED_METHODS));
            parameters.put("allowedCardNetworks", new JSONArray(Constants.SUPPORTED_NETWORKS));

            JSONObject billingAddressParameters = new JSONObject();
            billingAddressParameters.put("format", "FULL");
            parameters.put("billingAddressRequired", true);
            parameters.put("billingAddressParameters", billingAddressParameters);

            cardPaymentMethod.put("parameters", parameters);

            JSONObject isReadyToPayRequest = getBaseRequest();
            isReadyToPayRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(cardPaymentMethod)
            );
            return isReadyToPayRequest;
        } catch (JSONException e) {
            throw new RuntimeException("Failed to create isReadyToPayRequest", e);
        }
    }

    public static JSONObject getPaymentDataRequest(long priceCents) {
        try {
            String totalPrice = String.format("%.2f", priceCents / 100.0);
            JSONObject transactionInfo = new JSONObject();
            transactionInfo.put("totalPriceStatus", "FINAL");
            transactionInfo.put("totalPrice", totalPrice);
            transactionInfo.put("currencyCode", Constants.CURRENCY_CODE);

            JSONObject merchantInfo = new JSONObject();
            merchantInfo.put("merchantName", "Example Merchant");

            JSONObject cardPaymentMethod = new JSONObject();
            cardPaymentMethod.put("type", "CARD");
            JSONObject parameters = new JSONObject();
            parameters.put("allowedAuthMethods", new JSONArray(Constants.SUPPORTED_METHODS));
            parameters.put("allowedCardNetworks", new JSONArray(Constants.SUPPORTED_NETWORKS));
            parameters.put("billingAddressRequired", true);
            JSONObject billingParams = new JSONObject();
            billingParams.put("format", "FULL");
            parameters.put("billingAddressParameters", billingParams);

            cardPaymentMethod.put(
                    "parameters", parameters
            );

            // Tokenization spec
            JSONObject tokenizationSpec = new JSONObject();
            tokenizationSpec.put("type", "PAYMENT_GATEWAY");
            tokenizationSpec.put(
                    "parameters", new JSONObject(Constants.PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS)
            );
            cardPaymentMethod.put("tokenizationSpecification", tokenizationSpec);

            JSONObject paymentDataRequest = getBaseRequest();
            paymentDataRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(cardPaymentMethod)
            );
            paymentDataRequest.put("transactionInfo", transactionInfo);
            paymentDataRequest.put("merchantInfo", merchantInfo);
            return paymentDataRequest;
        } catch (JSONException e) {
            throw new RuntimeException("Failed to create paymentDataRequest", e);
        }
    }
}
