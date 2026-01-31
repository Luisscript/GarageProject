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

    public Engine(String engineId, String engineType, String volume, String fuelType, String horsePower, String torque){
        if(engineId == null){
            throw new IllegalArgumentException("Engine ID is null, this is invalid!");
        }else{
            if(engines.containsKey(engineId)){
                throw new IllegalArgumentException("This engine id already exists!");
            }
        }
        if(engineType == null){
            throw new IllegalArgumentException("Engine type is null, this is invalid!");
        }
        if(volume == null || !volume.matches(isNumeric)){
            throw new IllegalArgumentException("Volume is null or not numeric, this is invalid!");
        }
        if(fuelType == null){
            throw new IllegalArgumentException("Fuel type is null, this is invalid!");
        }
        if(horsePower == null || !horsePower.matches(isNumeric)){
            throw new IllegalArgumentException("Horse power is null or not numeric, this is invalid!");
        }
        if(torque == null || !torque.matches(isNumeric)){
            throw new IllegalArgumentException("Torque power is null or not numeric, this is invalid!");
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

    public static Engine getEngine(String engineId) {// assinatura da chamada de Excacao em caso de erro
        Engine eng = engines.get(engineId);
        if (eng != null) {
            return eng;
        }
        throw new IllegalArgumentException("Engine ID not founded!");
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
}