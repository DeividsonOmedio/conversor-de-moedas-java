package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD";

    public void run() {
        try {
            JsonObject exchangeRates = getExchangeRates();
            JsonObject rates = exchangeRates.getAsJsonObject("rates");

            System.out.println("Escolha uma moeda para conversão:");
            System.out.println("1. EUR");
            System.out.println("2. GBP");
            System.out.println("3. JPY");
            System.out.println("4. AUD");
            System.out.println("5. CAD");
            System.out.println("6. CHF");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            String currency = "";
            switch (choice) {
                case 1:
                    currency = "EUR";
                    break;
                case 2:
                    currency = "GBP";
                    break;
                case 3:
                    currency = "JPY";
                    break;
                case 4:
                    currency = "AUD";
                    break;
                case 5:
                    currency = "CAD";
                    break;
                case 6:
                    currency = "CHF";
                    break;
                default:
                    System.out.println("Escolha inválida");
                    return;
            }

            double rate = rates.get(currency).getAsDouble();
            System.out.println("A taxa de câmbio de USD para " + currency + " é " + rate);

            System.out.println("Digite o valor em USD:");
            double usdAmount = scanner.nextDouble();
            double convertedAmount = usdAmount * rate;

            System.out.println(usdAmount + " USD é equivalente a " + convertedAmount + " " + currency);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JsonObject getExchangeRates() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return JsonParser.parseString(content.toString()).getAsJsonObject();
    }
}
