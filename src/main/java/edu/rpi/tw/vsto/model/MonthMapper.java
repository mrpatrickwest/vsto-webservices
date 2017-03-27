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
public class MonthMapper extends BeanPropertyRowMapper<VstoDate>
{

	@Override
	public VstoDate mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final VstoDate date = new VstoDate(0, "", rs.getString("d.month"), "");

		return date;
	}
}

