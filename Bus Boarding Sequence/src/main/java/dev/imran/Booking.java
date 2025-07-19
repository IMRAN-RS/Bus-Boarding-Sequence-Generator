package dev.imran;

public class Booking {
    private final String bookingId;
    private final int seat;

    public Booking(String bookingId, int seat) {
        this.bookingId = bookingId;
        this.seat = seat;
    }

    public String getBookingId() {
        return bookingId;
    }

    public int getSeat() {
        return seat;
    }
}
