package edu.rpi.tw.vsto.model;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CEDAR File Mapper
 *
 * @author pwest
 *
 */
public class CedarFileMapper extends BeanPropertyRowMapper<CedarFile>
{

	@Override
	public CedarFile mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
		final CedarFile cedarFile = new CedarFile(rs.getInt("cf.file_id"),
				rs.getString("cf.file_name"),
				rs.getInt("cf.file_size"),
				rs.getString("cf.file_mark"),
                rs.getInt("cf.nrecords"));

		return cedarFile;
	}
}

