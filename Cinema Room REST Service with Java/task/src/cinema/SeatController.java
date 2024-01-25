package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class SeatController {

    private final RoomService roomService;

    public SeatController(RoomService roomService) {
        this.roomService = roomService;
    }

    RoomResponse generateRoom() {
        RoomResponse roomResponse = new RoomResponse(9,9);
        roomResponse.tickets = new ArrayList<>();
        for (int i = 0; i < roomResponse.getRows(); i++) {
            for (int j = 0; j < roomResponse.getColumns(); j++) {
                roomResponse.getSeats().add(new Ticket(i+1, j+1));
            }
        }
        return roomResponse;
    }

    @GetMapping("seats")
    public RoomResponse getRoom() {
        return roomService.getRoom();
    }

    @PostMapping("purchase")
    public ResponseEntity<?> postPurchase(@RequestBody PurchaseRequest request) {
        try {
            PriceToken priceToken = roomService.ticketPurchase(
                    request.getRow(), request.getColumn());
            return ResponseEntity.ok(new PurchaseResponse(
                    priceToken.getToken(),
                    new Ticket(request.getRow(), request.getColumn(), priceToken.getPrice())
            ));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("return")
    public ResponseEntity<?> postReturn(@RequestBody ReturnRequest request) {
        try {
            Ticket ticket = roomService.ticketReturn(request.getToken());
            return ResponseEntity.ok(new ReturnResponse(ticket));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("stats")
    public ResponseEntity<?> getStats(@RequestParam(value = "password", required = false) String password) {
        try {
            return ResponseEntity.ok(roomService.getStats(password));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorResponse(e.getMessage()));
        }
    }
}