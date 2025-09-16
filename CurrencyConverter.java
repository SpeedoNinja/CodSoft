import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

class CurrencyConverter {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public double getRate(String base, String target) throws Exception {
        URL url = new URL(API_URL + base);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();
        JSONObject json = new JSONObject(sb.toString());
        JSONObject rates = json.getJSONObject("conversion_rates");
        return rates.getDouble(target);
    }

    public double convert(String base, String target, double amount) throws Exception {
        double rate = getRate(base, target);
        return amount * rate;
    }
}

public class CurrencyApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CurrencyConverter converter = new CurrencyConverter();

        System.out.print("Enter base currency code (e.g., USD): ");
        String base = sc.next().toUpperCase();

        System.out.print("Enter target currency code (e.g., EUR): ");
        String target = sc.next().toUpperCase();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        try {
            double result = converter.convert(base, target, amount);
            System.out.printf("Converted amount: %.2f %s%n", result, target);
        } catch (Exception e) {
            System.out.println("Error during conversion. Check currency codes or API key.");
        }

        sc.close();
    }
}
