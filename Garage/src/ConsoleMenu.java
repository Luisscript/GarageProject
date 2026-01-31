import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class ConsoleMenu {

    private GarageService service;

    public ConsoleMenu(GarageService aService) {
        this.service = aService;
    }

    public static List<String> run(){
        IO.println("To start, let's create a your garage!");
        return ConsoleMenu.getGarArgs();
    }

    public void printMessage(String message){
        IO.println(message);
    }

    public static List<String> getGarArgs(){
        List<String> list = new ArrayList<>();
        String garName = IO.readln("Garage name: ");
        Integer size = Integer.parseInt(IO.readln("Garage size\n1. Small - 7 parking space\n2. Medium - 15 parking space\n3. Large - 20 parking space"));
        list.add(garName);
        list.add(String.valueOf(size));
        return list;
    }

    public String garOptions(){
        printMessage("What would you like to do?");
        String option = null;
        try{
            option = this.getOption("1. Add car\n2. Remove car\n3. Search Car/Car Infos\n4. Garage infos\n5. Quit");
        }catch(Exception e){
            printExcptMessages(e);
        }
        return option;
    }

    public void showGarageInfosFlow(List<String> list){
        IO.println("---------Garage Infos---------");
        IO.println("Name: "+list.get(0)+"\nSize: "+list.get(1));
    }

    public void startCarFlow(Car car) {
        try {
            service.startCarEngine(car);
        } catch (IllegalStateException e) {
            IO.println("Engine is already on");
        }
    }

    public void stopCarFlow(Car car) {
        try {
            service.stopCarEngine(car);
        } catch (IllegalStateException e) {
            IO.println("Engine is already off");
        }
    }

    public String getOption(String prompt){
        String option = IO.readln(prompt);
        if(option.isEmpty()){
            throw new IllegalArgumentException("The option cannot be empty!");
        }
        return option.toUpperCase();
    }

    public static void printExcptMessages(Exception e){
        IO.println(e.getMessage());
    }

    public String mainOptions(){
        printMessage("What would you like to do?");
        String option = null;
        try {
            option = this.getOption("1. Create a car\n2. Create a Engine\n3. Manage Garage\n4. Exit Program");
        }catch(IllegalStateException e){
            ConsoleMenu.printExcptMessages(e);
        }
        printMessage("Selected option: "+option);
        return option;
    }

    public List<String> getCarArgs(){
        String automaker = this.getOption("Automaker: ");
        String carModel = this.getOption("Car model: ");
        String carColor = this.getOption("Car color: ");
        String engineID = this.getOption("Engine ID: ");
        String licensePlate = this.getOption("License Plate: ");
        if(automaker.isEmpty() || carModel.isEmpty() || carColor.isEmpty()){
            throw new IllegalArgumentException("Car name and model or color cannot be empty!");
        }
        return List.of(automaker, carModel, carColor, engineID, licensePlate);
    }

    public List<String> createCarFlow(){
        printMessage("Creating a car...");
        return getCarArgs();
    }

    public Car searchCarFlow(){
        printMessage("Searching car...");
        String licensePlate = getOption("Car Plate: ");
        Car car = Car.getCar(licensePlate);
        if(car == null){
            throw new NullPointerException("Car not found!");
        }
        return car;
    }

    public void showCarInfosFlow(Car car){
        printMessage("---------Car Infos---------");
        List<Object> list = car.carFeatures();
        for(Object feature : list){
            printMessage((String)feature);
        }
    }

    private List<String> getEngineArgs(){
        String engineId = this.getOption("Engine ID: ");
        String engineType = this.getOption("Engine Type: ");
        String volume = this.getOption("Volume engine: ");
        String fuelType = this.getOption("Fuel type: ");
        String horsePower = this.getOption("Horse power: ");
        String torque = this.getOption("Torque: ");
        if(engineId.isEmpty() || engineType.isEmpty() || volume.isEmpty() ||fuelType.isEmpty() || horsePower.isEmpty() || torque.isEmpty()){
            throw new IllegalArgumentException("Arguments cannot be empty!");
        }
        return List.of(engineId, engineType, volume, fuelType, horsePower, torque);
    }

    public List<String> createEngineFlow(){
        printMessage("Creating a engine ...");
        return getEngineArgs();
    }

    public void showEngineInfosFlows(List<Object> engFeatures){
        printMessage("---------Engine Infos---------");
        for(Object feature : engFeatures){
            printMessage(feature.toString());
        }
    }
}