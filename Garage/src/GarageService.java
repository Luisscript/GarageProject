public class GarageService {
    private Garage garage;

    public GarageService(Garage aGarage){
        this.garage = aGarage;
    }

    public void startCarEngine(Car car){
        car.turnOnEngine();
    }

    public void stopCarEngine(Car car){
        car.turnOffEngine();
    }
}