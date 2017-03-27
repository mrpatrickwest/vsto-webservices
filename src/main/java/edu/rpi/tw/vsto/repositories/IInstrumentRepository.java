package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Instrument;

import java.util.List;

public interface IInstrumentRepository {

	/**
	 * @param kinst
	 * @return
	 */
	public Instrument findInstrument(int kinst);

	/**
	 * @param classType
	 * @return
	 */
	public List<Instrument> findInstrumentsByClassType(String classType);

	/**
	 * @param opMode
	 * @return
	 */
	public List<Instrument> findInstrumentsByOpMode(int opMode);

	/**
     * @param refresh
	 * @return
	 */
	public List<Instrument> getInstruments(boolean refresh);

	/**
	 * @param startdateid
	 * @param enddateid
	 * @return
	 */
	public List<Instrument> getInstrumentsGivenDate(final String startdateid,
												    final String enddateid);

	/**
	 * @param params
	 * @return
	 */
	public List<Instrument> getInstrumentsGivenParams(final String params);

	/**
	 * @param startdateid
	 * @param enddateid
	 * @param params
	 * @return
	 */
	public List<Instrument> getInstrumentsGivenDateAndParams(final String startdateid,
														     final String enddateid,
														     final String params);

	/**
	 * @return
	 */
	public long totalInstruments();

	/**
	 * @return
	 */
	public void refreshInstruments();
}

