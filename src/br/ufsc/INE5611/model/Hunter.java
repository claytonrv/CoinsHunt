package br.ufsc.INE5611.model;

import br.ufsc.INE5611.enumeration.HunterColorEnum;

public class Hunter {

	private HunterColorEnum color;
	private int coins;
	private Dog [] dogs;
	private int lastHuntingDog;
	
	public Hunter(HunterColorEnum color, Dog firstDog, Dog SecondDog) {
		this.color = color;
		this.coins = 0;
		dogs = new Dog [2];
		dogs[0] = firstDog;
		dogs[1] = SecondDog;
		dogs[0].setOwner(this);
		dogs[1].setOwner(this);
		dogs[0].setHunting(true);
		lastHuntingDog = 0;
	}
	
	public void switchDogs(){
		if(!this.dogs[lastHuntingDog].isHunting()){
			if(lastHuntingDog == 0){
				this.dogs[1].setHunting(true);
				this.dogs[0].setHunting(false);
				this.lastHuntingDog = 1;
			}else{
				this.dogs[1].setHunting(false);
				this.dogs[0].setHunting(true);
				this.lastHuntingDog = 0;
			}
		}	
	}
	
	public Dog getHuntingDog(){
		if(this.dogs[lastHuntingDog].isHunting()){
			if(lastHuntingDog == 0){
				return dogs[0];
			}else{
				return dogs[1];
			}
		}else {
			return null;
		}
	}
	
	public void stopDogs(){
		this.dogs[0].setHunting(false);
		this.dogs[1].setHunting(false);
	}
	
	public void receiveCoins(int coinsAmount){
		this.switchDogs();
		this.coins += coinsAmount;
	}
	
	public HunterColorEnum getColor()  {
		return color;
	}
	public void setColor(HunterColorEnum color) {
		this.color = color;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	
}
