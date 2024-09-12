import java.time.Duration;
import java.time.Instant;

class Time {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Instant now = Instant.now();
        System.out.println(now);
        System.out.println(now.minus(Duration.ofDays(1)));
        System.out.println(java.time.ZonedDateTime.now());
        System.out.println(java.time.OffsetDateTime.now());
        System.out.println(java.time.LocalDateTime.now());

    }
}