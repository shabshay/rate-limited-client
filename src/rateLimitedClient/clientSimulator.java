package rateLimitedClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class clientSimulator implements Runnable {

    private static boolean running = true;
    private static Random random = new Random();
    private String uri;
    private String clientId;

    public clientSimulator(String clientId, String uri) {
        this.uri = uri;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        int i = 0;
        while (running) {
            i++;
            createNewRequest(i);
            doWait();
        }
    }

    private void doWait() {
        int seconds = random.nextInt(7);
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.out.println("Failed to wait random number of seconds");
        }
    }

    private void createNewRequest(int requestNumber) {
        String status = " - Success";
        try {
            sendRequest(uri + clientId);
        } catch (Exception e) {
            status = " - Failed (" + e.getMessage() + ")";
        }

        System.out.printf("Client[%s], Request #%d%s%n", clientId, requestNumber, status);
    }

    public static void stopAllClients(){
        running = false;
    }

    public static String sendRequest(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
