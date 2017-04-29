package edu.rpi.tw.vsto;

import edu.rpi.tw.vsto.model.Instrument;
import edu.rpi.tw.vsto.model.OpMode;
import edu.rpi.tw.vsto.repositories.IInstrumentRepository;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
//import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(VstoWebServicesApplication.class)
public class VstoInstrumentTests
{

	private static final Logger log = LoggerFactory.getLogger(VstoInstrumentTests.class);

	@Autowired
	IInstrumentRepository instrumentRepository;

	@Test
	public void instrumentsTest() {
		Assert.assertNotNull(instrumentRepository);

		List<Instrument> instruments = instrumentRepository.getInstruments( false );
		Assert.assertNotNull(instruments);
		Assert.assertEquals(instruments.size(), 118);
		Instrument mhfp = null;
		for( Instrument instrument : instruments ) {
			if(instrument.getKinst() == 5340) {
				mhfp = instrument;
				break;
			}
		}
        testMhfp( mhfp );
	}

    @Test
    public void instrumentTest() {
        Assert.assertNotNull(instrumentRepository);

        Instrument mhfp = instrumentRepository.findInstrument(5340);
        testMhfp( mhfp );
    }

    private void testMhfp(Instrument mhfp) {
        Assert.assertNotNull(mhfp);
        Assert.assertEquals(mhfp.getKinst(), 5340);

        Assert.assertEquals(mhfp.getName(), "Millstone Hill Fabry-Perot");

        Assert.assertEquals(mhfp.getClassTypeId(), 23);
        Assert.assertNotNull(mhfp.getClassType());
        Assert.assertEquals(mhfp.getClassType().getId(), 23);
        Assert.assertNotNull(mhfp.getClassType().getParent());
        Assert.assertEquals(mhfp.getClassType().getParent().getId(), 15);
        Assert.assertNotNull(mhfp.getClassType().getParent().getParent());
        Assert.assertEquals(mhfp.getClassType().getParent().getParent().getId(), 5);

        Assert.assertEquals(mhfp.getNoteId(), 0);
        Assert.assertNull(mhfp.getNote());

        Assert.assertEquals(mhfp.getObservatoryId(), 0);
        Assert.assertNull(mhfp.getObservatory());

        List<OpMode> modes = mhfp.getOpModes();
        Assert.assertNotNull(modes);
        Assert.assertEquals(modes.size(), 4);
        Assert.assertEquals(modes.get(0).getKindat(), 7001);
        Assert.assertEquals(modes.get(1).getKindat(), 7002);
        Assert.assertEquals(modes.get(2).getKindat(), 17001);
        Assert.assertEquals(modes.get(3).getKindat(), 17002);
    }

    @Test
    public void instrumentDateTest() {
        Assert.assertNotNull(instrumentRepository);

        List<Instrument> instruments = instrumentRepository.getInstrumentsGivenDate( "17672", "17682" );
        Assert.assertNotNull(instruments);
        Assert.assertEquals(instruments.size(), 21);
        Assert.assertEquals(instruments.get(0).getKinst(), 10);
        Assert.assertEquals(instruments.get(0).getName(), "Jicamarca Peru I.S. Radar");
        Assert.assertEquals(instruments.get(1).getKinst(), 72);
        Assert.assertEquals(instruments.get(1).getName(), "EISCAT: Tromso antenna");
        Assert.assertEquals(instruments.get(2).getKinst(), 80);
        Assert.assertEquals(instruments.get(2).getName(), "Sondrestrom I.S. Radar");
        Assert.assertEquals(instruments.get(3).getKinst(), 120);
        Assert.assertEquals(instruments.get(3).getName(), "Interplanetary Mag Fld and Solar Wind");
        Assert.assertEquals(instruments.get(4).getKinst(), 175);
        Assert.assertEquals(instruments.get(4).getName(), "Estimated Hemispheric Power");
    }

    @Test
    public void instrumentParamTest() {
        Assert.assertNotNull(instrumentRepository);

        List<Instrument> instruments = instrumentRepository.getInstrumentsGivenParams( "-810" );
        Assert.assertNotNull(instruments);
        Assert.assertEquals(instruments.size(), 13);
        Assert.assertEquals(instruments.get(0).getKinst(), 80);
        Assert.assertEquals(instruments.get(0).getName(), "Sondrestrom I.S. Radar");
        Assert.assertEquals(instruments.get(1).getKinst(), 5000);
        Assert.assertEquals(instruments.get(1).getName(), "South Pole Fabry-Perot Interfer Spectr");
        Assert.assertEquals(instruments.get(2).getKinst(), 5015);
        Assert.assertEquals(instruments.get(2).getName(), "Arrival Heights Fabry-Perot Interf Sp");
        Assert.assertEquals(instruments.get(3).getKinst(), 5060);
        Assert.assertEquals(instruments.get(3).getName(), "Mount John New Zealand Fabry-Perot");
        Assert.assertEquals(instruments.get(4).getKinst(), 5240);
        Assert.assertEquals(instruments.get(4).getName(), "Fritz Peak CO Fabry-Perot Interf Sp");
    }

    @Test
    public void instrumentDateAndParamTest()
    {
        Assert.assertNotNull( instrumentRepository );

        List<Instrument> instruments = instrumentRepository.getInstrumentsGivenDateAndParams( "17672", "17682", "-810" );
        Assert.assertNotNull( instruments );
        Assert.assertEquals( instruments.size(), 2 );
        Assert.assertEquals( instruments.get( 0 ).getKinst(), 5000 );
        Assert.assertEquals( instruments.get( 0 ).getName(), "South Pole Fabry-Perot Interfer Spectr" );
        Assert.assertEquals( instruments.get( 1 ).getKinst(), 5340 );
        Assert.assertEquals( instruments.get( 1 ).getName(), "Millstone Hill Fabry-Perot" );
    }

    @Test
    public void instrumentErrorTests() {
        Assert.assertNotNull(instrumentRepository);

        Instrument instrument = instrumentRepository.findInstrument( 0 );
        Assert.assertNull(instrument);

        List<Instrument> instruments = instrumentRepository.getInstrumentsGivenDate( "", "" );
        Assert.assertNotNull(instruments);
        Assert.assertEquals(instruments.size(), 0);

        instruments = instrumentRepository.getInstrumentsGivenParams( "" );
        Assert.assertNotNull(instruments);
        Assert.assertEquals(instruments.size(), 0);

        instruments = instrumentRepository.getInstrumentsGivenDateAndParams( "", "", "" );
        Assert.assertNotNull(instruments);
        Assert.assertEquals(instruments.size(), 0);
    }
}
