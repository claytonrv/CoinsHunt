package br.ufsc.INE5611.model;

public class Dog {
	
	private boolean hunting;
    private Hunter owner;
    private int coins;
    private Node lastVisitedNode;
    private String id;
	
    public int incrementCoins(int coinsAmount){
    	int actualCoinAmount = this.coins;
    	if(this.hunting){
	    	if(this.coins < 20){
		    	if((this.coins+coinsAmount)>20){
		    		this.coins = 20;
		    	}else{
		    		this.coins += coinsAmount;
		    	}
	    	}
    	}
    	return this.coins-actualCoinAmount;
    }
    
    public synchronized void deliveryCoins(){
    	this.hunting = false;
    	System.out.println("Cão "+this.getId()+" entregando "+this.coins+" moedas ao seu dono.");
    	this.owner.receiveCoins(this.coins);
    	this.coins = 0;
    }
    
    public boolean isHunting() {
		return hunting;
	}
	public void setHunting(boolean hunting) {
		this.hunting = hunting;
	}
	public Hunter getOwner() {
		return owner;
	}
	public void setOwner(Hunter owner) {
		this.owner = owner;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}

	public Node getLastVisitedNode() {
		return lastVisitedNode;
	}

	public void setLastVisitedNode(Node lastVisitedNode) {
		this.lastVisitedNode = lastVisitedNode;
	}
	
	public String getId(){
		return this.id;
	}
    
	public void setId(String id){
		this.id = id;
	}
}
