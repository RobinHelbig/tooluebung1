import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class Flughafen {

    private String name;

    private List<Flughafen> kuerzesteRoute = new LinkedList<>();

    private Integer distanz = Integer.MAX_VALUE;

    Map<Flughafen, Integer> benachbarteFlughaefen = new HashMap<>();

    public Flughafen(String name) {
        this.name = name;
    }

    public void fuegeZielhinzu(Flughafen destination, int distance) {
        benachbarteFlughaefen.put(destination, distance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flughafen> getkuerzesteRoute() {
        return kuerzesteRoute;
    }

    public void setkuerzesteRoute(List<Flughafen> shortestPath) {
        this.kuerzesteRoute = shortestPath;
    }

    public Integer getDistanz() {
        return distanz;
    }

    public void setDistanz(Integer distanz) {
        this.distanz = distanz;
    }

    public Map<Flughafen, Integer> getBenachbarteFlughaefen() {
        return benachbarteFlughaefen;
    }

    public void setBenachbarteFlughaefen(Map<Flughafen, Integer> benachbarteFlughaefen) {
        this.benachbarteFlughaefen = benachbarteFlughaefen;
    }
}
