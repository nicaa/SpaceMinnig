package spaceShip;

import java.util.ArrayList;

public class AllComponets {
	public static ArrayList<Drill> drillList = new ArrayList<Drill>();
	public static ArrayList<Fuel> fuelList = new ArrayList<Fuel>();
	public static ArrayList<Cargo> cargoList = new ArrayList<Cargo>();

	public AllComponets() {
		initDrills();
		initFuel();
		initCargo();
	}

	public void initDrills() {
		Drill drill1 = new Drill(1, 0, "Drill1", 0);
		Drill drill2 = new Drill(2, 250, "drill 2", 0);
		Drill drill3 = new Drill(3, 650, "drill 3", 0);
		Drill drill4 = new Drill(4, 1250, "drill 4", 0);
		
		drillList.add(drill1);
		drillList.add(drill2);
		drillList.add(drill3);
		drillList.add(drill4);
	}

	public void initFuel() {
		Fuel fuel1 =  new Fuel(1, 0, "Fuel 1", 1, 0, 100);
		Fuel fuel2 = new Fuel(2, 400, "fuel 2", 0, 0, 150);
		Fuel fuel3 = new Fuel(3, 800, "fuel 3", 0, 0, 200);
		Fuel fuel4 = new Fuel(4, 1400, "fuel 4", 0, 0, 250);
		fuel1.setTotalFuel(fuel1.getFuelMax());
		
		fuelList.add(fuel1);
		fuelList.add(fuel2);
		fuelList.add(fuel3);
		fuelList.add(fuel4);

	}

	public void initCargo() {
		Cargo cargo1 = new Cargo(1, 0, "Cargo 1", 1, 0, 9999);
		Cargo cargo2 = new Cargo(2, 350, "cargo 2", 0, 0, 300);
		Cargo cargo3 = new Cargo(3, 700, "cargo 3", 0, 0, 450);
		Cargo cargo4 = new Cargo(4, 1350, "cargo 4", 0, 0, 600);
		
		cargoList.add(cargo1);
		cargoList.add(cargo2);
		cargoList.add(cargo3);
		cargoList.add(cargo4);
		
	}

}
