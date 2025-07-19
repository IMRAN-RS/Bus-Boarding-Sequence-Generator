package dev.imran;

import java.util.Set;

public class Seat implements Comparable<Seat> {
    private static final Set<String> VALID_ROWS = Set.of("A", "B", "c", "d");

    private final String label;
    private final int seatValue;

    public Seat(String label) {
        this.label = label.trim();

        if (this.label.length() < 2) {
            throw new IllegalArgumentException("Invalid seat label: " + label);
        }

        String row = String.valueOf(this.label.charAt(0));
        String numberPart = this.label.substring(1);

        if (!VALID_ROWS.contains(row)) {
            throw new IllegalArgumentException("Invalid seat row: " + row);
        }

        int number;
        try {
            number = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid seat number in: " + label);
        }

        if (number < 1 || number > 20) {
            throw new IllegalArgumentException("Invalid seat number in: " + label);
        }

        this.seatValue = computeSeatValue(row, number);
    }

    private int computeSeatValue(String row, int number) {
        switch (row) {
            case "A": return number;
            case "B": return 100 + number;
            case "c": return 200 + number;
            case "d": return 300 + number;
            default: throw new IllegalArgumentException("Unknown row: " + row);
        }
    }

    public int getSeatValue() {
        return seatValue;
    }

    @Override
    public int compareTo(Seat other) {
        return Integer.compare(this.seatValue, other.seatValue);
    }
}
