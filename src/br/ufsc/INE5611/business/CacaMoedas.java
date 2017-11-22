package br.ufsc.INE5611.business;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.ufsc.INE5611.enumeration.HunterColorEnum;
import br.ufsc.INE5611.model.BinaryTree;
import br.ufsc.INE5611.model.Dog;
import br.ufsc.INE5611.model.Hunter;
import br.ufsc.INE5611.model.Node;
import br.ufsc.INE5611.threads.HelperDogThread;
import br.ufsc.INE5611.threads.HuntingThread;

public class CacaMoedas implements Runnable{

	private BinaryTree forest;
	private Hunter greenHunter;
	private Hunter blueHunter;
	private Hunter yellowHunter;
	private HuntingThread yellowThread;
	private HuntingThread greenThread;
	private HuntingThread blueThread;
	private HelperDogThread helperThread;
	private ExecutorService executor;
	private HuntingThread winner;
	private HuntingThread seccondPlace;
	private HuntingThread lastOne;
	
	public CacaMoedas() {
		super();
		this.forest = new BinaryTree();
		this.winner = null;
		this.initForest();
		this.initHuntersAndDogs();
	}
	
	@Override
	public void run(){
		executor = Executors.newFixedThreadPool(4);
		yellowThread = new HuntingThread(forest, yellowHunter);
		greenThread = new HuntingThread(forest, greenHunter);
		blueThread = new HuntingThread(forest, blueHunter);
		helperThread = new HelperDogThread(forest);
		try {
			executor.execute(yellowThread);
			executor.execute(greenThread);
			executor.execute(blueThread);
			executor.execute(helperThread);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long yellowTime=0;
		long greenTime=0;
		long blueTime=0;
		while(winner == null){
			if(yellowThread.getDogOwner().getCoins() >= 50 && greenThread.getDogOwner().getCoins()>=50 && blueThread.getDogOwner().getCoins() >=50){
				if(yellowTime == 0 || blueTime == 0 || greenTime == 0){			
					if(yellowThread.getHuntFinish() != null && yellowTime == 0){
						yellowTime = (yellowThread.getHuntFinish().getTime() - yellowThread.getHuntStart().getTime());
						yellowThread.interrupt();
					}
					if(greenThread.getHuntFinish() != null && greenTime == 0){
						greenTime = (greenThread.getHuntFinish().getTime() - greenThread.getHuntStart().getTime());
						greenThread.interrupt();
					}
					if(blueThread.getHuntFinish() != null && blueTime == 0){
						blueTime = (blueThread.getHuntFinish().getTime() - blueThread.getHuntStart().getTime());
						blueThread.interrupt();
					}
				}else {
					helperThread.interrupt();
					if(yellowTime > 0 && greenTime > 0 && blueTime > 0){
						if(yellowTime < greenTime && yellowTime < blueTime){
							winner = yellowThread;
						}else if(greenTime < yellowTime && greenTime < blueTime){
							winner = greenThread;	
						}else if(blueTime < yellowTime && blueTime < greenTime){
							winner = blueThread;
						}
						if(winner != null){
							if(winner.getDogOwner().getColor().equals(HunterColorEnum.YELLOW)){
								if(greenTime < blueTime){
									seccondPlace = greenThread;
									lastOne = blueThread;
								}else {
									seccondPlace = blueThread;
									lastOne = greenThread;
								}
							}else if(winner.getDogOwner().getColor().equals(HunterColorEnum.GREEN)){
								if(yellowTime < blueTime){
									seccondPlace = yellowThread;
									lastOne = blueThread;
								}else {
									seccondPlace = blueThread;
									lastOne = yellowThread;
								}
							}else {
								if(yellowTime < greenTime){
									seccondPlace = yellowThread;
									lastOne = greenThread;
								}else {
									seccondPlace = greenThread;
									lastOne = yellowThread;
								}
							}
						}
					}
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("O caçador vencedor foi: "+winner.getDogOwner().getColor());
		System.out.println("Seus cachorros encontraram "+winner.getDogOwner().getCoins()+" em "+(winner.getHuntFinish().getTime() - winner.getHuntStart().getTime())+" milisegundos.");
		System.out.println("Segundo lugar: "+seccondPlace.getDogOwner().getColor()+" com "+seccondPlace.getDogOwner().getCoins()+" em "+(seccondPlace.getHuntFinish().getTime()-seccondPlace.getHuntStart().getTime())+" milisegundos.");
		System.out.println("Terceiro lugar: "+lastOne.getDogOwner().getColor()+" com "+lastOne.getDogOwner().getCoins()+" em "+(lastOne.getHuntFinish().getTime()-lastOne.getHuntStart().getTime())+" milisegundos.");
		System.exit(0);
 	}
	
	private void initForest(){
        ArrayList<Node> treeList = new ArrayList<Node>();
        for (int i=2; i<=20; i++){
            Node node = new Node(i);
            treeList.add(node);
        }
        while (!treeList.isEmpty()) {
            int nextNodePosition = (int) (Math.random() * treeList.size());
            Node node = treeList.get(nextNodePosition);
            treeList.remove(node);
            forest.insert(node.getNumber());
        }
	}
	
	private void initHuntersAndDogs(){
		Dog fistYellowDog = new Dog();
		Dog secondYellowDog = new Dog();
		fistYellowDog.setId(HunterColorEnum.YELLOW.getDescription()+" 1");
		secondYellowDog.setId(HunterColorEnum.YELLOW.getDescription()+" 2");
		this.yellowHunter = new Hunter(HunterColorEnum.YELLOW, fistYellowDog, secondYellowDog);
		Dog fistGreenDog = new Dog();
		Dog secondGreenDog = new Dog();
		fistGreenDog.setId(HunterColorEnum.GREEN.getDescription()+" 1");
		secondGreenDog.setId(HunterColorEnum.GREEN.getDescription()+" 2");
		this.greenHunter = new Hunter(HunterColorEnum.GREEN, fistGreenDog, secondGreenDog);
		Dog fistBlueDog = new Dog();
		Dog secondBlueDog = new Dog();
		fistBlueDog.setId(HunterColorEnum.BLUE.getDescription()+" 1");
		secondBlueDog.setId(HunterColorEnum.BLUE.getDescription()+" 2");
		this.blueHunter = new Hunter(HunterColorEnum.BLUE, fistBlueDog, secondBlueDog);
	}
}
