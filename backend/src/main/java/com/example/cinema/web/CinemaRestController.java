package com.example.cinema.web;

import com.example.cinema.dao.FilmRepository;
import com.example.cinema.dao.TicketRepository;
import com.example.cinema.entities.Film;
import com.example.cinema.entities.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws Exception {
        Film film = filmRepository.findById(id).get();
        String photoName = film.getPhoto();
        File file = new File("images/" + photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @GetMapping(path = "/index")
    public String index() {
        return "INDEX";
    }

    /*
     * @PostMapping("/payerTickets")
     * 
     * @Transactional public List<Ticket> payerTickets(@RequestBody TicketForm
     * ticketForm){ List<Ticket> listTickets = new ArrayList<>();
     * ticketForm.getTickets().forEach(idTicket->{ Ticket ticket =
     * ticketRepository.findById(idTicket).get();
     * ticket.setNomClient(ticketForm.getNomClient()); ticket.setReserve(true);
     * ticket.setCodePayement(ticketForm.getCodePayement());
     * ticketRepository.save(ticket); listTickets.add(ticket); }); return
     * listTickets; }
     */

    @PostMapping("/payerTickets")
    @Transactional
    public void payerTickets(@RequestBody TicketForm ticketFrom) {
        List<Ticket> listTickets = new ArrayList<>();
        System.out.println(ticketFrom);
        ticketFrom.getTickets().forEach(idTicket -> {
            Ticket ticket = ticketRepository.findById(idTicket).get();

            System.out.println(ticket);
            ticket.setNomClient(ticketFrom.getNomClient());
            ticket.setReserved(true);
            ticket.setCodePayement(ticketFrom.getCodePayement());

            System.out.println(ticket);
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
    }

    @Data
    static class TicketForm {
        private String nomClient;
        private Long codePayement;
        private List<Long> tickets = new ArrayList<>();
    }

}
