package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Note;

import java.util.List;

public interface INoteRepository {

    /** Retrieve the specified note
     *
     * @param id identifier of the note to retrieve
     * @return Note object with the given identifier
     */
    public Note findNote(int id);

    /** Retrieves all of the notes in the system
     *
     * @param refresh if true then refresh the notes from the database
     * @return List of Note objects for all notes
     */
    public List<Note> getNotes(boolean refresh);

    /** Retrieve all of the followup notes of the given note
     *
     * @param note parent note
     * @return List of Note objects
     */
    public List<Note> getNotesGivenNote(Note note);

    /** Retrieve the count of all notes
     *
     * @return count of all notes
     */
    public long totalNotes();

    /** Refresh the notes from the database
     */
    public void refreshNotes();
}

