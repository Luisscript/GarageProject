import java.util.List;
import java.util.ArrayList;

public class ConsoleMenu {

    private GarageService service;
    private static final String isNumeric = "(\\d\\.?(\\d+)?)$";

    public ConsoleMenu(GarageService aService) {
        this.service = aService;
    }

    public void printMessage(String message){
        IO.println(message);
    }

    public static List<String> getGarArgs() throws InvalidEntryException{
        List<String> list = new ArrayList<>();
        String garName = ConsoleMenu.getTextEntry("Garage name: ");
        Integer size = Integer.parseInt(ConsoleMenu.getNumEntry("Garage size\n1. Small - 7 parking space\n2. Medium - 15 parking space\n3. Large - 20 parking space"));
        list.add(garName);
        list.add(String.valueOf(size));
        return list;
    }

    public String garOptions(){
        printMessage("What would you like to do?");
        String option = null;
        try{
            option = ConsoleMenu.getTextEntry("1. Add car\n2. Remove car\n3. Search Car/Car Infos\n4. Garage infos\n5. Quit");
        }catch(Exception e){
            printExcptMessages(e);
        }
        return option;
    }

    public void showGarageInfosFlow(List<String> list){
        IO.println("---------Garage Infos---------");
        IO.println("Name: "+list.get(0)+"\nSize: "+list.get(1));
    }

    public void removeCarFromGarageFlow() throws InvalidEntryException, LicensePlateNotFoundException{
        String licensePlate = getTextEntry("Car license plate to be removed: ");
        boolean flag = service.removeCar(licensePlate);
        if(flag){
            printMessage(licensePlate + " has been removed!");
        }
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

    public static String getTextEntry(String prompt) throws InvalidEntryException{
        String entry = IO.readln(prompt);
        if(entry.isBlank()){
            throw new InvalidEntryException("The entry cannot be empty!");
        }
        return entry.toUpperCase();
    }

    public static String getNumEntry(String prompt) throws InvalidEntryException{
        String entry = IO.readln(prompt);
        if(entry.isBlank()){
            throw new InvalidEntryException("The entry cannot be empty!");
        }if(!entry.matches(isNumeric)){
            throw new InvalidEntryException("This entry is not valid!");
        }
        return entry;
    }

    public static void printExcptMessages(Exception e){
        IO.println(e.getMessage());
    }

    public String mainOptions(){
        printMessage("What would you like to do?");
        String entry = null;
        try {
            entry = ConsoleMenu.getTextEntry("1. Create a car\n2. Create a Engine\n3. Manage Garage\n4. Exit Program");
        }catch(IllegalStateException | InvalidEntryException e){
            ConsoleMenu.printExcptMessages(e);
        }
        printMessage("Selected option: "+ entry);
        return entry;
    }

    public List<String> getCarArgs() throws InvalidEntryException{
        String automaker = ConsoleMenu.getTextEntry("Automaker: ");
        String carModel = ConsoleMenu.getTextEntry("Car model: ");
        String carColor = ConsoleMenu.getTextEntry("Car color: ");
        String engineID = ConsoleMenu.getTextEntry("Engine ID: ");
        String licensePlate = ConsoleMenu.getTextEntry("License Plate: ");
        return List.of(automaker, carModel, carColor, engineID, licensePlate);
    }

    public List<String> createCarFlow(){
        printMessage("Creating a car...");
        return getCarArgs();
    }

    public Car searchCarFlow() throws CarNotFoundException, InvalidEntryException{
        printMessage("Searching car...");
        String licensePlate = ConsoleMenu.getTextEntry("Car Plate: ");
        Car car = Car.getCar(licensePlate);
        if(car == null){
            throw new CarNotFoundException("Car not found!");
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

    private List<String> getEngineArgs() throws InvalidEntryException{
        String engineId = ConsoleMenu.getTextEntry("Engine ID: ");
        String engineType = ConsoleMenu.getTextEntry("Engine Type: ");
        String volume = ConsoleMenu.getNumEntry("Volume engine: ");
        String fuelType = ConsoleMenu.getTextEntry("Fuel type: ");
        String horsePower = ConsoleMenu.getNumEntry("Horse power: ");
        String torque = ConsoleMenu.getNumEntry("Torque: ");
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