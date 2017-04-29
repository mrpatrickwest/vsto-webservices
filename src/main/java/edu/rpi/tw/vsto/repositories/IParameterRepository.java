package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Parameter;

import java.util.List;

public interface IParameterRepository {

    /** Retrieve the specified parameter
     *
     * @param id identifier of the parameter of interest
     * @return Parameter object with the given identifier
     */
    public Parameter findParameter(int id);

    /** Retrieve the list of all parameters
     *
     * @param refresh if true the refresh from the database
     * @return List of Parameter objects of all parameters
     */
    public List<Parameter> getParameters(boolean refresh);

    /** Retrieve the list of parameters measured by the specified instrument
     *
     * @param kinst id of an instrument
     * @return List of Parameter objects measured by the instrument
     */
    public List<Parameter> getParametersGivenInstrument(final String kinst);

    /** Retrieve the list of parameters measured by the instrument that has data points between the start and end dates
     *
     * @param kinst id of an instrument
     * @param startdateid id of the start date
     * @param enddateid id of the end date
     * @return List of Parameter objects that match the query
     */
    public List<Parameter> getParametersGivenInstrumentAndDate(final String kinst, final String startdateid, final String enddateid);

    /** Retrieve the list of parameters for which data has been collected between the start and end dates
     *
     * @param startdateid id of the start date
     * @param enddateid id of the end date
     * @return List of Parameter objects that meet the requirements
     */
    public List<Parameter> getParametersGivenDate(final String startdateid, final String enddateid);

    /** Retrieve the total number of parameters in the system
     * @return total number of parameters in the system
     */
    public long totalParameters();

    /** Refresh the list of parameters from the database
     */
    public void refreshParameters();
}

