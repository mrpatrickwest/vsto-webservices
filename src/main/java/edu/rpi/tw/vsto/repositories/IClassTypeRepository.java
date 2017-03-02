package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.VstoClassType;

import java.util.List;

public interface IClassTypeRepository {

	/**
	 * @param id
	 * @return
	 */
	public VstoClassType findClassType(int id);

	/**
	 * @param classType
	 * @return
	 */
	public List<VstoClassType> findChildren(VstoClassType classType);

	/**
	 * @param refresh
	 * @return
	 */
	public List<VstoClassType> getClassTypes(boolean refresh);


	/**
	 * @return
	 */
	public long totalClassTypes();

	/**
	 * @return
	 */
	public void refreshClassTypes();
}

