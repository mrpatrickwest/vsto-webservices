package edu.rpi.tw.vsto.model;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AccountMapper
 * Account to domain object mapper
 *
 * @author john
 *
 */
public class InstrumentMapper extends BeanPropertyRowMapper<Instrument>
{

	@Override
	public Instrument mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final Instrument instrument = new Instrument(rs.getInt("instrument.kinst"),
				rs.getString("instrument.inst_name"),
				rs.getString("instrument.prefix"),
				rs.getString("instrument.description"),
				rs.getInt("instrument.class_type_id"),
				rs.getInt("instrument.observatory"),
				rs.getInt("instrument.op_mode"),
				rs.getInt("instrument.note_id"));

		return instrument;
	}
}

