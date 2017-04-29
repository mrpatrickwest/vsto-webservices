package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Instrument;

import java.util.List;

public interface IInstrumentRepository {

    /** Retrieve the instrument object for the specified identifier
     *
     * @param kinst instrument identifier
     * @return Instrument object for that instrument
     */
    public Instrument findInstrument(int kinst);

    /** Retrieve the list of all instruments for the given class
     *
     * @param classType class of interest
     * @return List of Instrument objects that have the given class
     */
    public List<Instrument> findInstrumentsByClassType(String classType);

    /** Retrieve the list of all instruments given the operating mode
     *
     * @param opMode operating mode of interest
     * @return List of Instrument objects that have this operating mode
     */
    public List<Instrument> findInstrumentsByOpMode(int opMode);

    /** Retrieve the list of all instruments
     *
     * @param refresh if true then go back to the database to retrieve the list
     * @return List of instrument objects that meet the requirements
     */
    public List<Instrument> getInstruments(boolean refresh);

    /** Retrieve the list of instruments for which there are data points between the two dates
     *
     * @param startdateid identifier of starting date
     * @param enddateid identifier of ending date
     * @return List of Instrument objects that meet the requirements
     */
    public List<Instrument> getInstrumentsGivenDate(final String startdateid,
                                                    final String enddateid);

    /** Retrieve the list of instruments that measure the specified parameters
     *
     * @param params comma separated list of parameters of interest
     * @return List of Instrument objects that meet the requirements
     */
    public List<Instrument> getInstrumentsGivenParams(final String params);

    /** Retrieve the list of instruments given the date and list of parameters for which there is data
     *
     * @param startdateid identifier of starting date
     * @param enddateid identifier of ending date
     * @param params comma separated list of parameters of interest
     * @return List of Instrument objects that meet the requirements
     */
    public List<Instrument> getInstrumentsGivenDateAndParams(final String startdateid,
                                                             final String enddateid,
                                                             final String params);

    /** Retrieve the total number of instruments available
     *
     * @return count of all instruments
     */
    public long totalInstruments();

    /** Refresh from the database all of the instruments
     */
    public void refreshInstruments();
}

