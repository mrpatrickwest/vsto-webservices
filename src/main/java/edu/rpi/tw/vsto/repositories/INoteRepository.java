package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Note;

import java.util.List;

public interface INoteRepository {

	/**
	 * @param id
	 * @return
	 */
	public Note findNote(int id);

	/**
	 * @return
	 */
	public List<Note> getNotes(boolean refresh);

	/**
     * @param note
	 * @return
	 */
	public List<Note> getNotesGivenNote(Note note);

	/**
	 * @return
	 */
	public long totalNotes();

	/**
	 * @return
	 */
	public void refreshNotes();
}

