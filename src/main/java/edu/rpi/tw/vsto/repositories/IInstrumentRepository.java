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
	 * @return
	 */
	public long totalInstruments();

	/**
	 * @return
	 */
	public void refreshInstruments();
}

