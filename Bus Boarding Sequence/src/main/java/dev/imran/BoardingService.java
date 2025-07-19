package dev.imran;

import java.io.*;
import java.util.*;

public class BoardingService {

    private static final Set<String> VALID_SEATS = generateValidSeats();

    private static Set<String> generateValidSeats() {
        Set<String> seats = new HashSet<>();
        for (int i = 1; i <= 20; i++) {
            seats.add("A" + i);
            seats.add("B" + i);
            seats.add("c" + i);
            seats.add("d" + i);
        }
        return seats;
    }

    public List<Booking> readBookings(String filePath) {
        List<Booking> bookings = new ArrayList<>();
        Set<String> bookingIdSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 0;

            while ((line = br.readLine()) != null) {
                lineNum++;
                final String currentLine = line.trim();

                if (currentLine.isEmpty()) continue;

                String[] parts = currentLine.split("\\s+");
                if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
                    System.err.println("❌ Skipping invalid line #" + lineNum + ": \"" + currentLine + "\" (Missing Booking ID or Seat)");
                    continue;
                }

                String bookingId = parts[0];
                String[] seatLabels = parts[1].split(",");

                if (bookingIdSet.contains(bookingId)) {
                    System.out.println("⚠️ Duplicate Booking ID \"" + bookingId + "\" found at line #" + lineNum + ". Skipping this entry.");
                    continue;
                }

                try {
                    List<Seat> validSeats = new ArrayList<>();

                    for (String rawLabel : seatLabels) {
                        String label = rawLabel.trim();  // preserve case!
                        if (!VALID_SEATS.contains(label)) {
                            throw new IllegalArgumentException("Invalid seat label: " + rawLabel);
                        }
                        validSeats.add(new Seat(label));
                    }

                    int minSeatValue = validSeats.stream()
                            .min(Seat::compareTo)
                            .map(Seat::getSeatValue)
                            .orElseThrow(() -> new IllegalArgumentException("No valid seats in: " + currentLine));

                    bookings.add(new Booking(bookingId, minSeatValue));
                    bookingIdSet.add(bookingId);

                } catch (IllegalArgumentException e) {
                    System.err.println("❌ Error parsing seats in line #" + lineNum + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return bookings;
    }

    public List<Booking> sortBookings(List<Booking> bookings) {
        bookings.sort(Comparator.comparingInt(Booking::getSeat));
        return bookings;
    }

    public void writeBoardingSequence(List<Booking> sortedBookings, String outputPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(String.format("%-5s %-10s%n", "Seq", "Booking_ID"));
            int seq = 1;
            for (Booking b : sortedBookings) {
                writer.write(String.format("%-5d %-10s%n", seq++, b.getBookingId()));
            }
            System.out.println("✅ Boarding sequence written to: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
        }
    }
}
