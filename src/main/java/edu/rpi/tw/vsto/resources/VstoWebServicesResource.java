package edu.rpi.tw.vsto.resources;

import edu.rpi.tw.vsto.model.Instrument;
import edu.rpi.tw.vsto.model.Parameter;
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

    @CrossOrigin(origins = "*", methods = { RequestMethod.GET })
    @RequestMapping( value = "/instruments", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getInstruments() {
        String response = "{}";
        try
        {
            List<Instrument> instruments = instrumentRepository.getInstruments(false);
            JSONArray jinstruments = new JSONArray();
            if(instruments != null) {
                for(Instrument instrument : instruments) {
                    JSONObject jinstrument = new JSONObject();
                    jinstrument.put("kinst", instrument.getKinst());
                    jinstrument.put("name", instrument.getName());
                    if(instrument.getClassType() != null) jinstrument.put("class", instrument.getClassType().getName());
                    jinstruments.put(jinstrument);
                }
            }
            JSONObject jobj = new JSONObject();
            jobj.put( "instruments", jinstruments);
            response = jobj.toString();
        } catch(Exception e) {
            log.error("Failed to build the response " + e.getMessage());
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
    public String getParameters() {
        String response = "{}";
        try
        {
            List<Parameter> parameters = parameterRepository.getParameters(false);
            JSONArray jparameters = new JSONArray();
            if(parameters != null) {
                for(Parameter parameter : parameters) {
                    if(!parameter.getLongName().equals("UNDEFINED"))
                    {
                        JSONObject jparameter = new JSONObject();
                        jparameter.put( "id", parameter.getId() );
                        jparameter.put( "name", parameter.getLongName() );
                        jparameters.put( jparameter );
                    }
                }
            }
            JSONObject jobj = new JSONObject();
            jobj.put( "parameters", jparameters);
            response = jobj.toString();
        } catch(Exception e) {
            log.error("Failed to build the response " + e.getMessage());
        }
        return response;
    }

}

