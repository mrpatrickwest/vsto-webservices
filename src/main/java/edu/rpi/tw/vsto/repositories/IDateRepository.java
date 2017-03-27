package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.VstoDate;

import java.util.List;

public interface IDateRepository {

	/**
	 * @param kinst instrument id
	 * @param params comma separated list of parameters
	 * @return list of years as strings
	 */
	public List<VstoDate> getYears(String kinst, String params);

	/**
	 * @param kinst instrument id
	 * @param params comma separated list of parameters
	 * @param year selected year
	 * @return list of months as strings
	 */
	public List<VstoDate> getMonths(String kinst, String params, String year);

	/**
     * @param kinst instrument id
     * @param params comma separated list of parameters
     * @param year selected year
     * @param month selected month
	 * @return list of days as strings
	 */
	public List<VstoDate> getDays( String kinst, String params, String year, String month);

	/**
	 * @param year selected year
	 * @param month selected month
	 * @param day selected day
	 * @return vsto date
	 */
	public VstoDate getDateId( String year, String month, String day);
}

