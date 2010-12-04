package com.googlecode.waruma.rushhour.tests;

import java.util.List;

import junit.framework.TestCase;

import com.googlecode.waruma.rushhour.framework.GameState;
import com.googlecode.waruma.rushhour.framework.IGameWonObserver;
import com.googlecode.waruma.rushhour.framework.IPlayer;
import com.googlecode.waruma.rushhour.framework.IReachedDestinationObserver;

public class TestGameState extends TestCase {

	private class MockObserver implements IGameWonObserver {
		private boolean called = false;

		@Override
		public void updateGameWon() {
			called = true;
		}
	}

	private class MockSubject implements IPlayer {

		private List<IReachedDestinationObserver> observers;

		@Override
		public void registerReachedDestination(
				IReachedDestinationObserver eventTarget) {
			observers.add(eventTarget);
		}
	}

	private GameState gameState;
	private MockObserver mockObserver;

	private MockSubject mockSubject1;

	private MockSubject mockSubject2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		gameState = new GameState();

		mockObserver = new MockObserver();
		mockSubject1 = new MockSubject();
		mockSubject2 = new MockSubject();
	}

	// testet das Registrieren eines Observers beim Gamestate
	public void testRegisterGameWon() throws Exception {

		gameState.registerGameWon(mockObserver);

		mockObserver.updateGameWon();
		assertTrue(mockObserver.called);
	}

	public void testUpdateReachedDestinationBoth() {
		mockObserver.called = false;

		// den Mock-Observer bei GameState registrieren
		gameState.registerGameWon(mockObserver);

		// 2 Mock-Subjects hinzuf�gen
		gameState.addPlayer(mockSubject1);
		gameState.addPlayer(mockSubject2);

		// 1 Mock-Subject ist im Ziel
		gameState.updateReachedDestination(mockSubject1);

		// 2. und letzter Mock-Subject im Ziel
		gameState.updateReachedDestination(mockSubject2);

		// pr�fen, dass Mock-Observer benachrichtigt wird
		assertTrue(mockObserver.called);
	}

	public void testUpdateReachedDestinationOneOfTwo() {
		mockObserver.called = false;

		// den Mock-Observer bei GameState registrieren
		gameState.registerGameWon(mockObserver);

		// 2 Mock-Subjects hinzuf�gen
		gameState.addPlayer(mockSubject1);
		gameState.addPlayer(mockSubject2);

		// 1 Mock-Subject ist im Ziel
		gameState.updateReachedDestination(mockSubject1);

		// pr�fen, dass Mock-Observer nicht benachrichtigt wurde
		assertFalse(mockObserver.called);
	}

	public void testUpdateReachedDestinationTheOneAndOnly() {
		mockObserver.called = false;

		// den Mock-Observer bei GameState registrieren
		gameState.registerGameWon(mockObserver);

		// 1 Mock-Subject hinzuf�gen
		gameState.addPlayer(mockSubject1);

		// 1 Mock-Subject ist im Ziel
		gameState.updateReachedDestination(mockSubject1);

		// pr�fen, dass Mock-Observer nicht benachrichtigt wurde
		assertTrue(mockObserver.called);
	}
}
