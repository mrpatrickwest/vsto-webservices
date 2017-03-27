package edu.rpi.tw.vsto.model;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Parameter Mapper
 *
 * @author pwest
 *
 */
public class ParameterMapper extends BeanPropertyRowMapper<Parameter>
{

	@Override
	public Parameter mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final Parameter parameter = new Parameter(rs.getInt("pc.parameter_id"),
				rs.getString("pc.long_name"),
				rs.getString("pc.short_name"),
				rs.getString("pc.madrigal_name"),
				rs.getString("pc.units"),
				rs.getString("pc.scale"),
				rs.getInt("pc.note_id"));

		return parameter;
	}
}

