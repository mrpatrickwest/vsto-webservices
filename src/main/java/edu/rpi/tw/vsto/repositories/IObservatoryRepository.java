package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Observatory;

import java.util.List;

public interface IObservatoryRepository {

	/**
	 * @param id
	 * @return
	 */
	public Observatory findObservatory(int id);

	/**
     * @param refresh
	 * @return
	 */
	public List<Observatory> getObservatories(boolean refresh);


	/**
	 * @return
	 */
	public long totalObservatories();

	/**
	 * @return
	 */
	public void refreshObservatories();
}

