package edu.rpi.tw.vsto.model;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Note Mapper
 *
 * @author pwest
 *
 */
public class NoteMapper extends BeanPropertyRowMapper<Note>
{

	@Override
	public Note mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final Note note = new Note(rs.getInt("note.id"),
				rs.getString("note.note_user"),
				rs.getString("note.description"),
				rs.getTimestamp("note.note_date"),
				rs.getInt("note.next_note"),
				rs.getBoolean("note.public"));

		return note;
	}
}

