package gcs.projects.ticketmcs.model;

public enum TicketStatus {

    AVAILABLE("available"),
    SOLD("sold");

    private final String status;

    TicketStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
