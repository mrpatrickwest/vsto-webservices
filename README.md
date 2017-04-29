# vsto-webservices
The [Virtual Solar Terrestrial Observatory (VSTO)](https://vsto.org "VSTO Portal") is a unified semantic environment serving data from diverse data archives in the fields of solar, solar-terrestrial, and space physics (SSTSP), currently:

* Upper atmosphere data from the CEDAR (Coupling, Energetics and Dynamics of Atmospheric Regions) archive
* Solar corona data from the MLSO (Mauna Loa Solar Observatory) archive

The VSTO portal uses an underlying ontology (i.e. an organized knowledge base of the SSTSP domain) to present a general interface that allows selection and retrieval of products (ascii and binary data files, images, plots) from heterogenous external data services. More detailed information is available on the [About the VSTO ontology page](https://www.vsto.org/ontology "VSTO Ontology").

These web services provide access to the VSTO data store including access to
* list of and information about instruments
* list of and information about parameters
* access to data given particular constraints

The endpoint for these services is https://vsto.org/vsto-ws

The following are the different requests available

### Instruments
/instruments?startdateid=&lt;start&gt;&enddateid=&lt;end&gt;&params=&lt;param1,param2,...&gt;

Retrieve the list of instruments as a JSON object given the query string
* startdateid, optional, to get all instruments with data points starting with this date
* enddateid, optional, to get all instruments with data points ending with this date (if startdateid is provided then enddateid is required)
* params, optional, a comma separated list of identifiers for parameters that the instrument gathers information for

Example output: \{"instruments": \[\{"kinst": 5340, name: "Millstone Hill Fabrey Perot", class: "Fabrey Perot"\}\]\}

### Instrument
/instrument/&lt;kinst&gt;

Retrieves information about a single instrument as a JSON object
* kinst, required, instrument identifier you are interested in

Example output: \{"classTypeId":23,"observatoryId":0,"opModeId":0,"noteId":0,"kinst":5340,"name":"Millstone Hill Fabry-Perot","prefix":"MFP","description":"The Millstone Hill Fabry Perot interferometer is operated by MIT in cooperation with the University of Pittsburgh. The interferometer is located near the Millstone Hill incoherent scatter radar at latitude 42 degrees 37 minutes North (42.62) and longitude 71 degrees 27 minutes West (-71.45). Mean local solar time differs from UT by -(4 hour 46 minutes). The local magnetic field has a 15 degree variation to the West and an inclination of 72 degrees.","class_type":\{"parentId":15,"noteId":0,"id":23,"name":"FabryPerot","parent":\{"parentId":5,"noteId":0,"id":15,"name":"Interferometer","parent":\{"parentId":1,"noteId":0,"id":5,"name":"OpticalInstrument","parent":\{"parentId":0,"noteId":0,"id":1,"name":"Instrument"\}\}\}\},"op_mode":\[\{"id":262,"kindat":7001,"kinst":5340,"description":"Tn Em Vn vertical meas in zero vel ref"\},\{"id":264,"kindat":7002,"kinst":5340,"description":"Tn Em Vn combined meas in zero vel ref"\},\{"id":263,"kindat":17001,"kinst":5340,"description":"Vn derived data from kindat 7001"\},\{"id":265,"kindat":17002,"kinst":5340,"description":"Vn derived data from kindat 7002"\}\]\}

### Parameters
/parameters?startdateid=&lt;start&gt;&enddateid=&lt;end&gt;&kinst=&lt;kinst&gt;

Retrieves a list of parameters as a JSON object given the query string
* startdateid, optional, to get all parameters with data points starting with this date
* enddateid, optional, to get all parameters with data points ending with this date (if startdateid is provided then enddateid is required)
* kinst, optional, to get all parameters measured by the instrument with this identifier

Example output: \{"parameters": \[\{"name":"Tn","id":810\}\]\}

### Parameter
/parameter/&lt;param&gt;

Retrieves information about the requested parameter
* param, required, identifier of the requested parameter

Example output: \{"noteId":0,"id":810,"short_name":"Neutral temperature","long_name":"Tn","madrigal_name":"tn","units":"K","scale":"1."\}

### Years
/years?kinst=&lt;kinst&gt;&params=&lt;param1,param2,...&gt;

Retrieves the list of years for which there is data for the given query string
* kinst, optional, identifier of the instrument
* params, optional, comma separated list of parameter identifiers

Example output: \{"years":\[\{"year":"1989"\},\{"year":"1990"\},\{"year":"1991"\},\{"year":"1992"\},\{"year":"1993"\},\{"year":"1994"\},\{"year":"1995"\},\{"year":"1996"\},\{"year":"1997"\},\{"year":"1998"\},\{"year":"1999"\},\{"year":"2000"\},\{"year":"2001"\},\{"year":"2002"\}\]\}

### Months
/months?year=&lt;year&gt;&kinst=&lt;kinst&gt;&params=&lt;param1,param2,...&gt;

Retrieves the list of months for which there is data for the given query string
* year, required
* kinst, optional, identifier of the instrument
* params, optional, comma separated list of parameter identifiers

Example output: \{"months":\[\{"month":"1"\},\{"month":"2"\},\{"month":"5"\},\{"month":"10"\},\{"month":"11"\},\{"month":"12"\}\]\}

### Days
/days?year=&lt;year&gt;&month=&lt;month&gt;&kinst=&lt;kinst&gt;&params=&lt;param1,param2,...&gt;

Retrieves the list of days for which there is data for the given query string
* year, required
* month, required
* kinst, optional, identifier of the instrument
* params, optional, comma separated list of parameter identifiers

Example output: \{"days":\[\{"day":"13"\},\{"day":"14"\},\{"day":"15"\},\{"day":"16"\},\{"day":"17"\},\{"day":"18"\},\{"day":"19"\},\{"day":"20"\},\{"day":"22"\},\{"day":"23"\},\{"day":"24"\},\{"day":"25"\},\{"day":"27"\},\{"day":"28"\},\{"day":"29"\},\{"day":"30"\},\{"day":"31"\}\]\}

### DateID
/dateid?year=&lt;year&gt;&month=&lt;month&gt;&day=&lt;day&gt;

Retrieves the date identifier for the given year, month and day. This date identifier is used in the above requests

Example:
/dateid?year=1998&month=5&day=20

Example Output: \{"date_id":17672\}

### Files
/files?startdateid=&lt;start&gt;&enddateid=&lt;end&gt;&kinst=&lt;kinst&gt;

Retrieves the list of files for which there is data given the query string
* startdateid, required, beginning date
* enddateid, required, ending date
* kinst, required, instrument identifier
