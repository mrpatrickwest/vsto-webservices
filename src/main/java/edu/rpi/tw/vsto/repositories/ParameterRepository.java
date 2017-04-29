package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Parameter;
import edu.rpi.tw.vsto.model.ParameterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional
public final class ParameterRepository implements IParameterRepository {

    public final Logger log = LoggerFactory.getLogger(ParameterRepository.class);

    private static final ParameterMapper PARAMETER_MAPPER = new ParameterMapper();

    private static final StringBuffer PARAMETER_SELECT = new StringBuffer()
            .append("select distinct pc.parameter_id, pc.long_name, pc.short_name, pc.madrigal_name, pc.units, pc.scale, pc.note_id");

    private static final StringBuffer GET_PARAMETERS = new StringBuffer()
            .append("select * from tbl_parameter_code pc where parameter_id > 0 order by parameter_id");

    private static final StringBuffer GET_ERROR_PARAMETERS = new StringBuffer()
            .append("select * from tbl_parameter_code pc where parameter_id < 0 order by parameter_id desc");

    private static final StringBuffer GET_PARAMS_GIVEN_DATE = new StringBuffer()
            .append(PARAMETER_SELECT)
            .append(" from tbl_parameter_code pc, tbl_record_info ri, tbl_record_type rt, tbl_file_info fi,")
            .append(" tbl_date_in_file dif where pc.LONG_NAME!=\"UNDEFINED\" AND pc.PARAMETER_ID=ri.PARAMETER_ID")
            .append(" AND ri.RECORD_TYPE_ID=rt.RECORD_TYPE_ID AND rt.RECORD_TYPE_ID=fi.RECORD_TYPE_ID")
            .append(" AND fi.RECORD_IN_FILE_ID=dif.RECORD_IN_FILE_ID AND dif.DATE_ID >= :startdateid")
            .append(" AND dif.DATE_ID <= :enddateid order by ABS(pc.PARAMETER_ID) ASC");

    private static final StringBuffer GET_PARAMS_GIVEN_KINST_AND_DATE = new StringBuffer()
            .append(PARAMETER_SELECT)
            .append(" from tbl_parameter_code pc, tbl_record_info ri, tbl_record_type rt, tbl_file_info fi,")
            .append(" tbl_date_in_file dif where pc.LONG_NAME!=\"UNDEFINED\" AND pc.PARAMETER_ID=ri.PARAMETER_ID")
            .append(" AND ri.RECORD_TYPE_ID=rt.RECORD_TYPE_ID AND rt.RECORD_TYPE_ID=fi.RECORD_TYPE_ID")
            .append(" AND fi.RECORD_IN_FILE_ID=dif.RECORD_IN_FILE_ID AND dif.DATE_ID >= :startdateid")
            .append(" AND dif.DATE_ID <= :enddateid AND rt.KINST = :kinst order by ABS(pc.PARAMETER_ID) ASC");

    private static final StringBuffer GET_PARAMS_GIVEN_KINST = new StringBuffer()
            .append(PARAMETER_SELECT)
            .append(" FROM tbl_parameter_code pc, tbl_record_info ri, tbl_record_type rt")
            .append(" WHERE pc.PARAMETER_ID=ri.PARAMETER_ID AND ri.RECORD_TYPE_ID=rt.RECORD_TYPE_ID")
            .append(" AND rt.KINST = :kinst order by ABS(pc.PARAMETER_ID) ASC");

    private Map<Integer, Parameter> parameterMap = null;
    private Map<Integer, Parameter> errorParameterMap = null;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    INoteRepository noteRepository;

    /** Retrieve the specified parameter
     *
     * @param id identifier of the parameter of interest
     * @return Parameter object with the given identifier
     */
    public Parameter findParameter(int id) {
        Parameter parameter = null;
        getParameters(false);
        if(parameterMap != null) parameter = parameterMap.get(id);
        return parameter;
    }

