package dev.imran;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bus Boarding Sequence Generator");
        System.out.println("1. Console Version");
        System.out.println("2. Web Interface");
        System.out.print("Choose (1 or 2): ");

        String choice = scanner.nextLine().trim();

        if ("2".equals(choice)) {
            openWebInterface();
        } else {
            runConsoleVersion();
        }

        scanner.close();
    }

    private static void runConsoleVersion() {
        String inputFilePath = "input/bus_bookings.txt";
        String outputFilePath = "output/boarding_sequence.txt";

        BoardingService service = new BoardingService();
        List<Booking> bookings = service.readBookings(inputFilePath);

        if (bookings.isEmpty()) {
            System.out.println("No bookings found in " + inputFilePath);
            return;
        }

        List<Booking> sorted = service.sortBookings(bookings);
        service.writeBoardingSequence(sorted, outputFilePath);

        System.out.println("✅ Boarding sequence generated: " + outputFilePath);
        System.out.printf("%-5s %-10s%n", "Seq", "Booking_ID");

        int i = 1;
        for (Booking booking : sorted) {
            System.out.printf("%-5d %-10s%n", i++, booking.getBookingId());
        }
    }

    private static void openWebInterface() {
        try {
            File indexFile = new File("web/index.html");
            if (indexFile.exists()) {
                Desktop.getDesktop().browse(indexFile.toURI());
                System.out.println("🌐 Web interface opened in browser");
            } else {
                System.out.println("❗ Web files not found. Please create web folder with index.html.");
            }
        } catch (Exception e) {
            System.out.println("❗ Failed to open browser. Open web/index.html manually.");
        }
    }
}
