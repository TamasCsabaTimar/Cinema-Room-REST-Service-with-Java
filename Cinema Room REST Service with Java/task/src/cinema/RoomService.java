package cinema;

import org.apache.el.parser.Token;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RoomService {
    private final static int ROOM_SIZE = 9;
    private final boolean[][] purchased = new boolean[ROOM_SIZE][ROOM_SIZE];
    private final Map<String, Seat> tokens = new HashMap<>();

    public int getPrice(int row) {
        return row<=4 ? 10 : 8;
    }

    public RoomResponse getRoom() {
        RoomResponse roomResponse = new RoomResponse(ROOM_SIZE, ROOM_SIZE);
        roomResponse.tickets = new ArrayList<>();
        for (int i = 0; i < roomResponse.getRows(); i++) {
            for (int j = 0; j < roomResponse.getColumns(); j++) {
                roomResponse.getSeats().add(new Ticket(i+1, j+1, getPrice(i+1)));
            }
        }
        return roomResponse;
    }
    public PriceToken ticketPurchase(int row, int column) {
        if(row<1 || row>ROOM_SIZE || column<1 || column>ROOM_SIZE) {
            throw new IllegalArgumentException("The number of a row or a column is out of bounds!");
        }
        if(purchased[row-1][column-1]) {
            throw new IllegalArgumentException("The ticket has been already purchased!");
        }

        purchased[row-1][column-1] = true;
        String token = UUID.randomUUID().toString();
        tokens.put(token, new Seat(row,column));

        return new PriceToken(getPrice(row), token);
    }

    public Ticket ticketReturn(String token){
        Seat seat = tokens.get(token);
        if(seat == null) {
            throw new IllegalArgumentException("Wrong token!");
        }
        tokens.remove(token);
        purchased[seat.getRow()-1][seat.getColumn()-1] = false;
        return new Ticket(seat.getRow(), seat.getColumn(), getPrice(seat.getRow()));
    }

    public StatsResponse getStats(String password) {
        if(!"super_secret".equals(password)) {
            throw new IllegalArgumentException("The password is wrong!");
        }
        int income = 0;
        int purch = 0;
        for (int i = 0; i < ROOM_SIZE; i++) {
            for (int j = 0; j < ROOM_SIZE; j++) {
                if(purchased[i][j]) {
                    purch++;
                    income += getPrice(i+1);
                }
            }
        }
        return new StatsResponse(income, ROOM_SIZE*ROOM_SIZE-purch, purch);
    }
}
