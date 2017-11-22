package br.ufsc.INE5611.threads;

import java.util.Date;

import br.ufsc.INE5611.model.BinaryTree;
import br.ufsc.INE5611.model.Dog;
import br.ufsc.INE5611.model.Hunter;
import br.ufsc.INE5611.model.Node;

public class HuntingThread extends Thread implements Runnable {

	private Dog huntingDog;
	private Hunter dogOwner;
	private BinaryTree forest;
	private int nextNodeToHunt;
	private int lastVisitedNode;
	private Date huntStart;
	private Date huntFinish;
	
	public HuntingThread(BinaryTree forest, Hunter hunter) {
		this.forest = forest;
		this.dogOwner = hunter;
		this.huntStart = new Date();
	}
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			this.huntingDog = this.dogOwner.getHuntingDog();
			int nextNode = this.getNextNodeToHunt(huntingDog);
			if(nextNode == 0){
				if(dogOwner.getCoins() < 50){
					huntingDog.deliveryCoins();
				}else {
					setHuntFinish(new Date());
				}
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				Node node = forest.find(nextNode);
				if(node.getDogOnThree() == null){
					if(this.getDogOwner().getCoins() < 50){
						this.getCoinsOnNode(node);
						node.setDogOnThree(null);
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						setHuntFinish(new Date());
					}
				}
			}
		}
	}
	
	private void getCoinsOnNode(Node node){
		node.setDogOnThree(huntingDog);
		if(node.getCoins() >= 3){
			int coinsToRemove = huntingDog.incrementCoins((int)(Math.random() * 3)+1);
			node.deliveryCoins(coinsToRemove);
			System.out.println("Cão "+huntingDog.getId()+" : encontrou "+coinsToRemove+" moedas na árvore "+node.getNumber());
			return;
		}else if(node.getCoins() == 2){
			int coinsToRemove = huntingDog.incrementCoins(2);
			node.deliveryCoins(coinsToRemove);
			System.out.println("Cão "+huntingDog.getId()+" : encontrou "+coinsToRemove+" moedas na árvore "+node.getNumber());
			return;
		}else if(node.getCoins() == 1){
			int coinsToRemove = huntingDog.incrementCoins(1);
			node.deliveryCoins(coinsToRemove);
			System.out.println("Cão "+huntingDog.getId()+" : encontrou "+coinsToRemove+" moedas na árvore "+node.getNumber());
			return;
		}else{
			while(node.getCoins() < 1){
				System.out.println("Cão "+huntingDog.getId()+" : não encontrou moedas na árvore "+node.getNumber()+" Aguardando até existirem moedas para retirar.");
				try{
					Thread.sleep(1500);
					this.getCoinsOnNode(forest.find(node.getNumber()));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private int getNextNodeToHunt(Dog dog){
		if((dog.getCoins() < 20) && ((dog.getOwner().getCoins() + dog.getCoins()) < 50)) {                                      
		    do {                                                   
		        this.nextNodeToHunt = (int)(Math.random() * 20);
		        try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    } while ((this.nextNodeToHunt == lastVisitedNode));
		} else {                                                   
		    this.nextNodeToHunt = 0;                                   
		}                    
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.nextNodeToHunt;                                 
	}

	public Hunter getDogOwner() {
		return dogOwner;
	}

	public void setDogOwner(Hunter dogOwner) {
		this.dogOwner = dogOwner;
	}

	public Date getHuntStart() {
		return huntStart;
	}

	public synchronized void setHuntStart(Date huntStart) {
		this.huntStart = huntStart;
	}

	public Date getHuntFinish() {
		return huntFinish;
	}

	public void setHuntFinish(Date huntFinish) {
		this.huntFinish = huntFinish;
	}

}
