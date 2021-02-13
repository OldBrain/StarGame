package ru.geeckbrains.stargame;


import com.badlogic.gdx.Game;
import ru.geeckbrains.stargame.screen.MenuScreen;

public class StartGame extends Game {

	@Override
	public void create () {
		setScreen(new MenuScreen(this));
	}

}