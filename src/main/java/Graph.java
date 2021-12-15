
import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<Flughafen> flughaefen = new HashSet<>();

    public void fuegeFlughafenhinzu(Flughafen flughafenA) {
        flughaefen.add(flughafenA);
    }

    public Set<Flughafen> getFlughaefen() {
        return flughaefen;
    }

    public void setFlughaefen(Set<Flughafen> flughaefen) {
        this.flughaefen = flughaefen;
    }
}
