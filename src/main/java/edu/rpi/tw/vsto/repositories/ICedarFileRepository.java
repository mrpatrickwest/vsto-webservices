package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.CedarFile;

import java.util.List;

public interface ICedarFileRepository {

	/** Retrieves the list of all files that contains data for the given parameters
   *
	 * @param kinst id of the instrument
	 * @param startdateid id of the starting date
	 * @param enddateid id of the ending date
	 * @return List of CedarFile objects that match the specified requirements
	 */
	public List<CedarFile> getFiles(final String kinst, final String startdateid, final String enddateid);
}

