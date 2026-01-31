import java.util.List;

public class Main {

    public static int getLenMapEngines() {
        return Engine.enginesLenght();
    }

    public static void main(String[] args) {
        List<String> garArgs = ConsoleMenu.getGarArgs();
        Garage garage = null;
        try {
            garage = new Garage(garArgs.get(0), Integer.parseInt(garArgs.get(1)));
        } catch (IllegalArgumentException e) {
            ConsoleMenu.printExcptMessages(e);
        }
        GarageService service = new GarageService(garage);
        ConsoleMenu consoleMenu = new ConsoleMenu(service);
        while (true) {
            String option = consoleMenu.mainOptions();
            if (option.equals("1")) {
                if (getLenMapEngines() > 0) {
                    List<String> carArgs = consoleMenu.createCarFlow();
                    try{
                        Car car = new Car(carArgs.get(4), carArgs.get(0), carArgs.get(1), carArgs.get(2), carArgs.get(3));
                    }catch(IllegalArgumentException e){
                        ConsoleMenu.printExcptMessages(e);
                    }
                }else{
                    consoleMenu.printMessage("You cannot create a car without an engine to assign to it!");
                }
            }
            else if(option.equals("2")){
                List <String> engineArgs = consoleMenu.createEngineFlow();
                try{
                    Engine engine = new Engine(engineArgs.get(0), engineArgs.get(1), engineArgs.get(2), engineArgs.get(3), engineArgs.get(4), engineArgs.get(5));
                }catch(IllegalArgumentException e){
                    ConsoleMenu.printExcptMessages(e);
                }
            }
            else if(option.equals("3")){
                while(true){
                    option = consoleMenu.garOptions();
                    if(option.equals("1")){
                        Car car = null;
                        try {
                            car = consoleMenu.searchCarFlow();
                        }catch(NullPointerException e){
                            ConsoleMenu.printExcptMessages(e);
                        }
                        if(car!=null){
                            garage.addCar(car.getLicensePlate(), car);
                            consoleMenu.printMessage("Car added successfully!");
                        }
                    }
                    else if(option.equals("2")){

                    }
                    else if(option.equals("3")){
                        if(getLenMapEngines() > 0){
                            Car car = null;
                            String licensePlate = consoleMenu.getOption("Car license plate: ").toUpperCase();
                            try {
                                car = garage.getCar(licensePlate);
                            }catch(NullPointerException e){
                                ConsoleMenu.printExcptMessages(e);
                            }
                            if(car!=null) {
                                consoleMenu.showCarInfosFlow(car);
                                Engine engine = car.getEngine();
                                consoleMenu.showEngineInfosFlows(engine.getEngineFeatures());
                            }
                        }else{
                            consoleMenu.printMessage("Your garage don't have cars parked!\nAdd cars in the garage first!");
                        }

                    }
                    else if(option.equals("4")){
                        consoleMenu.showGarageInfosFlow(garage.garageInfos());
                    }
                    else if(option.equals("5")){
                        break;
                    }
                }
            }
            else if (option.equals("4")){
                    break;
                }
            }
        }
    }