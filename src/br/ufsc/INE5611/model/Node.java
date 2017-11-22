package br.ufsc.INE5611.model;

public class Node {

    private int number;
    private int coins;
    private Node rightNode;
    private Node leftNode;
    private Dog dogOnThree;

    public Node (int number) {
        this.number = number;
        this.coins = 4;
        this.leftNode = null;
        this.rightNode = null;
    }

    public synchronized void setCoins(int coins) {
        this.coins = coins;
        notifyAll();
    }

    public synchronized int getCoins() {
        return coins;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
	public Dog getDogOnThree() {
		return dogOnThree;
	}

	public synchronized void setDogOnThree(Dog dogOnThree) {
		this.dogOnThree = dogOnThree;
		notifyAll();
	}

    public synchronized void receiveCoins(int amount){
        if(amount > 4 || (amount+coins) > 4){
            throw new IllegalArgumentException();
        }else {
            this.setCoins(coins+amount);
            this.notifyAll();
        }
    }

    public synchronized int deliveryCoins(int amount){
        if(this.coins <= 0){
           return 0;
        }else{
        	if((this.coins - amount) < 0){
        		int cns = this.coins;
        		this.coins = 0;
        		return cns;
        	}else{
        		this.coins -= amount;
        	}
            this.notifyAll();
            return amount;
        }
    }
}