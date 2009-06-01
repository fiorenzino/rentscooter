package it.reservations.web;

import it.reservations.par.Created;
import it.reservations.par.Updated;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.event.Observes;

@Named
@SessionScoped
public class NoteObserver implements Serializable {

	public NoteObserver() {
		// TODO Auto-generated constructor stub
	}

	public void afterCreated(@Observes @Created ChangeNoteEvent event) {
		System.out.println("NoteObserver - @Created: " + event.getNote());
	}

	public void afterUpdated(@Observes @Updated ChangeNoteEvent event) {
		System.out.println("NoteObserver - @Updated: " + event.getNote());
	}
}
