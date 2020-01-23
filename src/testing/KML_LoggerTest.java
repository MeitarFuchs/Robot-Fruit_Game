package testing;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import gameClient.KML_Logger;
import utils.Point3D;

class KML_LoggerTest {
	@Test
	public void KML_Logger() {
		KML_Logger kml= new KML_Logger(25);
        double pointX = 33.62035820243920;
        double pointY = 36.38782379379339;
        for (int i = 0; i < 25; i++) 
        {
            Point3D point = new Point3D(pointX + i, pointY + i);
                    
                    kml.placeMark("0", point.toString());     
            }
        
        kml.close_KML();
        try {
			kml.save_KML();
		}
        catch (IOException e) 
        {
			e.printStackTrace();
		}
    
	}
	
	 @Test
	    void SaveFile()  
	 {
	        try {
	            BufferedReader KML_line = new BufferedReader(new FileReader("25.kml"));
	            if(KML_line.readLine()==null) {
	            	fail();
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
