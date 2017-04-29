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
 * Created by westp on 2/19/17.
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

    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/instrument/{kinst:.*}", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Instrument getInstrument(@PathVariable(value="kinst") final int kinst) {
        Instrument instrument = instrumentRepository.findInstrument( kinst );
        if(instrument != null) {
            return instrument;
        }
        return null;
    }

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

    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/parameter/{param:.*}", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Parameter getParameter(@PathVariable(value="param") final int param) {
        Parameter parameter = parameterRepository.findParameter( param );
        if(parameter != null) {
            return parameter;
        }
        return null;
    }

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

