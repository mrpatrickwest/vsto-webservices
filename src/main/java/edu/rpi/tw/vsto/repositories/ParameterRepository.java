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

	private static final StringBuffer GET_PARAMETERS = new StringBuffer()
			.append("select * from tbl_parameter_code parameter order by parameter_id");

	private Map<Integer, Parameter> parameterMap = null;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	INoteRepository noteRepository;

	/**
	 * @param id
	 * @return
	 */
	public Parameter findParameter(int id) {
        Parameter parameter = null;
		getParameters(false);
		if(parameterMap != null) parameter = parameterMap.get(id);
		return parameter;
    }

	/**
     * @param refresh
	 * @return
	 */
	public List<Parameter> getParameters(boolean refresh) {
		final Map<String, Object> params = new HashMap<>();

		List<Parameter> parameters = null;

		if(refresh || parameterMap == null) {
			try {
				parameters = this.jdbcTemplate.query(GET_PARAMETERS.toString(), params, PARAMETER_MAPPER);
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
			}
		} else if(parameterMap != null) {
			parameters = new ArrayList<>();
			for(Parameter parameter : parameterMap.values()) {
				parameters.add(parameter);
			}
		}

		return parameters;
    }

    private void loadExternals(Parameter parameter) {
		if(parameter.getNote() == null) parameter.setNote(this.noteRepository.findNote(parameter.getNoteId()));
	}

	/**
	 * @return
	 */
	public long totalParameters() {
		long num = 0;
		List<Parameter> parameters = getParameters(false);
		if(parameters != null) num = parameters.size();
		return num;
    }

	/**
	 * @return
	 */
	public void refreshParameters() {
        getParameters(true);
    }
}

