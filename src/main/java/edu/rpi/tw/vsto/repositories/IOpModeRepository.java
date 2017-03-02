package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.OpMode;

import java.util.List;

public interface IOpModeRepository {

	/**
	 * @param id
	 * @return
	 */
	public OpMode findOpMode(int id);

	/**
	 * @param kinst
	 * @return
	 */
	public List<OpMode> findOpModeFromInstrument(int kinst);

	/**
     * @param refresh
	 * @return
	 */
	public List<OpMode> getOpModes(boolean refresh);

	/**
	 * @return
	 */
	public long totalOpModes();

	/**
	 * @return
	 */
	public void refreshOpModes();
}

