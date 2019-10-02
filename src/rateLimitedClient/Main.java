package rateLimitedClient;

import java.io.IOException;

import org.apache.commons.cli.*;

public class Main {

    private static String serverAddress = "http://localhost:8080/?clientId=";
    private static int numberOfClients;
    private static Thread[] clientsThreads;
    private static int intervalMaxTimeInSeconds = 5;

    public static void main(String[] args) {

        try {
            setArguments(args);
            clientsThreads = new Thread[numberOfClients];
            showWelcomeMessage();
            startAllClients();
            waitForExit();
            terminateClients();
            System.exit(0);
        } catch (InterruptedException | IOException e) {
            System.out.printf("Error while trying to simulate clients: %s%n", e.getMessage());
        }
    }

    private static void setArguments(String[] args) {
        Options options = new Options();
        Option input = new Option("c", "clients", true, "number of clients to simulate");
        input.setRequired(true);
        options.addOption(input);

        input = new Option("a", "address", true, "server address (default value: http://localhost:8080/?clientId=)");
        input.setRequired(false);
        options.addOption(input);

        input = new Option("i", "interval", true, "interval maximum wait time in seconds");
        input.setRequired(false);
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            String clientsArg = cmd.getOptionValue("clients");
            numberOfClients = Integer.parseInt(clientsArg);

            String addressArg = cmd.getOptionValue("address");
            if (addressArg != null){
                serverAddress = addressArg;
            }

            String intervalArg = cmd.getOptionValue("interval");
            if (intervalArg != null){
                intervalMaxTimeInSeconds = Integer.parseInt(intervalArg);
            }
        } catch (Exception e) {
            if (e instanceof ParseException) {
                System.out.println(e.getMessage());
            }
            formatter.printHelp("rate limited clients simulator", options);
            System.exit(1);
        }
    }

    private static void waitForExit() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        System.out.println("Closing the program...");
    }

    private static void startAllClients() {
        for (int i = 0; i < numberOfClients; i++) {
            clientsThreads[i] = new Thread(new clientSimulator(Integer.toString(i + 1), serverAddress, intervalMaxTimeInSeconds));
            clientsThreads[i].start();
        }
    }

    private static void showWelcomeMessage() throws InterruptedException {
        System.out.printf("Simulating %d clients to %s with max wait time each of %d seconds.%n", numberOfClients, serverAddress, intervalMaxTimeInSeconds);
        System.out.println("Press any key to exit");
        System.out.print("Starting simulator in 3..");
        Thread.sleep(1000);
        System.out.print("2..");
        Thread.sleep(1000);
        System.out.println("1..");
        Thread.sleep(1000);
    }

    private static void terminateClients() throws InterruptedException {
        clientSimulator.stopAllClients();
        for (int i = 0; i < numberOfClients; i++) {
            clientsThreads[i].join();
        }
    }
}
