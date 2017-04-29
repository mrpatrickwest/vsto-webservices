package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.VstoDate;

import java.util.List;

public interface IDateRepository {

	/** Retrieves the list of all years for which there are data points
   *
	 * @param kinst instrument id
	 * @param params comma separated list of parameters
	 * @return list of years as strings
	 */
	public List<VstoDate> getYears(String kinst, String params);

	/** Retrieves the list of all months in the given year for which there are data points
   *
	 * @param kinst instrument id
	 * @param params comma separated list of parameters
	 * @param year selected year
	 * @return list of months as strings
	 */
	public List<VstoDate> getMonths(String kinst, String params, String year);

	/** Retrieves the list of all days of the year and month for which there are data points
   *
   * @param kinst instrument id
   * @param params comma separated list of parameters
   * @param year selected year
   * @param month selected month
	 * @return list of days as strings
	 */
	public List<VstoDate> getDays( String kinst, String params, String year, String month);

	/** Retrieve the date id for the given year, month and day
   *
	 * @param year selected year
	 * @param month selected month
	 * @param day selected day
	 * @return vsto date
	 */
	public VstoDate getDateId( String year, String month, String day);
}

