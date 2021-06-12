import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CinemaService} from "../services/cinema.service";

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.css']
})
export class CinemaComponent implements OnInit {


  public villes;
  public cinemas;
  public currentVille;
  public currentCinema;
  public salles: any;
  public currentProjection: any;
  public selectedTickets: any;

  constructor(public cinemaService: CinemaService) {
  }

  ngOnInit(): void {
    this.cinemaService.getVilles()
      .subscribe(data => {
        this.villes = data;
      }, error => {
        console.error(error);
      })
  }

  onGetCinemas(v) {
    this.salles = undefined;
    this.currentVille = v;
    this.cinemaService.getCinemas(v)
      .subscribe(data => {
        this.cinemas = data;
      }, error => {
        console.error(error);
      })
  }

  onGetSalles(c) {
    this.currentCinema = c;
    this.cinemaService.getSalles(c)
      .subscribe(data => {
        this.salles = data;
        this.salles._embedded.salles.forEach(salle => {
          this.cinemaService.getProjections(salle)
            .subscribe(data => {
              salle.projections = data;
            }, error => {
              console.error(error);
            })
        })
      }, error => {
        console.error(error);
      })
  }

  onGetTicketsPlaces(p) {
    this.currentProjection = p;
    console.log(this.currentProjection);
    this.cinemaService.getTicketsPlaces(p)
      .subscribe(data => {
        this.currentProjection.tickets = data;
        this.selectedTickets = [];
      }, error => {
        console.error(error);
      })
  }

  onSelectTicket(t) {
    if (!t.selected) {
      t.selected = true;
      this.selectedTickets.push(t);
    } else {
      t.selected = false;
      this.selectedTickets.splice(this.selectedTickets.indexOf(t), 1);
    }
  }

  getTicketClass(t) {
    let str = "numeroPlace_margin btn ";
    if (t.reserved) {
      str += "btn-danger";
    } else if (t.selected) {
      str += "btn-warning";
    } else {
      str += "btn-success";
    }
    return str;
  }

  onPayTickets(formData) {
    let tickets = [] as any;
    this.selectedTickets.forEach(t => {
      tickets.push(t.id);
    });
    formData.tickets = tickets;
    this.cinemaService.payerTickets(formData)
      .subscribe(data => {
        console.log("test");
        
        alert("Ticket Successfully Reserved.");
        this.onGetTicketsPlaces(this.currentProjection);
      }, error => {
        console.error(error);
      })
  }
}
