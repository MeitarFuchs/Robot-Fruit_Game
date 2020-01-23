package testing;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import gameClient.GameAlgorithms;

class AlgorithmsGameTest 
{
	
	@Test
	public void howManyRobots() throws JSONException 
	{
		GameAlgorithms algo=new GameAlgorithms();
		game_service game = Game_Server.getServer(5);
		game.addRobot(0);
		game.startGame();
		
		int ans=algo.howManyRobots(game);
		if(ans!=1)
		{
			fail();
		}
		game.stopGame();
		game = Game_Server.getServer(23);
		game.addRobot(0);
		game.addRobot(1);
		game.addRobot(2);
		game.startGame();
		
		int ans2=algo.howManyRobots(game);
		if(ans2!=3)
		{
			fail();
		}
		game.stopGame();
	}

}
