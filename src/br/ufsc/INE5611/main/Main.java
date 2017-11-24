package br.ufsc.INE5611.main;

import br.ufsc.INE5611.business.CoinsHunt;


public class Main {
	
	public static void main(String[] args) {
		Thread gameThread = new Thread(new CoinsHunt());
		gameThread.start();
	}

}
