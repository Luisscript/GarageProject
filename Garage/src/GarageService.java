public class GarageService {
    private final Garage garage;

    public GarageService(Garage aGarage){
        this.garage = aGarage;
    }

    public boolean removeCar(String licensePlate){
        return garage.removeCar(licensePlate);
    }

    public void startCarEngine(Car car){
        car.turnOnEngine();
    }

    public void stopCarEngine(Car car){
        car.turnOffEngine();
    }
}