package nan.com.jobhuntlog;

import android.content.Intent;
import android.test.ServiceTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nan.com.jobhuntlog.Service.GPSService;

/**
 * Created by NAN on 2017-01-12.
 */
public class GPSServiceTest extends ServiceTestCase<GPSService> {

    GPSService gpsService;
    public GPSServiceTest() {
        super(GPSService.class);
    }
    @Before
    public void setUp() throws Exception {
        Intent startIntent = new Intent();
        startIntent.setClass(getContext(), GPSService.class);
        startService(startIntent);
        gpsService = getService();

        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }


    @Test
    public void testService() throws Exception {

        assertEquals(true,gpsService.test());
    }

}
