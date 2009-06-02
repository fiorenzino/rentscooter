package it.reservations.web.test.event;

import it.reservations.par.Created;
import it.reservations.par.Updated;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.event.Observes;
import javax.inject.AnnotationLiteral;
import javax.inject.Current;
import javax.inject.manager.Manager;

@Named
@SessionScoped
public class NoteHandler implements Serializable {

	private String note;

	private boolean editMode;

	@Current
	Manager manager;

//	@Fires
//	Event<ChangeNoteEvent> event;

	public NoteHandler() {
		// TODO Auto-generated constructor stub
	}

	public String addNote1() {
		System.out.println("addNote1");
		setNote("");
		setEditMode(false);
		return "/test/test.xhtml";
	}

	public String addNote2() {
		System.out.println("addNote2");
//		event.fire(new ChangeNoteEvent(getNote()),
//				new AnnotationLiteral<Created>() {
//				});
		manager.fireEvent(new ChangeNoteEvent(getNote()),
				new AnnotationLiteral<Created>() {
				});
		return "/test/testOk.xhtml";
	}

	public String modNote1() {
		System.out.println("modNote1");
		setEditMode(true);
		return "/test/test.xhtml";
	}

	public String modNote2() {
		System.out.println("modNote2");
		setEditMode(false);
		manager.fireEvent(new ChangeNoteEvent(getNote()),
				new AnnotationLiteral<Updated>() {
				});
//		event.fire(new ChangeNoteEvent(getNote()),
//				new AnnotationLiteral<Updated>() {
//				});
		return "/test/testOk.xhtml";
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		System.out.println("setNote: " + note);
		this.note = note;

	}

	public void afterCreated(@Observes @Created ChangeNoteEvent event) {
		System.out.println("Osservato - CREAZIONE: " + event.getNote());
	}

	public void afterUpdated(@Observes @Updated ChangeNoteEvent event) {
		System.out.println("Osservato - UPDATE: " + event.getNote());
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

}
