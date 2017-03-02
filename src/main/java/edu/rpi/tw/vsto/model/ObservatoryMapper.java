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
public class ObservatoryMapper extends BeanPropertyRowMapper<Observatory>
{

	@Override
	public Observatory mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final Observatory Observatory = new Observatory(rs.getInt("observatory.id"),
				rs.getString("observatory.alpha_code"),
				rs.getString("observatory.long_name"),
				rs.getString("observatory.description"),
				rs.getString("observatory.duty_cycle"),
				rs.getString("observatory.operational_hours"),
				rs.getString("observatory.ref_url"),
				rs.getInt("observatory.note_id"));

		return Observatory;
	}
}

