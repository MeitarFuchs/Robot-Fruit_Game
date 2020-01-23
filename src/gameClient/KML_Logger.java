package gameClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * this class create a kml file for all the level of the game (0-23).
 * we can load the kml file to google earth and watch the route of the robot for the level we choose. 
 * @author meitar&salome
 *
 */
public class KML_Logger {

	private StringBuilder strBuilder;
	private int level;

	/**
	 * default constructor who start the kml file
	 */
	public  KML_Logger()
	{
		this.strBuilder = new StringBuilder();
		this.level=MyGameGUI.level;
		makeKML();
	}
	/**
	 * constructor who get level to start the kml file
	 * @param level the level of the game
	 */
	public KML_Logger(int level) 
	{
		this.level = level;
		this.strBuilder = new StringBuilder();
		makeKML();
	}
	/**
	 * this method initialize the kml file and the node there
	 */
	public void makeKML() 
	{
		this.strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" );
		this.strBuilder.append(               "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" );
		this.strBuilder.append(     "  <Document>\r\n" );
		this.strBuilder.append("  <name>" + "Game stage :" + this.level + "</name>" +"\r\n");
		this.strBuilder.append(" <Style id=\"node\">\r\n");
		this.strBuilder.append(   "      <IconStyle>\r\n" );
		this.strBuilder.append(    "        <Icon>\r\n" );
		this.strBuilder.append(    "          <href>http://maps.google.com/mapfiles/kml/pal3/icon35.png</href>\r\n");
		this.strBuilder.append(    "        </Icon>\r\n" );
		this.strBuilder.append( "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n");
		this.strBuilder.append(  "      </IconStyle>\r\n");
		this.strBuilder.append( "    </Style>" );
		
		robotFruit_Kml();
	}

	/**
	 *  this method initialize the Robots and the fruit to KML
	 */
	public void robotFruit_Kml()
	{
		this.strBuilder.append(" <Style id=\"robot\">\r\n" );
		this.strBuilder.append( "      <IconStyle>\r\n" );
		this.strBuilder.append("        <Icon>\r\n" );
		this.strBuilder.append( "          <href>http://maps.google.com/mapfiles/kml/pal4/icon26.png></href>\r\n" );
		this.strBuilder.append( "        </Icon>\r\n" );
		this.strBuilder.append("        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" );
		this.strBuilder.append( "      </IconStyle>\r\n");
		this.strBuilder.append(  "    </Style>");


		this.strBuilder.append(     " <Style id=\"banana\">\r\n" );
		this.strBuilder.append(    "      <IconStyle>\r\n" );
		this.strBuilder.append(    "        <Icon>\r\n" );
		this.strBuilder.append(    "          <href>http://maps.google.com/mapfiles/kml/pal5/icon49.png</href>\r\n" );
		this.strBuilder.append("        </Icon>\r\n" );
		this.strBuilder.append( "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" );
		this.strBuilder.append(  "      </IconStyle>\r\n" );
		this.strBuilder.append(  "    </Style>" );
		this.strBuilder.append(" <Style id=\"apple\">\r\n" );
		this.strBuilder.append( "      <IconStyle>\r\n" );
		this.strBuilder.append( "        <Icon>\r\n" );
		this.strBuilder.append( "          <href>http://maps.google.com/mapfiles/kml/pal5/icon56.png</href>\r\n" );
		this.strBuilder.append( "        </Icon>\r\n" );
		this.strBuilder.append(  "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" );
		this.strBuilder.append("      </IconStyle>\r\n" );
		this.strBuilder.append( "    </Style>" );
	}

	/**
	 * the function enter the kml the location of each element
	 * @param id the id of the robot
	 * @param position the position of the fruit
	 */
	public void placeMark(String id, String position)
	{
		LocalDateTime localTime = LocalDateTime.now();
		this.strBuilder.append( "    <Placemark>\r\n" );
		this.strBuilder.append(    "      <TimeStamp>\r\n" );
		this.strBuilder.append(      "        <when>" + localTime+ "</when>\r\n" );
		this.strBuilder.append(     "      </TimeStamp>\r\n" );
		this.strBuilder.append(       "      <styleUrl>#" + id + "</styleUrl>\r\n" );
		this.strBuilder.append(        "      <Point>\r\n" );
		this.strBuilder.append(        "        <coordinates>" + position + "</coordinates>\r\n" );
		this.strBuilder.append(        "      </Point>\r\n" );
		this.strBuilder.append(        "    </Placemark>\r\n");
	}

	/**
	 *  this method close the kml file
	 */
	public void close_KML()
	{
		this.strBuilder.append("  </Document>\r\n");
		this.strBuilder.append("</kml>");
	}

	/**
	 * this method save the kml file
	 * @throws IOException
	 */
	public void save_KML() throws IOException {
		try {

			File f=new File(MyGameGUI.level+".kml");
			PrintWriter pWriter=new PrintWriter(f);
			pWriter.write( this.strBuilder.toString());
			pWriter.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}



}