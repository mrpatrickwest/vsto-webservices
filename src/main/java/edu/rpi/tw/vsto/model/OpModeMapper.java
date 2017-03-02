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
public class OpModeMapper extends BeanPropertyRowMapper<OpMode>
{

	@Override
	public OpMode mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final OpMode opmode = new OpMode(rs.getInt("opmode.record_type_id"),
				rs.getInt("opmode.kindat"),
				rs.getInt("opmode.kinst"),
				rs.getString("opmode.description"));

		return opmode;
	}
}