    /** Retrieve the list of all parameters
     *
     * @param refresh if true the refresh from the database
     * @return List of Parameter objects of all parameters
     */
    public List<Parameter> getParameters(boolean refresh) {
        final Map<String, Object> params = new HashMap<>();

        List<Parameter> parameters = null;
        List<Parameter> errorParameters = null;

        if(refresh || parameterMap == null) {
            try {
                parameters = this.jdbcTemplate.query(GET_PARAMETERS.toString(), params, PARAMETER_MAPPER);
            } catch (final EmptyResultDataAccessException erdae) {
                log.error("Failed to retrieve the parameters " + erdae.getMessage());
                //NOOP
            }
            try {
                errorParameters = this.jdbcTemplate.query(GET_ERROR_PARAMETERS.toString(), params, PARAMETER_MAPPER);
            } catch (final EmptyResultDataAccessException erdae) {
                log.error("Failed to retrieve the parameters " + erdae.getMessage());
                //NOOP
            }
        }

        if(parameters != null && parameters.size() > 0) {
            if(refresh || parameterMap == null) {
                parameterMap = null;
                for (Parameter parameter : parameters) {
                    loadExternals(parameter);
                    if(parameterMap == null) parameterMap = new TreeMap<>();
                    parameterMap.put(parameter.getId(), parameter);
                }
                errorParameterMap = null;
                if(errorParameters != null && errorParameters.size() > 0)
                {
                    for( Parameter parameter : errorParameters )
                    {
                        loadExternals( parameter );
                        if( errorParameterMap == null ) errorParameterMap = new TreeMap<>();
                        errorParameterMap.put( parameter.getId(), parameter );
                        parameters.add(parameter);
                    }
                } else {
                    log.info("no error parameters");
                }
            }
        } else if(parameterMap != null) {
            parameters = new ArrayList<>();
            for(Parameter parameter : parameterMap.values()) {
                parameters.add(parameter);
            }
            if(errorParameterMap != null) {
                for( Parameter parameter : errorParameterMap.values() ) {
                    parameters.add( parameter );
                }
            }
        }

        return parameters;
    }

    /** Retrieve the list of parameters measured by the specified instrument
     *
     * @param kinst id of an instrument
     * @return List of Parameter objects measured by the instrument
     */
    public List<Parameter> getParametersGivenInstrument(final String kinst) {
        Map<String, Object> params = new HashMap<>();
        params.put("kinst", kinst);
        List<Parameter> parameters = null;
        try {
            parameters = this.jdbcTemplate.query(GET_PARAMS_GIVEN_KINST.toString(), params, PARAMETER_MAPPER);
        } catch (final EmptyResultDataAccessException erdae) {
            log.error("Failed to retrieve the parameters " + erdae.getMessage());
            //NOOP
        }
        return parameters;
    }

    /** Retrieve the list of parameters measured by the instrument that has data points between the start and end dates
     *
     * @param kinst id of an instrument
     * @param startdateid id of the start date
     * @param enddateid id of the end date
     * @return List of Parameter objects that match the query
     */
    public List<Parameter> getParametersGivenInstrumentAndDate(final String kinst, final String startdateid, final String enddateid) {
        Map<String, Object> params = new HashMap<>();
        params.put("kinst", kinst);
        params.put("startdateid", startdateid);
        params.put("enddateid", enddateid);
        List<Parameter> parameters = null;
        try {
            parameters = this.jdbcTemplate.query(GET_PARAMS_GIVEN_KINST_AND_DATE.toString(), params, PARAMETER_MAPPER);
        } catch (final EmptyResultDataAccessException erdae) {
            log.error("Failed to retrieve the parameters " + erdae.getMessage());
            //NOOP
        }
        return parameters;
    }

    /** Retrieve the list of parameters for which data has been collected between the start and end dates
     *
     * @param startdateid id of the start date
     * @param enddateid id of the end date
     * @return List of Parameter objects that meet the requirements
     */
    public List<Parameter> getParametersGivenDate(final String startdateid, final String enddateid) {
        Map<String, Object> params = new HashMap<>();
        params.put("startdateid", startdateid);
        params.put("enddateid", enddateid);
        List<Parameter> parameters = null;
        try {
            parameters = this.jdbcTemplate.query(GET_PARAMS_GIVEN_DATE.toString(), params, PARAMETER_MAPPER);
        } catch (final EmptyResultDataAccessException erdae) {
            log.error("Failed to retrieve the parameters " + erdae.getMessage());
            //NOOP
        }
        return parameters;
    }

    // load any external objects of the parameter, which are its notes
    private void loadExternals(Parameter parameter) {
        if(parameter.getNote() == null) parameter.setNote(this.noteRepository.findNote(parameter.getNoteId()));
    }

    /** Retrieve the total number of parameters in the system
     * @return total number of parameters in the system
     */
    public long totalParameters() {
        long num = 0;
        List<Parameter> parameters = getParameters(false);
        if(parameters != null) num = parameters.size();
        return num;
    }

    /** Refresh the list of parameters from the database
     */
    public void refreshParameters() {
        getParameters(true);
    }
}

