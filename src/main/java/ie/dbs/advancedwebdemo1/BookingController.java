
package ie.dbs.advancedwebdemo1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/booking")
    public String createBooking(
            @RequestParam int participants,
            @RequestParam double price) {

        double total =
                bookingService.calculateTotal(participants, price);

        return "Total: " + total;
    }
}
