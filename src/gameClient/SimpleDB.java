package gameClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;

import utils.StdDraw;
/**
 * This class represents a simple example of using MySQL Data-Base.
 * Use this example for writing solution. 
 * @author boaz.benmoshe
 *
 */
public class SimpleDB 
{
	public static HashMap<Integer, Integer> scores= new HashMap<Integer, Integer>();
    public static final String jdbcUrl = "jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
    public static final String jdbcUser = "student";
    public static final String jdbcUserPassword = "OOP2020student";
	

	/**
     * Simple main for demonstrating the use of the Data-base
     *
     * @param args
	 * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException 
    {
//        int id1 = 3; 
//        int level = 0;
        
        printLog();
//        String kml = Playingthegame.kmlstring.toString();
//        System.out.println("* KML file example: **");
//        System.out.println(kml);
    }

   
    static int CountGame = 0;
    static int LevelGame = 0;
    static int GradeGame = 0;
    static int placeInClass = 0;
    static int Moves = 0;
    
    /**
     *  prints all the games as played by the users (in the database).
     */
    
    public static void printLog() throws InterruptedException
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                    DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            Statement statement = connection.createStatement();
            String allCustomersQuery = "SELECT * FROM Logs;";
            ResultSet resultSet = statement.executeQuery(allCustomersQuery);
            switch (MyGameGUI.level) 
            {
                case 0: 
                {
                	 Moves= 290;
                    break;
                }
				case 1: 
				{
					 Moves=580;
					break;
				}
				case 3: 
				{
					 Moves=580;
					break;
				}
				case 5: 
				{
					 Moves=500;
					break;
				}
				case 9:
				{
					 Moves=580;
					break;
				}
				case 11: 
				{
					 Moves=580;
					break;
				}
				case 13: 
				{
					 Moves=580;
					break;
				}
				case 16: 
				{
					 Moves=290;
					break;
				}
				case 19: 
				{
					 Moves=580;
					break;
				}
				case 20: 
				{
					 Moves=290;
					break;
				}
				case 23: 
				{
					 Moves=1140;
					break;
				}

            }
            
            while (resultSet.next()) 
            {
                if (MyGameGUI.level == resultSet.getInt("levelID")) 
                {
                    int id = resultSet.getInt("UserID");
                    if (id == MyGameGUI.idStudent) 
                    {
                    	CountGame++;
                        LevelGame=resultSet.getInt("levelID");
                        int score= resultSet.getInt("score");
                        if (GradeGame<score) 
                        {
                        	GradeGame= score;
                        }
                    }
                }

            }
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            statement = connection.createStatement();
            allCustomersQuery = "SELECT * FROM Logs;";
            resultSet = statement.executeQuery(allCustomersQuery);
            while (resultSet.next()) 
            {
                int score = resultSet.getInt("score");
                int moves = resultSet.getInt("moves");
                int User_id = resultSet.getInt("UserID");
                int levelID = resultSet.getInt("levelID");
                    if (GradeGame< score && moves <= Moves &&   levelID == MyGameGUI.level) 
                    {
                        if (scores.get(User_id) == null) 
                        {
                        	scores.put(User_id, score);
                            placeInClass++;
                            System.out.println("Id: " + resultSet.getInt("UserID") + ", Level: " + resultSet.getInt("levelID") + ",Move: " + resultSet.getInt("moves") + ",Time: "
                                    + resultSet.getDate("time") + ",Score: " + resultSet.getInt("score"));
                        }

                    }

                }
            
            JOptionPane.showMessageDialog(null,"Your score: "+MyGameGUI.grade+"\nLevel Of Game: " + LevelGame+ 
            		"\n The times you played : " + CountGame +"\n Your place in class:  " +  
            				placeInClass+"\n The high value you got in this level:  "+ GradeGame  );
           
			String kmlString=getKML(MyGameGUI.idStudent,MyGameGUI.level);
			System.out.println(kmlString);
            resultSet.close();
            statement.close();
            connection.close();

        } 
        catch (SQLException sqle)
{
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        } catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
	}

    /**
     * this function returns the KML string as stored in the database (userID, level);
     *
     * @param id
     * @param level
     * @return
     */
    
    public static String getKML(int id, int level) 
    {
        String ans= null;
        String allCustomersQuery = "SELECT * FROM Users where userID=" + id + ";";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                    DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(allCustomersQuery);
            if (resultSet != null && resultSet.next()) {
                ans = resultSet.getString("kml_" + level);
            }
        } catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static int allUsers() 
    {
        int ansCount = 0;
        String allCustomersQuery = "SELECT * FROM Users;";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                    DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(allCustomersQuery);
            while (resultSet.next()) 
            {
                System.out.println("Id: " + resultSet.getInt("UserID"));
                ansCount++;
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException sqle) 
        {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        } 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
        return ansCount;
    }
}