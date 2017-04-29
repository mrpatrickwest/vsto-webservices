package edu.rpi.tw.vsto.resources;

import edu.rpi.tw.vsto.model.CedarFile;
import edu.rpi.tw.vsto.model.Instrument;
import edu.rpi.tw.vsto.model.Parameter;
import edu.rpi.tw.vsto.model.VstoDate;
import edu.rpi.tw.vsto.repositories.ICedarFileRepository;
import edu.rpi.tw.vsto.repositories.IDateRepository;
import edu.rpi.tw.vsto.repositories.IInstrumentRepository;
import edu.rpi.tw.vsto.repositories.IParameterRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by pwest on 2/19/17.
 */
@RestController
public class VstoWebServicesResource
{
    private static final Logger log = LoggerFactory.getLogger(VstoWebServicesResource.class);

    @Autowired
    IInstrumentRepository instrumentRepository;
    @Autowired
    IParameterRepository parameterRepository;
    @Autowired
    IDateRepository dateRepository;
    @Autowired
    ICedarFileRepository cedarFileRepository;

    /** Retrieve the list of instruments given the query string parameters
     *
     * Example output: {"instruments": [{"kinst": 5340, name: "Millstone Hill Fabrey Perot", class: "Fabrey Perot"}]}
     *
     * @param startdateid retrieve instruments that have data points starting with this date identifier
     * @param enddateid retrieves instruments that have data points ending with this date identifer (required if startdateid is provided)
     * @param params comma separated list of parameters that an instrument collects data
     * @return JSON representation of instruments
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/instruments", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getInstruments(@RequestParam(value = "startdateid", required = false, defaultValue = "") final String startdateid,
                                 @RequestParam(value = "enddateid", required = false, defaultValue = "") final String enddateid,
                                 @RequestParam(value = "params", required = false, defaultValue = "") final String params) {
        String response = "{}";
        List<Instrument> instruments;
        try {
            if(startdateid.isEmpty() && params.isEmpty()) {
                instruments = instrumentRepository.getInstruments( false );
            } else if(startdateid.isEmpty() && !params.isEmpty()) {
                instruments = instrumentRepository.getInstrumentsGivenParams(params);
            } else if(!startdateid.isEmpty() && params.isEmpty()) {
                instruments = instrumentRepository.getInstrumentsGivenDate(startdateid, enddateid);
            } else {
                instruments = instrumentRepository.getInstrumentsGivenDateAndParams(startdateid, enddateid, params);
            }
        } catch(Exception e) {
            log.error("Failed to get the instrument list " + e.getMessage());
            return response;
        }

        try {
            JSONArray jinstruments = new JSONArray();
            if( instruments != null ) {
                for( Instrument instrument : instruments ) {
                    JSONObject jinstrument = new JSONObject();
                    jinstrument.put( "kinst", instrument.getKinst() );
                    jinstrument.put( "name", instrument.getName() );
                    if( instrument.getClassType() != null )
                        jinstrument.put( "class", instrument.getClassType().getName() );
                    jinstruments.put( jinstrument );
                }
            }
            JSONObject jobj = new JSONObject();
            jobj.put( "instruments", jinstruments );
            response = jobj.toString();
        } catch( Exception e ) {
            log.error( "Failed to build the response " + e.getMessage() );
        }
        return response;
    }

    /** Retrieves information about the specified instrument
     *
     * Example Output:
     * {
     *   "classTypeId": 23,
     *   "observatoryId": 0,
     *   "opModeId": 0,
     *   "noteId": 0,
     *   "kinst": 5340,
     *   "name": "Millstone Hill Fabry-Perot",
     *   "prefix": "MFP",
     *   "description": "The Millstone Hill Fabry Perot interferometer is operated by MIT in cooperation with the University of Pittsburgh. The interferometer is located near the Millstone Hill incoherent scatter radar at latitude 42 degrees 37 minutes North (42.62) and longitude 71 degrees 27 minutes West (-71.45). Mean local solar time differs from UT by -(4 hour 46 minutes). The local magnetic field has a 15 degree variation to the West and an inclination of 72 degrees.",
     *   "class_type": {
     *     "parentId": 15,
     *     "noteId": 0,
     *     "id": 23,
     *     "name": "FabryPerot",
     *     "parent": {
     *       "parentId": 5,
     *       "noteId": 0,
     *       "id": 15,
     *       "name": "Interferometer",
     *       "parent": {
     *         "parentId": 1,
     *         "noteId": 0,
     *         "id": 5,
     *         "name": "OpticalInstrument",
     *         "parent": {
     *           "parentId": 0,
     *           "noteId": 0,
     *           "id": 1,
     *           "name": "Instrument"
     *         }
     *       }
     *     }
     *   },
     *   "op_mode": [
     *     {
     *       "id": 262,
     *       "kindat": 7001,
     *       "kinst": 5340,
     *       "description": "Tn Em Vn vertical meas in zero vel ref"
     *     },
     *     {
     *       "id": 264,
     *       "kindat": 7002,
     *       "kinst": 5340,
     *       "description": "Tn Em Vn combined meas in zero vel ref"
     *     },
     *     {
     *       "id": 263,
     *       "kindat": 17001,
     *       "kinst": 5340,
     *       "description": "Vn derived data from kindat 7001"
     *     },
     *     {
     *       "id": 265,
     *       "kindat": 17002,
     *       "kinst": 5340,
     *       "description": "Vn derived data from kindat 7002"
     *     }
     *   ]
     * }
     *
     * @param kinst identifier of the instrument interested in
     * @return JSON representation of the instrument's information
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/instrument/{kinst:.*}", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Instrument getInstrument(@PathVariable(value="kinst") final int kinst) {
        Instrument instrument = instrumentRepository.findInstrument( kinst );
        if(instrument != null) {
            return instrument;
        }
        return null;
    }

    /** Retrieve the list of parameters given the query string parameters
     *
     * Example output: {"parameters": [{"name":"Tn","id":810}]}
     *
     * @param startdateid retrieve parameters that have data points starting with this date identifier
     * @param enddateid retrieves parameters that have data points ending with this date identifer (required if startdateid is provided)
     * @param kinst identifier of the instrument that measures the parameters
     * @return JSON representation of parameters
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/parameters", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getParameters(@RequestParam(value = "startdateid", required = false, defaultValue = "") final String startdateid,
                                @RequestParam(value = "enddateid", required = false, defaultValue = "") final String enddateid,
                                @RequestParam(value = "kinst", required = false, defaultValue = "") final String kinst) {
        String response = "{}";
        List<Parameter> parameters = null;
        try
        {
            if( kinst.isEmpty() && startdateid.isEmpty() )
            {
                parameters = parameterRepository.getParameters( false );
            } else if(kinst.isEmpty() && !startdateid.isEmpty()) {
                parameters = parameterRepository.getParametersGivenDate( startdateid, enddateid );
            } else if(!kinst.isEmpty() && startdateid.isEmpty()) {
                parameters = parameterRepository.getParametersGivenInstrument( kinst );
            } else if(!kinst.isEmpty() && !startdateid.isEmpty()) {
                parameters = parameterRepository.getParametersGivenInstrumentAndDate( kinst, startdateid, enddateid );
            }
        } catch(Exception e)
        {
            log.error( "Failed to get the parameters " + e.getMessage() );
        }
        if(parameters != null)
        {
            try
            {
                JSONArray jparameters = new JSONArray();
                if( parameters != null )
                {
                    for( Parameter parameter : parameters )
                    {
                        if( !parameter.getLongName().equals( "UNDEFINED" ) )
                        {
                            JSONObject jparameter = new JSONObject();
                            jparameter.put( "id", parameter.getId() );
                            jparameter.put( "name", parameter.getLongName() );
                            jparameters.put( jparameter );
                        }
                    }
                }
                JSONObject jobj = new JSONObject();
                jobj.put( "parameters", jparameters );
                response = jobj.toString();
            }
            catch( Exception e )
            {
                log.error( "Failed to build the response " + e.getMessage() );
            }
        }
        return response;
    }

    /** Retrieves information about the specified parameter
     *
     * Example Output:
     * {
     *   "noteId": 0,
     *   "id": 810,
     *   "short_name": "Neutral temperature",
     *   "long_name": "Tn",
     *   "madrigal_name": "tn",
     *   "units": "K",
     *   "scale": "1."
     * }
     *
     * @param param identifier of the parameter interested in
     * @return JSON representation of the parameter
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/parameter/{param:.*}", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Parameter getParameter(@PathVariable(value="param") final int param) {
        Parameter parameter = parameterRepository.findParameter( param );
        if(parameter != null) {
            return parameter;
        }
        return null;
    }

    /** Retrieve the list of years where data points have been collected
     *
     * Example output:
     * {
     *   "years": [
     *     { "year": "1989" },
     *     { "year": "1990" },
     *     { "year": "1991" },
     *     { "year": "1992" },
     *     { "year": "1993" },
     *     { "year": "1994" },
     *     { "year": "1995" },
     *     { "year": "1996" },
     *     { "year": "1997" },
     *     { "year": "1998" },
     *     { "year": "1999" },
     *     { "year": "2000" },
     *     { "year": "2001" },
     *     { "year": "2002" }
     *   ]
     * }
     *
     * @param kinst identifier of an instrument
     * @param params comma separated list of parameter identifiers
     * @return JSON object with the return years
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/years", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getYears(@RequestParam(value = "kinst", required = false, defaultValue = "") final String kinst,
                           @RequestParam(value = "params", required = false, defaultValue = "") final String params)
    {
        String response = "{}";
        List<VstoDate> years = this.dateRepository.getYears(kinst, params);
        if(years != null) {
            try
            {
                JSONArray jyears = new JSONArray();
                for( VstoDate year : years )
                {
                    JSONObject jyear = new JSONObject();
                    jyear.put("year", year.getYear());
                    jyears.put(jyear);
                }
                JSONObject jobj = new JSONObject();
                jobj.put("years", jyears);
                response = jobj.toString();
            } catch(Exception e) {
                log.error( "Failed to build year response " + e.getMessage());
            }
        }
        return response;
    }

    /** Retrieves list of months for which there are data points
     *
     * Example output: {"months":[{"month":"1"},{"month":"2"},{"month":"5"},{"month":"10"},{"month":"11"},{"month":"12"}]}
     * {
     *   "months": [
     *     { "month": "1" },
     *     { "month": "2" },
     *     { "month": "5" },
     *     { "month": "10" },
     *     { "month": "11" },
     *     { "month": "12" }
     *   ]
     * }
     *
     * @param year year of interest
     * @param kinst identifier of the instrument for which there is data
     * @param params comma separated list of parameter identifiers for which there are data points
     * @return JSON representation of the list
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/months", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getMonths(@RequestParam(value = "year") final String year,
                            @RequestParam(value = "kinst", required = false, defaultValue = "") final String kinst,
                            @RequestParam(value = "params", required = false, defaultValue = "") final String params)
    {
        String response = "{}";
        List<VstoDate> months = this.dateRepository.getMonths(kinst, params, year);
        if(months != null) {
            try
            {
                JSONArray jmonths = new JSONArray();
                for( VstoDate month : months )
                {
                    JSONObject jmonth = new JSONObject();
                    jmonth.put("month", month.getMonth());
                    jmonths.put(jmonth);
                }
                JSONObject jobj = new JSONObject();
                jobj.put("months", jmonths);
                response = jobj.toString();
            } catch(Exception e) {
                log.error( "Failed to build month response " + e.getMessage());
            }
        }
        return response;
    }

    /** Retrieves the days of the month and year where there are data points
     *
     * Example output:
     * {
     *   "days": [
     *     { "day": "13" },
     *     { "day": "14" },
     *     { "day": "15" },
     *     { "day": "16" },
     *     { "day": "17" },
     *     { "day": "18" },
     *     { "day": "19" },
     *     { "day": "20" },
     *     { "day": "22" },
     *     { "day": "23" },
     *     { "day": "24" },
     *     { "day": "25" },
     *     { "day": "27" },
     *     { "day": "28" },
     *     { "day": "29" },
     *     { "day": "30" },
     *     { "day": "31" }
     *   ]
     * }
     *
     * @param year year of interest
     * @param month month of interest
     * @param kinst identifier of the instrument for which there are data points
     * @param params comma separated list of parameters for which there are data points
     * @return JSON representation of the day list
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/days", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getDays(@RequestParam(value = "year") final String year,
                          @RequestParam(value = "month") final String month,
                          @RequestParam(value = "kinst", required = false, defaultValue = "") final String kinst,
                          @RequestParam(value = "params", required = false, defaultValue = "") final String params)
    {
        String response = "{}";
        List<VstoDate> days = this.dateRepository.getDays(kinst, params, year, month);
        if(days != null) {
            try
            {
                JSONArray jdays = new JSONArray();
                for( VstoDate day : days )
                {
                    JSONObject jday = new JSONObject();
                    jday.put("day", day.getDay());
                    jdays.put(jday);
                }
                JSONObject jobj = new JSONObject();
                jobj.put("days", jdays);
                response = jobj.toString();
            } catch(Exception e) {
                log.error( "Failed to build day response " + e.getMessage());
            }
        }
        return response;
    }

    /** Retrieve the dataid given the year, month and day
     *
     * Example output: {"date_id":17672}
     *
     * @param year year of interest
     * @param month month of interest
     * @param day day of interest
     * @return JSON representation of the result
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/dateid", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getDateId(@RequestParam(value = "year") final String year,
                            @RequestParam(value = "month") final String month,
                            @RequestParam(value = "day") final String day)
    {
        String response = "{}";
        VstoDate theDate = this.dateRepository.getDateId( year, month, day );
        if(theDate != null) {
            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put( "date_id", theDate.getDateId() );
                response = jobj.toString();
            } catch(Exception e) {
                log.error("Failed to build date id response");
            }
        }
        return response;
    }

    /** Retrieves the list of files that contains the data points for the given start and end dates and instrument and parameters
     *
     * Example output: {"files":[{"file_name":"mfp980109a"}]}
     *
     * @param startdateid starting date for which there are data points
     * @param enddateid ending date for which there are data points
     * @param kinst identifier of the instrument that has collected the data points
     * @param params comma separated list of parameters for which there is data
     * @return JSON representation of the file list
     */
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/files", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getFiles(@RequestParam(value = "startdateid") final String startdateid,
                           @RequestParam(value = "enddateid") final String enddateid,
                           @RequestParam(value = "kinst") final String kinst,
                           @RequestParam(value = "params", required = false, defaultValue = "") final String params)
    {
        String response = "{}";
        List<CedarFile> files = this.cedarFileRepository.getFiles(kinst, startdateid, enddateid);
        if(files != null) {
            try
            {
                JSONArray jfiles = new JSONArray();
                for( CedarFile file : files )
                {
                    JSONObject jfile = new JSONObject();
                    jfile.put("file_name", file.getFileName());
                    jfiles.put(jfile);
                }
                JSONObject jobj = new JSONObject();
                jobj.put("files", jfiles);
                response = jobj.toString();
            } catch(Exception e) {
                log.error( "Failed to build files response " + e.getMessage());
            }
        }
        return response;
    }
}

