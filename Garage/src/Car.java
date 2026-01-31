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

    public Car(String licensePlate, String automaker, String model, String color, String engineId) {
        if(automaker == null || automaker.isEmpty()){
            throw new IllegalArgumentException("Automaker is empty, this is invalid!");
        }
        if(model == null || model.isEmpty()){
            throw new IllegalArgumentException("Model is empty, this is invalid!");
        }
        if(color == null || color.isEmpty()){
            throw new IllegalArgumentException("Color is empty, this is invalid!");
        }
        if(engineId == null){
            throw new IllegalArgumentException("Engine is null, this is invalid!");
        }
        if(licensePlate == null){
            throw new IllegalArgumentException("License plate is null, this is invalid!");
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

    public String setLicensePlate(String licensePlt) {
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
           throw new IllegalStateException("Invalid license plate, please try again!");
        } else
            return licensePlate.formattedLicensePlate.toUpperCase();
    }

    public String getLicensePlate() {return this.licensePlate;}

    public Engine setEngine(String engineId){
        Engine engine = null;
        try{
            engine = Engine.getEngine(engineId);
        }catch(Exception e){
            throw new IllegalArgumentException("Invalid engine, please try again!");
        }
        return engine;
    }

    public void turnOnEngine(){
        if(!this.statusEngine){
        this.statusEngine = true;
    }else{
            throw new IllegalStateException("Engine already started!");
        }
    }

    public void turnOffEngine(){
        if(this.statusEngine){
            this.statusEngine = false;
        }else{
            throw new IllegalStateException("Engine is already stopped!");
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

    public static Car getCar(String licensePlate){
        Car car = cars.get(licensePlate);
        if(car == null){
            throw new NullPointerException("Car not found!");
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