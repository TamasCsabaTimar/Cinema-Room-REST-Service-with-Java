package cinema;

public class ReturnResponse {
    private Ticket ticket;

    public ReturnResponse() {
    }

    public ReturnResponse(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
