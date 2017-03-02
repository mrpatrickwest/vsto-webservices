package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Instrument;
import edu.rpi.tw.vsto.model.InstrumentMapper;
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
public final class InstrumentRepository implements IInstrumentRepository {

    public final Logger log = LoggerFactory.getLogger(InstrumentRepository.class);

    private static final InstrumentMapper INSTRUMENT_MAPPER = new InstrumentMapper();

    private static final StringBuffer GET_INSTRUMENTS = new StringBuffer()
            .append("select * from tbl_instrument instrument order by kinst");

    private Map<Integer, Instrument> instrumentMap = null;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    IClassTypeRepository classTypeRepository;
    @Autowired
    IObservatoryRepository observatoryRepository;
    @Autowired
    IOpModeRepository opModeRepository;
    @Autowired
    INoteRepository noteRepository;

    /**
     * @param kinst
     * @return
     */
    @Override
    public Instrument findInstrument(int kinst) {
        Instrument instrument = null;

        if(instrumentMap == null) {
            this.getInstruments(false);
        }
        if(instrumentMap != null) {
            instrument = instrumentMap.get(kinst);
        }

        return instrument;
    }

    /**
     * @param classType
     * @return
     */
    @Override
    public List<Instrument> findInstrumentsByClassType(String classType) {
        List<Instrument> instruments = null;
        if(instrumentMap == null) getInstruments(false);
        if(instrumentMap != null) {
            // ask the class repository for the class with the given name and all of its children
            // given the list of class types grab all of the instruments of those types
        }
        return instruments;
    }

    /**
     * @param opMode
     * @return
     */
    @Override
    public List<Instrument> findInstrumentsByOpMode(int opMode) {
        List<Instrument> instruments = null;
        if(instrumentMap == null) getInstruments(false);
        if(instrumentMap != null) {
            // ask the opmode repository for the op mode with the given id
            // given the op mode find all of the instruments that have that opmode
        }
        return instruments;
    }

    /**
     * @return
     */
    @Override
    public List<Instrument> getInstruments(boolean refresh) {
        final Map<String, Object> params = new HashMap<>();

        List<Instrument> instruments = null;

        if(refresh || instrumentMap == null) {
            try {
                instruments = this.jdbcTemplate.query(GET_INSTRUMENTS.toString(), params, INSTRUMENT_MAPPER);
            } catch (final EmptyResultDataAccessException erdae) {
                log.error("Failed to retrieve the instruments " + erdae.getMessage());
                //NOOP
            }
        }

        if(instruments != null && instruments.size() > 0) {
            if(refresh || instrumentMap == null) {
                instrumentMap = null;
                for (Instrument instrument : instruments) {
                    loadExternals(instrument);
                    if(instrumentMap == null) instrumentMap = new TreeMap<>();
                    instrumentMap.put(instrument.getKinst(), instrument);
                }
            }
        } else if(instrumentMap != null) {
            instruments = new ArrayList<>();
            for(Instrument instrument : instrumentMap.values()) {
                instruments.add(instrument);
            }
        }

        return instruments;
    }

    private void loadExternals(Instrument instrument) {
        if(instrument.getClassType() == null) instrument.setClassType(this.classTypeRepository.findClassType(instrument.getClassTypeId()));
        if(instrument.getObservatory() == null) instrument.setObservatory(this.observatoryRepository.findObservatory(instrument.getObservatoryId()));
        if(instrument.getOpModes() == null) instrument.setOpModes(this.opModeRepository.findOpModeFromInstrument(instrument.getKinst()));
        if(instrument.getNote() == null) instrument.setNote(this.noteRepository.findNote(instrument.getNoteId()));
    }

    /**
     * @return
     */
    @Override
    public long totalInstruments() {
        if(instrumentMap == null) getInstruments(false);
        return instrumentMap.size();
    }

    /**
     * @return
     */
    @Override
    public void refreshInstruments() {
        getInstruments(true);
    }
}

