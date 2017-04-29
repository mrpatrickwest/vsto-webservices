package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.VstoClassType;

import java.util.List;

public interface IClassTypeRepository {

	/** Retrieve the VSTO class given its identifier
   *
	 * @param id identifier of the class of interest
	 * @return VstoClassType object of the given identifier
	 */
	public VstoClassType findClassType(int id);

	/** Retrieves the children of the specified parent class
   *
	 * @param classType The parent class
	 * @return List of VstoClassType instances that are the children of the specified parent
	 */
	public List<VstoClassType> findChildren(VstoClassType classType);

	/** Retrieve all classes
   *
	 * @param refresh if true then go to the database to retrieve the list
	 * @return List of VstoClassType instances for all classes
	 */
	public List<VstoClassType> getClassTypes(boolean refresh);

	/** Retrieve the total number of classes in VSTO
   *
	 * @return total number of classes
	 */
	public long totalClassTypes();

	/** Refreshes the list of VSTO classes
	 */
	public void refreshClassTypes();
}

