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
	 * @return
	 */
	public long totalParameters();

	/**
	 * @return
	 */
	public void refreshParameters();
}

