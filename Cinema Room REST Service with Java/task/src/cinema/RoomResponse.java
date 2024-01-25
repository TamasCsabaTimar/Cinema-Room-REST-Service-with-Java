package cinema;

import java.util.List;

public class RoomResponse {
    int rows;
    int columns;
    List<Ticket> tickets;

    public RoomResponse() {
    }

    public RoomResponse(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Ticket> getSeats() {
        return tickets;
    }

    public void setSeats(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
