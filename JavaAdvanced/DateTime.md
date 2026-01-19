# Java Date & Time API (java.time)

## 1. Why New Date-Time API?

Problems with old API:

* Mutable
* Not thread-safe
* Confusing methods
* Poor time-zone support

New API advantages:

* Immutable
* Thread-safe
* Clear separation of date, time, and zone
* Fluent and readable

---

## 2. Core Classes (Must-Know)

| Class               | Purpose                  |
| ------------------- | ------------------------ |
| `LocalDate`         | Date only (YYYY-MM-DD)   |
| `LocalTime`         | Time only (HH:mm:ss)     |
| `LocalDateTime`     | Date + Time              |
| `ZonedDateTime`     | Date + Time + Zone       |
| `Instant`           | Timestamp (machine time) |
| `Period`            | Date-based difference    |
| `Duration`          | Time-based difference    |
| `DateTimeFormatter` | Formatting & parsing     |

---

## 3. `LocalDate`

Represents **date without time**.

```java
LocalDate today = LocalDate.now();
LocalDate date = LocalDate.of(2026, 1, 18);
```

Common methods:

```java
today.getYear();
today.getMonth();
today.plusDays(5);
today.minusMonths(1);
```

---

## 4. `LocalTime`

Represents **time without date**.

```java
LocalTime now = LocalTime.now();
LocalTime time = LocalTime.of(10, 30);
```

Methods:

```java
now.getHour();
now.plusMinutes(15);
```

---

## 5. `LocalDateTime`

Represents **date and time without zone**.

```java
LocalDateTime dt = LocalDateTime.now();
LocalDateTime custom = LocalDateTime.of(2026, 1, 18, 10, 30);
```

---

## 6. `ZonedDateTime` (Time Zones)

```java
ZonedDateTime india = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
ZonedDateTime usa = ZonedDateTime.now(ZoneId.of("America/New_York"));
```

List zones:

```java
ZoneId.getAvailableZoneIds();
```

---

## 7. `Instant` (Machine Time)

Represents **epoch timestamp**.

```java
Instant now = Instant.now();
```

Used in:

* Logging
* Auditing
* Event timestamps

---

## 8. Parsing Dates

```java
LocalDate date = LocalDate.parse("2026-01-18");
```

Custom format:

```java
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
LocalDate date = LocalDate.parse("18-01-2026", fmt);
```

---

## 9. Formatting Dates

```java
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
String formatted = LocalDate.now().format(fmt);
```

---

## 10. Date Arithmetic (Immutability)

```java
LocalDate date = LocalDate.now();
LocalDate newDate = date.plusDays(10);
```

Original object remains unchanged.

---

## 11. `Period` (Date Difference)

Used for **years, months, days**.

```java
Period p = Period.between(startDate, endDate);
p.getDays();
```

---

## 12. `Duration` (Time Difference)

Used for **hours, minutes, seconds**.

```java
Duration d = Duration.between(startTime, endTime);
d.toMinutes();
```

---

## 13. Comparing Dates & Times

```java
date1.isAfter(date2);
date1.isBefore(date2);
date1.isEqual(date2);
```

---

## 14. Converting Old Date to New API

```java
Date oldDate = new Date();
Instant instant = oldDate.toInstant();
LocalDateTime ldt =
        instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
```

---

## 15. Common Mistakes

* Using `LocalDateTime` when timezone is required
* Modifying date objects (they are immutable)
* Using old `Date` instead of `java.time`

---

## 16. Interview Comparison (Old vs New)

| Feature     | Old API | New API |
| ----------- | ------- | ------- |
| Thread-safe | ❌       | ✔       |
| Immutable   | ❌       | ✔       |
| Clear API   | ❌       | ✔       |
| Time zones  | Weak    | Strong  |

---

## 17. When to Use What

| Scenario    | Class           |
| ----------- | --------------- |
| Date only   | `LocalDate`     |
| Time only   | `LocalTime`     |
| Date + Time | `LocalDateTime` |
| Timezone    | `ZonedDateTime` |
| Timestamp   | `Instant`       |

---

## 18. Interview One-Liners

* "`java.time` API is immutable and thread-safe."
* "`LocalDateTime` has no timezone."
* "`Instant` represents epoch time."
* "`DateTimeFormatter` is used for parsing and formatting."

---

## 19. Final Summary

> **Always prefer `java.time` over old Date/Calendar APIs for clean, safe, and modern date-time handling.**

---
