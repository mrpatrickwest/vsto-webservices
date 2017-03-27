package edu.rpi.tw.vsto.model;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * VSTO Date Mapper
 *
 * @author pwest
 *
 */
public class VstoDateMapper extends BeanPropertyRowMapper<VstoDate>
{

	@Override
	public VstoDate mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final VstoDate date = new VstoDate(rs.getInt("d.date_id"),
				rs.getString("d.year"),
				rs.getString("d.month"),
				rs.getString("d.day"));

		return date;
	}
}

