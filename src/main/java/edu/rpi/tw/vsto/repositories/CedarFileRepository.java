package edu.rpi.tw.vsto.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rpi.tw.vsto.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class CedarFileRepository implements ICedarFileRepository {

    public final Logger log = LoggerFactory.getLogger(CedarFileRepository.class);

	private static final CedarFileMapper CEDAR_FILE_MAPPER = new CedarFileMapper();

    private static final StringBuffer GET_FILES = new StringBuffer()
            .append("select distinct cf.file_id, cf.file_name, cf.file_size, cf.file_mark, cf.nrecords")
			.append(" from tbl_date_in_file dif, tbl_cedar_file cf, tbl_file_info fi, tbl_record_type rt")
			.append(" where dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID AND fi.FILE_ID=cf.FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=rt.RECORD_TYPE_ID AND (dif.DATE_ID >= :startdateid )")
			.append(" AND (dif.DATE_ID <= :enddateid ) AND (rt.KINST = :kinst )");

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * @param kinst instrument id
	 * @param startdateid id of the starting date
	 * @param enddateid id of the ending date
	 * @return list of files
	 */
	public List<CedarFile> getFiles(final String kinst, final String startdateid, final String enddateid) {
		List<CedarFile> files = null;
		Map<String, Object> params = new HashMap<>();
        params.put("kinst", kinst);
        params.put("startdateid", startdateid);
        params.put("enddateid", enddateid);
		try
		{
            files = this.jdbcTemplate.query( GET_FILES.toString(), params, CEDAR_FILE_MAPPER );
        } catch (final EmptyResultDataAccessException erdae) {
            log.error("Failed to retrieve the instruments " + erdae.getMessage());
            //NOOP
        }
		return files;
    }
}

