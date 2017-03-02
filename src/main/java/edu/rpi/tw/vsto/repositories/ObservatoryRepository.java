package edu.rpi.tw.vsto.repositories;


import edu.rpi.tw.vsto.model.Observatory;
import edu.rpi.tw.vsto.model.ObservatoryMapper;
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
public final class ObservatoryRepository implements IObservatoryRepository {

    public final Logger log = LoggerFactory.getLogger(ObservatoryRepository.class);

	private static final ObservatoryMapper OBSERVATORY_MAPPER = new ObservatoryMapper();

	private static final StringBuffer GET_OBSERVATORIES = new StringBuffer()
			.append("select * from tbl_observatory observatory order by alpha_code");

	private Map<Integer, Observatory> observatoryMap = null;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	INoteRepository noteRepository;

	/**
	 * @param id
	 * @return
	 */
	public Observatory findObservatory(int id) {
        Observatory observatory = null;
		getObservatories(false);
		if(observatoryMap != null) observatory = observatoryMap.get(id);
        return observatory;
    }

	/**
     * @param refresh
	 * @return
	 */
	public List<Observatory> getObservatories(boolean refresh) {
		final Map<String, Object> params = new HashMap<>();

		List<Observatory> observatories = null;

		if(refresh || observatoryMap == null) {
            try {
                observatories = this.jdbcTemplate.query(GET_OBSERVATORIES.toString(), params, OBSERVATORY_MAPPER);
            } catch (final EmptyResultDataAccessException erdae) {
                log.error("Failed to retrieve the observatories " + erdae.getMessage());
                //NOOP
            }
        }

		if(observatories != null && observatories.size() > 0) {
			if(refresh || observatoryMap == null) {
				observatoryMap = null;
				for (Observatory observatory : observatories) {
					loadExternals(observatory);
					if(observatoryMap == null) observatoryMap = new TreeMap<>();
					observatoryMap.put(observatory.getId(), observatory);
				}
			}
        } else if(observatoryMap != null) {
            observatories = new ArrayList<>();
            for(Observatory observatory : observatoryMap.values()) {
                observatories.add(observatory);
            }
        }

		return observatories;
    }

    private void loadExternals(Observatory observatory) {
		if(observatory.getNote() == null) observatory.setNote(this.noteRepository.findNote(observatory.getNoteId()));

	}

	/**
	 * @return
	 */
	public long totalObservatories() {
		long num = 0;
		List<Observatory> observatories = getObservatories(false);
		if(observatories != null) num = observatories.size();
        return num;
    }

	/**
	 * @return
	 */
	public void refreshObservatories() {
		getObservatories(true);
    }
}

