package de.othr.cryptopal.entity.util;

import de.othr.cryptopal.entity.Currency;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import de.othr.cryptopal.util.CurrencyPropertiesUtil;

import java.util.List;

/**
 * A custom JSON deserializer
 */

public class JsonUtils {

    private static String ECB_RATES_STRING = "rates";

    private static String BASE_CURRENCY_STRING = CurrencyPropertiesUtil.getBaseCurrency();

    public static List<Currency> getFiatCurrenciesFromResponse(JSONObject jsonObject, List<Currency> currencies) {

        try {
            JSONObject rates = jsonObject.getJSONObject(ECB_RATES_STRING);

            for(Currency currency : currencies) {
                // Check if base currency
                if(!currency.getCurrencyId().equals(BASE_CURRENCY_STRING)) {
                    double exchangeRate = Double.parseDouble(rates.getString(currency.getCurrencyId()));

                    currency.setExchangeRate(exchangeRate);
                } else {
                    // Set base currency to 1.00
                    currency.setExchangeRate(1.00);
                }
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return currencies;
    }

    public static List<Currency> getCryptoCurrenciesFromResponse(JSONObject jsonObject, List<Currency> currencies) {

        try {
            for(Currency currency : currencies) {
                JSONObject cryptoCurrencyJsonObject = jsonObject.getJSONObject(currency.getCurrencyId());

                double exchangeRate = Double.parseDouble(cryptoCurrencyJsonObject.getString(BASE_CURRENCY_STRING));

                currency.setExchangeRate(1 / exchangeRate);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return currencies;
    }

}
