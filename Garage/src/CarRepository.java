import java.util.Map;
import java.util.HashMap;

public class CarRepository {
    // global point access instance
    private static CarRepository carRepository = new CarRepository();

    // state fields
    private Map<String, Car> cars;

    private CarRepository(){// this guarantees singularity
        this.cars = new HashMap<>();
    }

    public static CarRepository getInstance(){
        return carRepository;
    }

    public void putCar(Car car){
        this.cars.put(car.getLicensePlate(), car);
    }

    public Car getCar(String licensePlate) throws CarNotFoundException{
        Car car = cars.get(licensePlate);
        if(car == null){
            throw new CarNotFoundException(licensePlate+" not found");
        }
        return car;
    }
    public int lenCarsRepository(){return this.cars.size();}

    // for use in serialization
    public Map<String, Car> getCarsRepository(){return this.cars;}
}