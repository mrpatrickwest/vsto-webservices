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
public final class DateRepository implements IDateRepository {

    public final Logger log = LoggerFactory.getLogger(DateRepository.class);

	private static final VstoDateMapper DATE_MAPPER = new VstoDateMapper();
	private static final YearMapper YEAR_MAPPER = new YearMapper();
	private static final MonthMapper MONTH_MAPPER = new MonthMapper();
	private static final DayMapper DAY_MAPPER = new DayMapper();

    private static final StringBuffer GET_YEARS = new StringBuffer()
            .append("select distinct d.year from tbl_date d, tbl_date_in_file dif  where d.DATE_ID=dif.DATE_ID")
			.append(" order by d.year");
	private static final StringBuffer GET_YEARS_GIVEN_PARAMS = new StringBuffer()
			.append("select distinct d.year from tbl_date d, tbl_date_in_file dif, tbl_file_info fi, tbl_record_info ri")
			.append(" where d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID AND ri.PARAMETER_ID in (:params)")
			.append(" order by d.year");
	private static final StringBuffer GET_YEARS_GIVEN_KINST = new StringBuffer()
			.append("select distinct d.year from tbl_date d, tbl_date_in_file dif, tbl_file_info fi, tbl_record_type rt, tbl_record_info ri")
			.append(" where d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=rt.RECORD_TYPE_ID AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID")
			.append(" AND rt.KINST=:kinst")
			.append(" order by d.year");
	private static final StringBuffer GET_YEARS_GIVEN_KINST_AND_PARAMS = new StringBuffer()
            .append("select distinct d.year from tbl_date d, tbl_date_in_file dif,")
            .append(" tbl_file_info fi, tbl_record_type rt, tbl_record_info ri")
            .append(" where d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
            .append(" AND fi.RECORD_TYPE_ID=rt.RECORD_TYPE_ID  AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID")
            .append(" AND rt.KINST=:kinst AND ri.PARAMETER_ID in (:params)")
			.append(" order by d.year");

	private static final StringBuffer GET_MONTHS = new StringBuffer()
			.append("select distinct d.month from tbl_date d, tbl_date_in_file dif where d.DATE_ID=dif.DATE_ID AND d.YEAR=:year")
			.append(" order by d.month");
	private static final StringBuffer GET_MONTHS_GIVEN_PARAMS = new StringBuffer()
			.append("select distinct d.month from tbl_date d, tbl_date_in_file dif, tbl_file_info fi, tbl_record_info ri")
			.append(" where d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID AND d.YEAR=:year AND ri.PARAMETER_ID in (:params)")
			.append(" order by d.month");
	private static final StringBuffer GET_MONTHS_GIVEN_KINST = new StringBuffer()
			.append("select distinct d.month from tbl_date d, tbl_date_in_file dif, tbl_file_info fi, tbl_record_type rt, tbl_record_info ri")
			.append(" where d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=rt.RECORD_TYPE_ID AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID")
			.append(" AND d.YEAR=:year AND rt.KINST=:kinst")
			.append(" order by d.month");
	private static final StringBuffer GET_MONTHS_GIVEN_KINST_AND_PARAMS = new StringBuffer()
			.append("select distinct d.month from tbl_date d, tbl_date_in_file dif, tbl_file_info fi, tbl_record_type rt, tbl_record_info ri")
			.append(" where d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=rt.RECORD_TYPE_ID AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID")
			.append(" AND d.YEAR=:year AND rt.KINST=:kinst AND ri.PARAMETER_ID in (:params)")
			.append(" order by d.month");

	private static final StringBuffer GET_DAYS = new StringBuffer()
			.append("select distinct d.day from tbl_date d, tbl_date_in_file dif")
			.append(" where d.DATE_ID=dif.DATE_ID AND d.YEAR=:year AND d.MONTH=:month")
			.append(" order by d.day");
	private static final StringBuffer GET_DAYS_GIVEN_PARAMS = new StringBuffer()
			.append("select distinct d.day from tbl_date d, tbl_date_in_file dif, tbl_file_info fi, tbl_record_info ri")
			.append(" where d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID AND d.YEAR=:year AND d.MONTH=:month")
			.append(" AND ri.PARAMETER_ID in (:params)")
			.append(" order by d.day");
	private static final StringBuffer GET_DAYS_GIVEN_KINST = new StringBuffer()
			.append("select distinct d.day from tbl_date d, tbl_date_in_file dif,")
			.append(" tbl_file_info fi, tbl_record_type rt, tbl_record_info ri")
			.append(" where d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=rt.RECORD_TYPE_ID AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID")
			.append(" AND d.YEAR=:year AND d.MONTH=:month AND rt.KINST=:kinst")
			.append(" order by d.day");
	private static final StringBuffer GET_DAYS_GIVEN_KINST_AND_PARAMS = new StringBuffer()
			.append("select distinct d.day from tbl_date d, tbl_date_in_file dif,")
			.append(" tbl_file_info fi, tbl_record_type rt, tbl_record_info ri")
			.append(" WHERE d.DATE_ID=dif.DATE_ID AND dif.RECORD_IN_FILE_ID=fi.RECORD_IN_FILE_ID")
			.append(" AND fi.RECORD_TYPE_ID=rt.RECORD_TYPE_ID AND fi.RECORD_TYPE_ID=ri.RECORD_TYPE_ID")
			.append(" AND d.YEAR=:year AND d.MONTH=:month AND rt.KINST=:kinst AND ri.PARAMETER_ID in (:params)")
			.append(" order by d.day");

	private static final StringBuffer GET_DATEID = new StringBuffer()
			.append("select * from tbl_date d where d.YEAR=:year AND d.MONTH=:month AND d.DAY=:day");

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * @param kinst instrument id
	 * @param parameters comma separated list of params
	 * @return list of years in the VstoDate object
	 */
	public List<VstoDate> getYears(String kinst, String parameters) {
		List<VstoDate> dates = null;
		Map<String, Object> params = new HashMap<>();
		try
		{
			if( kinst.isEmpty() && parameters.isEmpty() )
			{
				dates = this.jdbcTemplate.query( GET_YEARS.toString(), params, YEAR_MAPPER );
			} else if(kinst.isEmpty() && !parameters.isEmpty()) {
				params.put("params", parameters);
				dates = this.jdbcTemplate.query( GET_YEARS_GIVEN_PARAMS.toString(), params, YEAR_MAPPER );
			} else if(!kinst.isEmpty() && parameters.isEmpty()) {
				params.put("kinst", kinst);
				dates = this.jdbcTemplate.query( GET_YEARS_GIVEN_KINST.toString(), params, YEAR_MAPPER );
			} else {
				params.put("kinst", kinst);
				params.put("params", parameters);
				dates = this.jdbcTemplate.query( GET_YEARS_GIVEN_KINST_AND_PARAMS.toString(), params, YEAR_MAPPER );
			}
        } catch (final EmptyResultDataAccessException erdae) {
            log.error("Failed to retrieve the instruments " + erdae.getMessage());
            //NOOP
        }
		return dates;
    }

	/**
	 * @param kinst instrument id
	 * @param parameters comma separated list of params
	 * @param year selected year
	 * @return list of months in the VstoDate object
	 */
	public List<VstoDate> getMonths(String kinst, String parameters, String year) {
		List<VstoDate> dates = null;
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		try
		{
			if( kinst.isEmpty() && parameters.isEmpty() )
			{
				dates = this.jdbcTemplate.query( GET_MONTHS.toString(), params, MONTH_MAPPER );
			} else if(kinst.isEmpty() && !parameters.isEmpty()) {
				params.put("params", parameters);
				dates = this.jdbcTemplate.query( GET_MONTHS_GIVEN_PARAMS.toString(), params, MONTH_MAPPER );
			} else if(!kinst.isEmpty() && parameters.isEmpty()) {
				params.put("kinst", kinst);
				dates = this.jdbcTemplate.query( GET_MONTHS_GIVEN_KINST.toString(), params, MONTH_MAPPER );
			} else {
				params.put("kinst", kinst);
				params.put("params", parameters);
				dates = this.jdbcTemplate.query( GET_MONTHS_GIVEN_KINST_AND_PARAMS.toString(), params, MONTH_MAPPER );
			}
		} catch (final EmptyResultDataAccessException erdae) {
			log.error("Failed to retrieve the instruments " + erdae.getMessage());
			//NOOP
		}
		return dates;
    }

	/**
     * @param kinst instrument id
     * @param parameters comma separated list of params
     * @param year selected year
     * @param month selected month
	 * @return list of days in the VstoDate object
	 */
	public List<VstoDate> getDays(String kinst, String parameters, String year, String month) {
		List<VstoDate> dates = null;
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		params.put("month", month);
		try
		{
			if( kinst.isEmpty() && parameters.isEmpty() )
			{
				dates = this.jdbcTemplate.query( GET_DAYS.toString(), params, DAY_MAPPER );
			} else if(kinst.isEmpty() && !parameters.isEmpty()) {
				params.put("params", parameters);
				dates = this.jdbcTemplate.query( GET_DAYS_GIVEN_PARAMS.toString(), params, DAY_MAPPER );
			} else if(!kinst.isEmpty() && parameters.isEmpty()) {
				params.put("kinst", kinst);
				dates = this.jdbcTemplate.query( GET_DAYS_GIVEN_KINST.toString(), params, DAY_MAPPER );
			} else {
				params.put("kinst", kinst);
				params.put("params", parameters);
				dates = this.jdbcTemplate.query( GET_DAYS_GIVEN_KINST_AND_PARAMS.toString(), params, DAY_MAPPER );
			}
		} catch (final EmptyResultDataAccessException erdae) {
			log.error("Failed to retrieve the instruments " + erdae.getMessage());
			//NOOP
		}
		return dates;
    }

	/**
	 * @param year selected year
	 * @param month selected month
	 * @param day selected day
	 * @return vsto date
	 */
	public VstoDate getDateId( String year, String month, String day) {
		List<VstoDate> dates = null;
		VstoDate retDate = null;
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		params.put("month", month);
		params.put("day", day);
		try
		{
			dates = this.jdbcTemplate.query( GET_DATEID.toString(), params, DATE_MAPPER );
		} catch (final EmptyResultDataAccessException erdae) {
			log.error("Failed to retrieve the instruments " + erdae.getMessage());
			//NOOP
		}
		if(dates != null) {
			retDate = dates.get(0);

		}
		return retDate;
	}
}

