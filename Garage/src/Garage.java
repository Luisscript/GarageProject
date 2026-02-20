import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Garage {

    private int MAXPARKINGSPACE;
    private int usedParkingSpace;
    private String garageName;
    private String size;
    private Map<String, Car> cars = new HashMap<>();

    public Garage(String garageName, Integer size) throws InvalidGarageEntryException{
        this.garageName = garageName;
        if(garageName.isBlank() || size == null){
            throw new InvalidGarageEntryException("Invalid arguments");
        }else {
            if (size.equals(1)) {
                this.MAXPARKINGSPACE = 7;
                this.size = "Small";
            } else if (size.equals(2)) {
                this.MAXPARKINGSPACE = 15;
                this.size = "Medium";
            } else if (size.equals(3)) {
                this.MAXPARKINGSPACE = 20;
                this.size = "Large";
            } else {
                throw new InvalidGarageEntryException("Invalid arguments");
            }
        }
    }



    public void addCar(String licensePlate, Car car) {
        if(this.usedParkingSpace < this.MAXPARKINGSPACE){
           this.cars.put(licensePlate, car);
            this.usedParkingSpace++;
        }else{
            throw new IllegalStateException("This Garage is full!");
        }
    }

    public boolean removeCar(String licensePlate) throws LicensePlateNotFoundException{
        boolean flag = false;
        Car car = this.cars.get(licensePlate);
        if(car!=null){
            this.cars.remove(licensePlate);
            return flag = true;
        }throw new LicensePlateNotFoundException("License plate not founded in garage!");
    }

    public Car getCar(String licensePlate) throws LicensePlateNotFoundException{
        Car car  =  this.cars.get(licensePlate);
        if(car == null){
            throw new LicensePlateNotFoundException("License plate not found!");
        }
        return car;
    }

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

    public int getMaxParkingSpace(){return MAXPARKINGSPACE;}

    public int getUsedParkingSpace(){return usedParkingSpace;}

    public String getGarageName(){return garageName;}

    public String getSize(){return size;}

    public Map<String, Car> getCarsParkedMap(){return this.cars;}
}