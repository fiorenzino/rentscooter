package it.reservations.web.test.event;

import it.reservations.par.Created;
import it.reservations.par.Updated;
import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.event.Observes;

import org.jboss.logging.Logger;

@Named
@SessionScoped
public class NoteObserver implements Serializable {

	Logger log = Logger.getLogger(NoteObserver.class.getName());

	public NoteObserver() {
		// TODO Auto-generated constructor stub
	}

	public void afterCreated(@Observes @Created ChangeNoteEvent event) {
		log.info("NoteObserver - @Created: " + event.getNote());
	}

	public void afterUpdated(@Observes @Updated ChangeNoteEvent event) {
		log.info("NoteObserver - @Updated: " + event.getNote());
	}
}
