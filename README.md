# 🚌 Bus Boarding Sequence Generator

A simple web-based application that processes bus seat bookings from Excel files, validates inputs, and generates an optimized boarding sequence. 
This tool is ideal for transport operators who want to ensure a smooth and logical passenger boarding experience.

---

## ✨ Features

- 📥 Upload `.xlsx` Excel files with Booking IDs and Seats.
- ✅ Validates seat format and allowed seat rows (`A`, `B`, `c`, `d` only).
- 🚫 Prevents:
  - Duplicate Booking IDs
  - Duplicate seat assignments
  - More than 20 seats per side (Left: `A/B`, Right: `c/d`)
- ⚠️ Provides clear validation errors on the UI.
- 📤 Downloads a clean text file with the boarding sequence in order from back to front.
  - **Left side** (A20/B20 → A1/B1)
  - **Right side** (c20/d20 → c1/d1)

---

## 📁 File Format

Your Excel file must have the following structure:

| Booking ID | Seat     |
|------------|----------|
| 101        | A1       |
| 102        | B3       |
| 103        | c15,d5   |
