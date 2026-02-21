import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

public class Engine {

    private String engineId;
    private String engineType;
    private double volume;
    private String fuelType;
    private double horsePower;
    private double torque;
    private static Map<String, Engine> engines = new HashMap<>();
    private static final String isNumeric = "(\\d+\\.?\\d+)$";

    public Engine(){}

    public Engine(String engineId, String engineType, String volume, String fuelType, String horsePower, String torque) throws InvalidEngineEntryException{
        if(engineId.isBlank()){
            throw new InvalidEngineIdException("Engine ID is empty, this is invalid!");
        }else{
            if(engines.containsKey(engineId)){
                throw new InvalidEngineEntryException("This engine id already exists!");
            }
        }
        if(engineType.isBlank()){
            throw new InvalidEngineEntryException("Engine type is empty, this is invalid!");
        }
        if(volume.isBlank() || !volume.matches(isNumeric)){
            throw new InvalidEngineEntryException("Volume is null or not numeric, this is invalid!");
        }
        if(fuelType.isBlank()){
            throw new InvalidEngineEntryException("Fuel type is empty, this is invalid!");
        }
        if(horsePower.isBlank() || !horsePower.matches(isNumeric)){
            throw new InvalidEngineEntryException("Horse power is empty or not numeric, this is invalid!");
        }
        if(torque.isBlank() || !torque.matches(isNumeric)){
            throw new InvalidEngineEntryException("Torque power is null or not numeric, this is invalid!");
        }
        this.engineId = engineId.toUpperCase();
        this.engineType = engineType;
        this.volume = Double.parseDouble(volume);
        this.fuelType = fuelType;
        this.horsePower = Double.parseDouble(horsePower);
        this.torque = Double.parseDouble(torque);
        addEngineList();
    }

    public void addEngineList(){
        engines.put(engineId, this);
    }

    public static Engine getEngine(String engineId) throws EngineNotFoundException{// assinatura da chamada de Excacao em caso de erro
        Engine eng = engines.get(engineId);
        if (eng != null) {
            return eng;
        }
        throw new EngineNotFoundException("Engine ID not founded!");
    }

    public static int enginesLenght(){
        return engines.size();
    }

    public List<Object> getEngineFeatures(){
        List<String> notshow = List.of("engines", "isNumeric");
        List<Object> toreturn = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field : fields){
            if(notshow.contains(field.getName())){
                continue;
            }
            field.setAccessible(true);
            try{
                toreturn.add(field.get(this));
            }catch(IllegalAccessException except){
            }
        }
        return toreturn;
    }

    public String getEngineId(){return engineId;}
    public String getEngineType(){return engineType;}
    public double getVolume(){return volume;}
    public String getFuelType(){return fuelType;}
    public double getHorsePower(){return horsePower;}
    public double getTorque(){return torque;}

    public Map<String, Engine> getEngines(){return engines;}
}