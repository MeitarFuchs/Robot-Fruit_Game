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
public class SimpleDB {
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
    public static void main(String[] args) throws InterruptedException {
        int id1 = 3; 
        int level = 0;
        printLog();
//        String kml = Playingthegame.kmlstring.toString();
//        System.out.println("* KML file example: **");
//        System.out.println(kml);
    }

   
    static int CountGame = 0;
    static int Level = 0;
    static int Grade = 0;
    static int placeInClass = 0;
    static int Move = 0;
    
    /**
     *  prints all the games as played by the users (in the database).
     */
    
    public static void printLog() throws InterruptedException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                    DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            Statement statement = connection.createStatement();
            String allCustomersQuery = "SELECT * FROM Logs;";
            ResultSet resultSet = statement.executeQuery(allCustomersQuery);
            switch (MyGameGUI.level) {
                case 0: {
                	 Move= 290;
                    break;
                }
				case 1: {
					 Move=580;
					break;
				}
				case 3: {
					 Move=580;
					break;
				}
				case 5: {
					 Move=500;
					break;
				}
				case 9: {
					 Move=580;
					break;
				}
				case 11: {
					 Move=580;
					break;
				}
				case 13: {
					 Move=580;
					break;
				}
				case 16: {
					 Move=290;
					break;
				}
				case 19: {
					 Move=580;
					break;
				}
				case 20: {
					 Move=290;
					break;
				}
				case 23: {
					 Move=1140;
					break;
				}

            }
            while (resultSet.next()) {

                if (MyGameGUI.level == resultSet.getInt("levelID")) {
                  
                    int id = resultSet.getInt("UserID");
                    if (id == MyGameGUI.idStudent) {
                      
                    	CountGame++;
                      
                        Level = resultSet.getInt("levelID");
                        int score = resultSet.getInt("score");
                        if (Grade<score) {
                        	Grade = score;
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
            while (resultSet.next()) {
                int score = resultSet.getInt("score");
                int moves = resultSet.getInt("moves");
                int Userid = resultSet.getInt("UserID");
                int levelID = resultSet.getInt("levelID");

                    if (Grade < score && moves <=  Move && levelID == MyGameGUI.level) {
                        if (scores.get(Userid) == null) {
                        	scores.put(Userid, score);
                            placeInClass++;
                            System.out.println("Id: " + resultSet.getInt("UserID") + ", Level: " + resultSet.getInt("levelID") + ",Move: " + resultSet.getInt("moves") + ",Time: "
                                    + resultSet.getDate("time") + ",Score: " + resultSet.getInt("score"));
                        }

                    }

                }
            
            JOptionPane.showMessageDialog(null,"Your score: "+MyGameGUI.grade+"\nLevel Of Game: " + Level + "\n The times you played : " 
            + CountGame +"\n Your place in class:  " +  placeInClass+"\n The high value you got in this level:  "  + Grade  );
           
			String kml=getKML(MyGameGUI.idStudent,MyGameGUI.level);
			System.out.println(kml);
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        } catch (ClassNotFoundException e) {
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
    
    public static String getKML(int id, int level) {
        String ans = null;
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

    public static int allUsers() {
        int ans = 0;
        String allCustomersQuery = "SELECT * FROM Users;";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                    DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(allCustomersQuery);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("UserID"));
                ans++;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ans;
    }
}
//	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
//	public static final String jdbcUser="student";
//	public static final String jdbcUserPassword="OOP2020student";
//	//public static StdDraw st = new StdDraw();
//	public static HashMap<Integer, Integer> allans= new HashMap<Integer, Integer>();
//	public static HashMap <Integer, HashMap<Integer,Integer>> allscorealllevel= new HashMap<Integer,  HashMap<Integer,Integer>>();
//	public static int howManyGame = 0;
//	public static int Level = 0; 
//	public static int	value = 0; 
//	public static int placeInClass = 0; 
//	public static int	MaxMove = 0;
//	public static String s="";
//	public static int level;
//
//	/**
//	 * Simple main for demonstrating the use of the Data-base
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		int id1 = 315325605;  // "dummy existing ID  
//		int level = MyGameGUI.level;
//		//allUsers();
//		printLog();
//		String kml = getKML(id1,level);
//		System.out.println("***** KML file example: ******");
//		System.out.println(kml);
//	}
//	/** simply prints all the games as played by the users (in the database).
//	 * 
//	 */
//	public static void printLog() {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection connection = 
//					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
//			Statement statement = connection.createStatement();
//			String allCustomersQuery = "SELECT * FROM Logs;";
//			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
//			switch (MyGameGUI.level) 
//			{
//			case 0: {
//				MaxMove = 290;
//				break;
//			}
//			case 1: {
//				MaxMove=580;
//				break;
//			}
//			case 3: {
//				MaxMove=580;
//				break;
//			}
//			case 5: {
//				MaxMove=500;
//				break;
//			}
//			case 9: {
//				MaxMove=580;
//				break;
//			}
//			case 11: {
//				MaxMove=580;
//				break;
//			}
//			case 13: {
//				MaxMove=580;
//				break;
//			}
//			case 16: {
//				MaxMove=290;
//				break;
//			}
//			case 19: {
//				MaxMove=580;
//				break;
//			}
//			case 20: {
//				MaxMove=290;
//				break;
//			}
//			case 23: {
//				MaxMove=1140;
//				break;
//			}
//			}
//
//			while(resultSet.next())
//			{
//				int ID = resultSet.getInt("UserID");
//				int grade = resultSet.getInt("score");
//				level= resultSet.getInt("levelID");
//				if (ID == MyGameGUI.idStudent) {
//					if (allscorealllevel.get(ID)==null ){
//						allscorealllevel.put(ID,new HashMap<Integer, Integer>());
//						allscorealllevel.get(ID).put(level,grade);
//					}
//					else
//					{
//						if(allscorealllevel.get(ID).get(level)<grade){
//							allscorealllevel.get(ID).remove(level);
//							allscorealllevel.get(ID).put(level,grade);
//						}
//					}
//				}
//				if (MyGameGUI.level == level) {
//					//System.out.println("Id: " + resultSet.getInt("UserID")+","+resultSet.getInt("levelID")+","+resultSet.getInt("moves")+","+resultSet.getDate("time")+", "+resultSet.getInt("score"));
//					// int id = resultSet.getInt("UserID");
//					System.out.println("this is id :"+ID);
//					if (ID == MyGameGUI.idStudent) {
//						// System.out.println("i am herer!!!!!!");
//						howManyGame++;
//						//System.out.println("Id: " + resultSet.getInt("UserID") + ", Level: " + resultSet.getInt("levelID") + ",Move: " + resultSet.getInt("moves") + ",Time: " + resultSet.getDate("time") + ",Score: " + resultSet.getInt("score"));
//						Level = resultSet.getInt("levelID");
//						if (grade > value) {
//							value = grade;
//						}
//					}
//				}
//			}
//			Class.forName("com.mysql.jdbc.Driver");
//			connection =
//					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
//			statement = connection.createStatement();
//			allCustomersQuery = "SELECT * FROM Logs;";
//			resultSet = statement.executeQuery(allCustomersQuery);
//			while (resultSet.next()) {
//				int score = resultSet.getInt("score");
//				int moves = resultSet.getInt("moves");
//				int id = resultSet.getInt("UserID");
//				int levelID = resultSet.getInt("levelID");
//
//				//System.out.println("score of all class: " + score + " this is your moves: " + moves);
//				//                if (MyGameGUI.count <= MaxMove) {
//				if (MyGameGUI.grade < score && moves <= MaxMove && levelID == MyGameGUI.level) {
//					if (allans.get(id) == null || allans.get(id)<score) {
//						allans.remove(id,allans.get(id));
//						allans.put(id, score);
//						placeInClass++;
//						System.out.println("Id: " + resultSet.getInt("UserID") + ", Level: " + resultSet.getInt("levelID") + ",Move: " + resultSet.getInt("moves") + ",Time: "
//								+ resultSet.getDate("time") + ",Score: " + resultSet.getInt("score"));
//					}
//
//				}
//
//			}
//
//			/*StdDraw.setCanvasSize(2000,800);
//            StdDraw.setXscale(-50,50);
//            StdDraw.setYscale(-50,50);*/
//			for (int i = 0; i <allscorealllevel.size() ; i++) {
//				s+="In level: "+ allscorealllevel.get(MyGameGUI.idStudent) +"Your best score is: "+ allscorealllevel.get(MyGameGUI.idStudent).get(level) +"\n";
//
//			}
//
//			StdDraw.text(150,120,"we place on class: " + placeInClass,3);
//			//Object game_select = JOptionPane.showInputDialog(null, "Choose a mood", "Note", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("robot_tec.jpg"), p, p[0]);
//			JOptionPane.showMessageDialog(null,"Current game: " + Level + "\n Your grade is: "+MyGameGUI.grade+       "\n Place on class: " +
//					"" + placeInClass+
//					"\n High value of this game: " +
//					"" + value + "\n Num of game we play (this game): " + howManyGame+"\nyour score in every game until now:"+s.toString());
//			//            JOptionPane.showMessageDialog(null,"your score in game 0 is:\n");
//			System.err.println("\n We place on class: " + placeInClass);
//
//			System.err.println("high value of this game " + value);
//			System.err.println("num of game we play: " + howManyGame);
//
////			MyGameGUI.t1.join();
//
//			System.out.println("****KML IS*****");
//			String kml=getKML(MyGameGUI.idStudent,MyGameGUI.level);
//			System.out.println(kml);
//			resultSet.close();
//			statement.close();		
//			connection.close();		
//		}
//
//		catch (SQLException sqle) {
//			System.out.println("SQLException: " + sqle.getMessage());
//			System.out.println("Vendor Error: " + sqle.getErrorCode());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		}
//	}
//
//	/**
//	 * this function returns the KML string as stored in the database (userID, level);
//	 * @param id
//	 * @param level
//	 * @return
//	 */
//	public static String getKML(int id, int level) {
//		String ans = null;
//		String allCustomersQuery = "SELECT * FROM Users where userID="+id+";";
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection connection = 
//					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
//			Statement statement = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
//			if(resultSet!=null && resultSet.next()) {
//				ans = resultSet.getString("kml_"+level);
//			}
//		}
//		catch (SQLException sqle) {
//			System.out.println("SQLException: " + sqle.getMessage());
//			System.out.println("Vendor Error: " + sqle.getErrorCode());
//		}
//
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return ans;
//	}
//
//	public static int allUsers() {
//		int ans = 0;
//		String allCustomersQuery = "SELECT * FROM Users;";
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection connection = 
//					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
//			Statement statement = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
//			while(resultSet.next()) {
//				System.out.println("Id: " + resultSet.getInt("UserID"));
//				ans++;
//			}
//			resultSet.close();
//			statement.close();		
//			connection.close();
//		}
//		catch (SQLException sqle) {
//			System.out.println("SQLException: " + sqle.getMessage());
//			System.out.println("Vendor Error: " + sqle.getErrorCode());
//		}
//
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return ans;
//	}
//}
//
