package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.Observatory;

import java.util.List;

public interface IObservatoryRepository {

    /** Retrieve the specified Observatory
     *
     * @param id identifier of the observatory of interest
     * @return Observatory object with the given identifier
     */
    public Observatory findObservatory(int id);

    /** Retrieve the list of observatories in the system
     *
     * @param refresh if true then refresh the list from the database
     * @return List of Observatory objects for all observatories
     */
    public List<Observatory> getObservatories(boolean refresh);


    /** Retrieve the count of observatories in the system
     *
     * @return count of all observatories
     */
    public long totalObservatories();

    /** Refresh the list of observatories from the database
     */
    public void refreshObservatories();
}

