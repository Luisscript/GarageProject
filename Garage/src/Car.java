import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Car {
    private static final String licensePlateRegex = "[^0-9A-Za-z]";
    private static final String licensePlateFormat = "[a-zA-Z]{3}\\d[a-zA-Z]\\d{2}";
    private String automaker;
    private String model;
    private String color;
    private String licensePlate;
    private Engine engine;
    private boolean statusEngine = false;
    private static Map<String, Car> cars = new HashMap<>();

    public Car(String licensePlate, String automaker, String model, String color, String engineId) throws InvalidCarEntryException {
        if(automaker.isBlank()){
            throw new InvalidCarEntryException("Automaker is empty, this is invalid!");
        }
        if(model.isBlank()){
            throw new InvalidCarEntryException("Model is empty, this is invalid!");
        }
        if(color.isBlank()){
            throw new InvalidCarEntryException("Color is empty, this is invalid!");
        }
        if(engineId.isBlank()){
            throw new InvalidCarEntryException("Engine is empty, this is invalid!");
        }
        if(licensePlate.isBlank()){
            throw new InvalidCarEntryException("License plate is empty, this is invalid!");
        }
        this.automaker = automaker;
        this.model = model;
        this.color = color;
        this.engine = Engine.getEngine(engineId);
        this.licensePlate = setLicensePlate(licensePlate);
        addCarList();
    }

    private void addCarList(){
        cars.put(licensePlate, this);
    }

    public String setLicensePlate(String licensePlt) throws InvalidLicensePlateException{
        int lenLicensePlate = 7;

        class LicensePlate {
            String formattedLicensePlate = null;

            public LicensePlate(String licensePlt) {
                String lPlate = licensePlt.replaceAll(licensePlateRegex, "");
                if (lPlate.matches(licensePlateFormat)) {
                    this.formattedLicensePlate = lPlate;
                }
            }
        }

        LicensePlate licensePlate = new LicensePlate(licensePlt);
        if (licensePlate.formattedLicensePlate == null) {
           throw new InvalidLicensePlateException("Invalid license plate, please try again!");
        } else
            return licensePlate.formattedLicensePlate.toUpperCase();
    }

    public String getLicensePlate() {return this.licensePlate;}

    public Engine setEngine(String engineId) throws InvalidEngineIdException{
        Engine engine = null;
        try{
            engine = Engine.getEngine(engineId);
        }catch(Exception e){
            throw new InvalidEngineIdException("Invalid engine, please try again!");
        }
        return engine;
    }

    public void turnOnEngine() throws InvalidEngineStateException {
        if(!this.statusEngine){
        this.statusEngine = true;
    }else{
            throw new InvalidEngineStateException("Engine already started!");
        }
    }

    public void turnOffEngine() throws InvalidEngineStateException {
        if(this.statusEngine){
            this.statusEngine = false;
        }else{
            throw new InvalidEngineStateException("Engine is already stopped!");
        }
    }

    public List<Object> carFeatures() {
        List<Object> toReturn = new ArrayList<>();
        String[] fieldsToShow = {"automaker", "model", "color"}; // aqui tinha inserido direto os nomes, sem serem strings, a comparacao sempre dava falsa poor "incompatibilidade" entre os nomes
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean flag = Arrays.stream(fieldsToShow).anyMatch(fieldName -> fieldName.equals(field.getName()));
            if (flag) {
                field.setAccessible(true);
                try {
                    toReturn.add(field.get(this));
                } catch (IllegalAccessException e) {}
            }
        }
        toReturn.add(this.getLicensePlate());
        return toReturn;
    }

    public static Car getCar(String licensePlate) throws CarNotFoundException {
        Car car = cars.get(licensePlate);
        if(car == null){
            throw new CarNotFoundException("Car not found!");
        }
        return car;
    }

    public Engine getEngine(){
        return this.engine;
    }

    public static void carsListFeatures(){
        for(Map.Entry<String, Car> entry : cars.entrySet()){
            entry.getValue().carFeatures();
        }
    }
}