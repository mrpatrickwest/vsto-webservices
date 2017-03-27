package edu.rpi.tw.vsto.model;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class Type Mapper
 *
 * @author pwest
 *
 */
public class VstoClassTypeMapper extends BeanPropertyRowMapper<VstoClassType>
{

	@Override
	public VstoClassType mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final VstoClassType VstoClassType = new VstoClassType(rs.getInt("type.id"),
				rs.getString("type.name"),
				rs.getInt("type.parent"),
				rs.getInt("type.note_id"));

		return VstoClassType;
	}
}

