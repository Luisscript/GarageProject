import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Garage {

    private int MAXPARKINGSPACE;
    private int usedParkingSpace;
    private final String garageName;
    private String size;
    private Map<String, Car> cars = new HashMap<>();

    public Garage(String garageName, Integer size){
        this.garageName = garageName;
        if(size.equals(1)){
            this.MAXPARKINGSPACE = 7;
            this.size = "Small";
        }else if(size.equals(2)){
            this.MAXPARKINGSPACE = 15;
            this.size = "Medium";
        }else if(size.equals(3)){
            this.MAXPARKINGSPACE = 20;
            this.size = "Large";
        }else{
            throw new IllegalArgumentException("Invalid arguments");
        }
    }

    public void addCar(String licensePlate, Car car){
        if(this.usedParkingSpace < this.MAXPARKINGSPACE){
           this.cars.put(licensePlate, car);
            this.usedParkingSpace++;
        }else{
            throw new IllegalStateException("This Garage is full!");
        }
    }

    public Car getCar(String licensePlate){
        Car car  =  this.cars.get(licensePlate);
        if(car == null){
            throw new NullPointerException("Car not found!");
        }
        return car;
    }

//    public void viewCars(){
//        for(Map.Entry<String, Car> entry : this.cars.entrySet()){
//            if(entry.getValue() != null){
//                entry.getValue().carFeatures();
//            }
//            return;
//        }
//    }

    public List<String> garageInfos(){
        List<String> toreturn = new ArrayList<>();
        toreturn.add(this.garageName);
        toreturn.add(this.size);
        return toreturn;
    }

    public List<Object> garageCapacity(){
        List<Object> toreturn = new ArrayList<>();
        toreturn.add(this.MAXPARKINGSPACE);
        toreturn.add(this.usedParkingSpace);
        toreturn.add(this.MAXPARKINGSPACE-this.usedParkingSpace);
        return toreturn;
    }
}