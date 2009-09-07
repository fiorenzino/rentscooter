package it.reservations.web.test.event;

import it.reservations.par.Created;
import it.reservations.par.Updated;
import it.reservations.web.printer.RendererFilter;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.event.Observes;
import javax.inject.AnnotationLiteral;
import javax.inject.Current;
import javax.inject.manager.Manager;

import org.jboss.logging.Logger;

@Named
@SessionScoped
public class NoteHandler implements Serializable {

	Logger log = Logger.getLogger(RendererFilter.class.getName());

	private String note;

	private boolean editMode;

	@Current
	Manager manager;

	// @Fires
	// Event<ChangeNoteEvent> event;

	public NoteHandler() {
		// TODO Auto-generated constructor stub
	}

	public String addNote1() {
		log.info("addNote1");
		setNote("");
		setEditMode(false);
		return "/test/test.xhtml";
	}

	public String addNote2() {
		log.info("addNote2");
		// event.fire(new ChangeNoteEvent(getNote()),
		// new AnnotationLiteral<Created>() {
		// });
		manager.fireEvent(new ChangeNoteEvent(getNote()),
				new AnnotationLiteral<Created>() {
				});
		return "/test/testOk.xhtml";
	}

	public String modNote1() {
		log.info("modNote1");
		setEditMode(true);
		return "/test/test.xhtml";
	}

	public String modNote2() {
		log.info("modNote2");
		setEditMode(false);
		manager.fireEvent(new ChangeNoteEvent(getNote()),
				new AnnotationLiteral<Updated>() {
				});
		// event.fire(new ChangeNoteEvent(getNote()),
		// new AnnotationLiteral<Updated>() {
		// });
		return "/test/testOk.xhtml";
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		log.info("setNote: " + note);
		this.note = note;

	}

	public void afterCreated(@Observes @Created ChangeNoteEvent event) {
		log.info("Osservato - CREAZIONE: " + event.getNote());
	}

	public void afterUpdated(@Observes @Updated ChangeNoteEvent event) {
		log.info("Osservato - UPDATE: " + event.getNote());
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

}
