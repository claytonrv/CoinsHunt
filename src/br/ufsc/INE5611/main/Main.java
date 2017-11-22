package br.ufsc.INE5611.main;

import br.ufsc.INE5611.business.CacaMoedas;


public class Main {
	
	public static void main(String[] args) {
		Thread threadJogo = new Thread(new CacaMoedas());
		threadJogo.start();
	}

}
