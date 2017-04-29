package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.OpMode;

import java.util.List;

public interface IOpModeRepository {

    /** Retrieve the specified operating mode information
     *
     * @param id identifier of the operating mode of interest
     * @return OpMode object with the given identifier
     */
    public OpMode findOpMode(int id);

    /** Retrieve all of the operating modes for the given instrument
     *
     * @param kinst identifier of the instrument
     * @return List of OpMode objects for operating modes for the instrument
     */
    public List<OpMode> findOpModeFromInstrument(int kinst);

    /** Retrieve all of the operating modes in the system
     *
     * @param refresh if true then refresh the list from the database
     * @return List of OpMode objects for all operating modes
     */
    public List<OpMode> getOpModes(boolean refresh);

    /** Retrieve the total number of operating modes in the system
     *
     * @return total number of operating modes
     */
    public long totalOpModes();

    /** Refresh the operating modes from the database
     */
    public void refreshOpModes();
}

