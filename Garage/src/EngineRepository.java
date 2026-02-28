import java.util.Map;
import java.util.HashMap;

public class EngineRepository {
    // global point access instance
    private static EngineRepository engineRepository = new EngineRepository();

    private Map<String, Engine> engines;

    private EngineRepository(){this.engines = new HashMap<>();}

    public static EngineRepository getInstance(){return engineRepository;}

    public void putEngine(Engine engine){this.engines.put(engine.getEngineId(), engine);}

    public Engine getEngine(String engineId) throws EngineNotFoundException{
        Engine engine = this.engines.get(engineId);
        if(engine == null){
            throw new EngineNotFoundException(engineId);
        }
        return engine;
    }

    public int lenEnginesRepository(){return this.engines.size();}

    public boolean containsEngineId(String engineId){return this.engines.containsKey(engineId);}

    // for serialization
    public Map<String, Engine> getEngines(){return this.engines;}
}
