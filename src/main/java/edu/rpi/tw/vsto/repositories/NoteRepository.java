package edu.rpi.tw.vsto.repositories;


import edu.rpi.tw.vsto.model.Note;
import edu.rpi.tw.vsto.model.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional
public final class NoteRepository implements INoteRepository {

    public final Logger log = LoggerFactory.getLogger(NoteRepository.class);

	private static final NoteMapper NOTE_MAPPER = new NoteMapper();

	private static final StringBuffer GET_NOTES = new StringBuffer()
			.append("select * from tbl_notes note");

	private Map<Integer, Note> noteMap = null;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * @param id
	 * @return
	 */
	public Note findNote(int id) {
        Note note = null;
		List<Note> notes = getNotes(false);
		if(notes != null) note = noteMap.get(id);
		return note;
    }

	/**
     * @param refresh
	 * @return
	 */
	public List<Note> getNotes(boolean refresh) {
		final Map<String, Object> params = new HashMap<>();

		List<Note> notes = null;

		if(refresh || noteMap == null) {
			try {
				notes = this.jdbcTemplate.query(GET_NOTES.toString(), params, NOTE_MAPPER);
			} catch (final EmptyResultDataAccessException erdae) {
				log.error("Failed to retrieve the notes " + erdae.getMessage());
				//NOOP
			}
		}

		if(notes != null && notes.size() > 0) {
			if(refresh || noteMap == null) {
				noteMap = null;
				for (Note note : notes) {
					if(noteMap == null) noteMap = new TreeMap<>();
					noteMap.put(note.getId(), note);
				}

				for(Note note : notes) {
					if(note.getNextNoteId() != 0) {
						note.setNextNote(findNote(note.getNextNoteId()));
					}
				}
			}
		} else if(noteMap != null) {
            notes = new ArrayList<>();
			for(Note note : noteMap.values()) {
				notes.add(note);
			}
		}

		return notes;
    }

	/**
     * @param note
	 * @return
	 */
	public List<Note> getNotesGivenNote(Note note) {
		List<Note> notes = new ArrayList<>();
		notes.add(note);
		while(note.getNextNote() != null) {
			note = note.getNextNote();
            notes.add(note);
		}
		return notes;
    }

	/**
	 * @return
	 */
	public long totalNotes() {
		long num = 0;
		List<Note> notes = getNotes(false);
		if(notes != null) num = notes.size();
		return num;
    }

	/**
	 * @return
	 */
	public void refreshNotes() {
        getNotes(true);
    }
}

