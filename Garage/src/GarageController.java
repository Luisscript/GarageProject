import java.security.InvalidParameterException;
import java.util.List;

public class GarageController {
    private Garage garage = null;
    private GarageService service = null;
    private ConsoleMenu cMenu = null;

    public GarageController(){}

    public GarageController(String garName, String size){
        this.garage = getGarage(garName, Integer.parseInt(size));
        this.service = getService(this.garage);
        this.cMenu = getConsoleMenu(this.service);
    }

    private static Garage getGarage(String garName, Integer size){
        return new Garage(garName, size);}

    private static GarageService getService(Garage garage){
        return new GarageService(garage);
    }

    private static ConsoleMenu getConsoleMenu(GarageService service){
        return new ConsoleMenu(service);
    }

    private static Car getCar(String automaker, String model, String color, String engineId, String licensePlate){
        return new Car(automaker, model, color, engineId, licensePlate);
    }

    private static Engine getEngine(String engineId, String engineType, String volume, String fuelType, String horsePower, String torque){
        return new Engine(engineId, engineType, volume, fuelType, horsePower, torque);
    }

    private int getLenMapEngines(){
        return Engine.enginesLenght();
    }

    private int getLenMapCars(){
        return Car.lenMapCars();
    }

    public static GarageController getGarageController(){
        List<String> garArgs = null;
        while(true) {
            try {
                garArgs = ConsoleMenu.getGarArgs();
                if(Integer.parseInt(garArgs.get(1)) < 1 || Integer.parseInt(garArgs.get(1)) > 3) {
                    throw new InvalidEntryException("Invalid garage size option!\nTry again!");
                }
            }catch(InvalidEntryException e){
                ConsoleMenu.printExcptMessages(e);
                continue;
            }
                break;
            }
            return new GarageController(garArgs.get(0), garArgs.get(1));
        }

    public void createCar(){
        if (getLenMapEngines() > 0) {
            while(true) {
                Car car = null;
                try {
                    List<String> carArgs = cMenu.createCarFlow();
                    car = getCar(carArgs.get(4), carArgs.get(0), carArgs.get(1), carArgs.get(2), carArgs.get(3));
                } catch (InvalidEntryException | InvalidCarEntryException | EngineNotFoundException |
                         InvalidLicensePlateException e) {
                    ConsoleMenu.printExcptMessages(e);
                }if(car != null){
                    return;
                }
            }
        }else {cMenu.printMessage("You cannot create a car without an engine to assign to it!");}
    }

    public void createEngine(){
        try{
            List<String> engineArgs = cMenu.createEngineFlow();
            Engine engine = getEngine(engineArgs.get(0), engineArgs.get(1), engineArgs.get(2), engineArgs.get(3), engineArgs.get(4), engineArgs.get(5));
        }catch(InvalidEntryException | InvalidEngineEntryException e){
            ConsoleMenu.printExcptMessages(e);
        }
    }

    public void parkCar() {
        while (true) {
            Car car = null;
            try {
                car = cMenu.searchCarFlow();
            } catch (CarNotFoundException | InvalidEntryException e) {
                ConsoleMenu.printExcptMessages(e);
            }
            if (car != null) {
                garage.addCar(car.getLicensePlate(), car);
                cMenu.printMessage(car.getLicensePlate() + " parked!");
                break;
            }
        }
    }

    public void removeCar(){
        if(getLenMapCars() > 0) {
            while (true) {
                boolean flag = false;
                try {
                    cMenu.removeCarFromGarageFlow();
                    flag = true;
                } catch (InvalidEntryException | LicensePlateNotFoundException e) {
                    ConsoleMenu.printExcptMessages(e);
                }if(flag==true){
                    break;
                }
            }
        }else{
            cMenu.printMessage("Your garage don't have cars parked!\nAdd cars in the garage first!");
        }
    }

    public void carAndEngineFeatures(){
        Car car = null;
        try{
            String licensePlate = ConsoleMenu.getTextEntry("Car License Plate: ");
            car = garage.getCar(licensePlate);
        }catch(InvalidEntryException | LicensePlateNotFoundException | CarNotFoundException e){
            ConsoleMenu.printExcptMessages(e);
        }if(car != null){
            cMenu.showCarInfosFlow(car);
            Engine engine = car.getEngine();
            cMenu.showEngineInfosFlows(engine.getEngineFeatures());
        }
    }

    public void garageFeatures(){
        cMenu.showGarageInfosFlow(garage.garageInfos());
    }

    public void run(){
        while(true){
            String option = this.cMenu.mainOptions();
            switch(option){
                case "1": createCar();
                    break;
                case "2": createEngine();
                    break;
                case "3":
                    while(true){
                    String subOption = this.cMenu.garOptions();
                    switch(subOption) {
                        case "1":
                            parkCar();
                            break;
                        case "2":
                            removeCar();
                            break;
                        case "3":
                            carAndEngineFeatures();
                            break;
                        case "4":
                            this.cMenu.showGarageInfosFlow(this.garage.garageInfos());
                            break;
                        default: break;
                        }break;
                    }break;
                case "4": return;
                default: this.cMenu.printMessage("Invalid option!");
            }
        }
    }
}