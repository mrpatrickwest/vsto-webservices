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
public class ParameterMapper extends BeanPropertyRowMapper<Parameter>
{

	@Override
	public Parameter mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final Parameter parameter = new Parameter(rs.getInt("parameter.id"),
				rs.getString("parameter.long_name"),
				rs.getString("parameter.short_name"),
				rs.getString("parameter.madrigal_name"),
				rs.getString("parameter.units"),
				rs.getString("parameter.scale"),
				rs.getInt("parameter.note_id"));

		return parameter;
	}
}

