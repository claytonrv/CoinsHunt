package br.ufsc.INE5611.threads;

import br.ufsc.INE5611.model.BinaryTree;
import br.ufsc.INE5611.model.Node;

public class HelperDogThread extends Thread implements Runnable{

	private BinaryTree forest;
	private int nextNodeToInsertCoins;
	private int lastVisitedNode;
	
	
	public HelperDogThread(BinaryTree forest) {
		this.forest = forest;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){
			int nextNode = this.getNextNodeToInsertCoins();
			Node next = forest.find(nextNode);
			if(next.getCoins() < 4){
				this.deliveryCoins(next);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void deliveryCoins(Node node){
		node.receiveCoins(1);
		System.out.println("Cão ajudante : Colocando 1 moeda na árvore "+node.getNumber());
    }
	
	public int getNextNodeToInsertCoins(){                                      
	    do {                                                   
	        this.nextNodeToInsertCoins = (int)(Math.random() * 20);       
	    } while ((this.nextNodeToInsertCoins == lastVisitedNode));                 
		return this.nextNodeToInsertCoins;                                 
	}

}
