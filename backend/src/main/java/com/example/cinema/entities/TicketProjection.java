package com.example.cinema.entities;
    import org.springframework.data.rest.core.config.Projection;



@Projection(name = "ticketProj", types = Ticket.class)
public interface TicketProjection {
    public Long getId();
    public String getNomClient();
    public double getPrix();
    public Long getCodePayement();
    public boolean getReserved();
    public Place getPlace();
}