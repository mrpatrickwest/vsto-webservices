package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Parameter;

import java.util.List;

public interface IParameterRepository {

	/**
	 * @param id
	 * @return
	 */
	public Parameter findParameter(int id);

	/**
     * @param refresh
	 * @return
	 */
	public List<Parameter> getParameters(boolean refresh);

	/**
	 * @param kinst id of an instrument
	 * @return
	 */
	public List<Parameter> getParametersGivenInstrument(final String kinst);

	/**
	 * @param kinst id of an instrument
	 * @param startdateid id of the start date
	 * @param enddateid id of the end date
	 * @return
	 */
	public List<Parameter> getParametersGivenInstrumentAndDate(final String kinst, final String startdateid, final String enddateid);

	/**
	 * @param startdateid id of the start date
	 * @param enddateid id of the end date
	 * @return
	 */
	public List<Parameter> getParametersGivenDate(final String startdateid, final String enddateid);

	/**
	 * @return
	 */
	public long totalParameters();

	/**
	 * @return
	 */
	public void refreshParameters();
}

