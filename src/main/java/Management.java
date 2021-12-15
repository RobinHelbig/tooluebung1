import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class Management {

    /**
     * Berechnet ausgehend von einem Startflughafen die k�rzeste Distanz zu allen
     * anderen Flughaefen, die im System (im Graphen) eingetragen sind. Au�erdem wird
     * f�r jede Verbindung zwischen Startflughafen und dem jeweiligen Ziel die Route
     * (Folge von Flughaefen) berechnet, die durchlaufen wird, um zu diesem Ziel zu
     * kommen.
     * @param graph Gerichteter Graph, in dem die Flughaefen die Knoten und die
     * Flugverbindungen die Kanten sind
     * @param start Startflughafen
     * @return
     */
    public static Graph berechnekuerzestenWegvomStartflughafen(Graph graph, Flughafen start) {
        start.setDistanz(0);
        Set<Flughafen> besuchteFlughaefen = new HashSet<>();
        Set<Flughafen> unbesuchteFlughaefen = new HashSet<>();

        unbesuchteFlughaefen.add(start);

        while (unbesuchteFlughaefen.size() != 0) {
            Flughafen aktuellerFlughafen = getnaechstgelegenenFlughafen(unbesuchteFlughaefen);
            unbesuchteFlughaefen.remove(aktuellerFlughafen);

            for (Entry<Flughafen, Integer> Nachbarschaftsbeziehung: aktuellerFlughafen.getBenachbarteFlughaefen().entrySet()) {
                Flughafen benachbarterFlughafen = Nachbarschaftsbeziehung.getKey();
                Integer entfernung = Nachbarschaftsbeziehung.getValue();
                if (!besuchteFlughaefen.contains(benachbarterFlughafen)) {
                    berechnegeringsteDistanz(benachbarterFlughafen, entfernung, aktuellerFlughafen);
                    unbesuchteFlughaefen.add(benachbarterFlughafen);
                }
            }
            besuchteFlughaefen.add(aktuellerFlughafen);
        }
        return graph;
    }

    private static Flughafen getnaechstgelegenenFlughafen(Set <Flughafen> unbesuchteFlughaefen) {
        Flughafen geringsteDistanzFlughafen = null;
        int geringsteDistanz = Integer.MAX_VALUE;
        for (Flughafen flughafen: unbesuchteFlughaefen) {
            int FlughafenDistanz = flughafen.getDistanz();
            if (FlughafenDistanz < geringsteDistanz) {
                geringsteDistanz = FlughafenDistanz;
                geringsteDistanzFlughafen = flughafen;
            }
        }
        return geringsteDistanzFlughafen;
    }

    private static void berechnegeringsteDistanz(Flughafen auszuwertenderFlughafen, Integer entfernung, Flughafen startFlughafen) {
        Integer DistanzzumStartflughafen = startFlughafen.getDistanz();
        if (DistanzzumStartflughafen + entfernung < auszuwertenderFlughafen.getDistanz()) {
            auszuwertenderFlughafen.setDistanz(DistanzzumStartflughafen + entfernung);
            LinkedList<Flughafen> kuerzesteRoute = new LinkedList<>(startFlughafen.getkuerzesteRoute());
            kuerzesteRoute.add(startFlughafen);
            auszuwertenderFlughafen.setkuerzesteRoute(kuerzesteRoute);
        }
    }

    public static void main(String[] args) {
        Flughafen flughafenA = new Flughafen("A");
        Flughafen flughafenB = new Flughafen("B");
        Flughafen flughafenC = new Flughafen("C");
        Flughafen flughafenD = new Flughafen("D");
        Flughafen flughafenE = new Flughafen("E");
        Flughafen flughafenF = new Flughafen("F");

        flughafenA.fuegeZielhinzu(flughafenB, 10);
        flughafenA.fuegeZielhinzu(flughafenC, 15);

        flughafenB.fuegeZielhinzu(flughafenD, 12);
        flughafenB.fuegeZielhinzu(flughafenF, 15);

        flughafenC.fuegeZielhinzu(flughafenE, 10);

        flughafenD.fuegeZielhinzu(flughafenE, 2);
        flughafenD.fuegeZielhinzu(flughafenF, 1);

        flughafenF.fuegeZielhinzu(flughafenE, 5);

        Graph graph = new Graph();

        graph.fuegeFlughafenhinzu(flughafenA);
        graph.fuegeFlughafenhinzu(flughafenB);
        graph.fuegeFlughafenhinzu(flughafenC);
        graph.fuegeFlughafenhinzu(flughafenD);
        graph.fuegeFlughafenhinzu(flughafenE);
        graph.fuegeFlughafenhinzu(flughafenF);

        graph = berechnekuerzestenWegvomStartflughafen(graph, flughafenA);

        for(Flughafen key : graph.getFlughaefen()) {
            System.out.println("Name des Flughafen: " + key.getName()+ " Distanz zum Flughafen A: "+ key.getDistanz());
            for(Flughafen key2 : key.getkuerzesteRoute()) {
                System.out.print(key2.getName()+ "->");
            }
            System.out.print(key.getName());
            System.out.print("\n");
        }


    }

}
