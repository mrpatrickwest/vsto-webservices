package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.OpMode;
import edu.rpi.tw.vsto.model.OpModeMapper;
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
public final class OpModeRepository implements IOpModeRepository {

    public final Logger log = LoggerFactory.getLogger(OpModeRepository.class);

    private static final OpModeMapper OPMODE_MAPPER = new OpModeMapper();

    private static final StringBuffer GET_OPMODES = new StringBuffer()
            .append("select * from tbl_record_type opmode order by kindat");

    private Map<Integer, OpMode> opModeMap = null;
    private Map<Integer, List<OpMode>> opModeMapByKindat = null;
    private Map<Integer, List<OpMode>> opModeMapByKinst = null;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /** Retrieve the specified operating mode information
     *
     * @param id identifier of the operating mode of interest
     * @return OpMode object with the given identifier
     */
    public OpMode findOpMode(int id) {
        OpMode opmode = null;
        List<OpMode> opmodes = getOpModes(false);
        if(opmodes != null) opmode = opmodes.get(id);
        return opmode;
    }

    /** Retrieve all of the operating modes for the given instrument
     *
     * @param kinst identifier of the instrument
     * @return List of OpMode objects for operating modes for the instrument
     */
    public List<OpMode> findOpModeFromInstrument(int kinst) {
        List<OpMode> opModes = null;
        if(opModeMapByKinst == null) getOpModes(false);
        if(opModeMapByKinst != null) {
            opModes = opModeMapByKinst.get(kinst);
        }
        return opModes;
    }

    /** Retrieve all of the operating modes in the system
     *
     * @param refresh if true then refresh the list from the database
     * @return List of OpMode objects for all operating modes
     */
    public List<OpMode> getOpModes(boolean refresh) {
        final Map<String, Object> params = new HashMap<>();

        List<OpMode> opmodes = null;

        if(refresh || opModeMap == null) {
            try {
                opmodes = this.jdbcTemplate.query(GET_OPMODES.toString(), params, OPMODE_MAPPER);
            } catch (final EmptyResultDataAccessException erdae) {
                log.error("Failed to retrieve the op modes " + erdae.getMessage());
                //NOOP
            }
        }

        if(opmodes != null && opmodes.size() > 0) {
            if(refresh || opModeMap == null) {
                opModeMap = null;
                for (OpMode opmode : opmodes) {
                    if(opModeMap == null) {
                        opModeMap = new TreeMap<>();
                        opModeMapByKindat = new HashMap<>();
                        opModeMapByKinst = new HashMap<>();
                    }
                    opModeMap.put(opmode.getId(), opmode);
                    if(!opModeMapByKindat.containsKey(opmode.getKindat())) {
                        opModeMapByKindat.put(opmode.getKindat(), new ArrayList<>());
                    }
                    opModeMapByKindat.get(opmode.getKindat()).add(opmode);
                    if(!opModeMapByKinst.containsKey(opmode.getKinst())) {
                        opModeMapByKinst.put(opmode.getKinst(), new ArrayList<>());
                    }
                    opModeMapByKinst.get(opmode.getKinst()).add(opmode);
                }
            }
        } else if(opModeMap != null) {
            if(opmodes == null) opmodes = new ArrayList<>();
            for(OpMode opmode : opModeMap.values()) {
                opmodes.add(opmode);
            }
        }

        return opmodes;
    }

    /** Retrieve the total number of operating modes in the system
     *
     * @return total number of operating modes
     */
    public long totalOpModes() {
        long num = 0;
        List<OpMode> opmodes = getOpModes(false);
        if(opmodes != null) num = opmodes.size();
        return num;
    }

    /** Refresh the operating modes from the database
     */
    public void refreshOpModes() {
        getOpModes(true);
    }
}

