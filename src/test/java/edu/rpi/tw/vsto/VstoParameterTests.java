package edu.rpi.tw.vsto;

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
public class VstoParameterTests
{

    private static final Logger log = LoggerFactory.getLogger(VstoParameterTests.class);

    @Test
    public void parametersTest() {
    }
}
