import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManagementTest {

    private static Management management;
    private static Flughafen flughafenA, flughafenB, flughafenC, flughafenD, flughafenE, flughafenF ;
    private static Graph graph, result_graph;


    @BeforeAll
    static void init_setup() {
        System.out.println("Before All");
    }

    @BeforeEach
    public void  beforeEach() {
        System.out.println("Before Each");
        management = new Management();
        flughafenA = new Flughafen("A");
        flughafenB = new Flughafen("B");
        flughafenC = new Flughafen("C");
        flughafenD = new Flughafen("D");
        flughafenE = new Flughafen("E");
        flughafenF = new Flughafen("F");
        graph = new Graph();
        flughafenA.fuegeZielhinzu(flughafenB, 10);
        flughafenA.fuegeZielhinzu(flughafenC, 15);
        flughafenB.fuegeZielhinzu(flughafenD, 12);
        flughafenB.fuegeZielhinzu(flughafenF, 15);
        flughafenC.fuegeZielhinzu(flughafenE, 10);
        flughafenD.fuegeZielhinzu(flughafenE, 2);
        flughafenD.fuegeZielhinzu(flughafenF, 1);
        flughafenF.fuegeZielhinzu(flughafenE, 5);
        graph.fuegeFlughafenhinzu(flughafenA);
        graph.fuegeFlughafenhinzu(flughafenB);
        graph.fuegeFlughafenhinzu(flughafenC);
        graph.fuegeFlughafenhinzu(flughafenD);
        graph.fuegeFlughafenhinzu(flughafenE);
        graph.fuegeFlughafenhinzu(flughafenF);
    }

    @AfterEach
    public void  afterEach() {
        System.out.println("After Each");
    }

    @AfterAll
    static void  afterAll() {
        System.out.println("After All");
    }

    //Startflughafen ist nicht im Graphen enthalten
    @Test
    public void testStartairportNotInGraph() throws Throwable {
        System.out.println("testStartairportNotInGraph()");
        Flughafen flughafenG = new Flughafen("G");
        graph =  management.berechnekuerzestenWegvomStartflughafen(graph, flughafenG);
        // Distanz zu allen Flughafen ist 2147483647
    }

    //Startflughafen ist null
    @Test
    public void testStartairportIsNull() throws Throwable {
        System.out.println("testStartairportIsNull()");
        Flughafen flughafenA = null;
        graph.fuegeFlughafenhinzu(flughafenA);
        graph =  management.berechnekuerzestenWegvomStartflughafen(graph, flughafenA);
        // Error: Null Exception Pointer
    }

    //Es gibt negative Entfernungen
    @Test
    public void testNegativeDistance() throws Throwable {
        System.out.println("testNegativeDistance()");
        flughafenB.fuegeZielhinzu(flughafenD, -5);
        graph =  management.berechnekuerzestenWegvomStartflughafen(graph, flughafenA);
        // Es wird mit den ngativen Werten /gerechner/addiert
    }

    //Es gibt Flughäfen im Graphen, die null sind
    @Test
    public void testAirportsAreNull() throws Throwable {
        System.out.println("testAirportsAreNull()");
        flughafenD = null;
        graph.fuegeFlughafenhinzu(flughafenD);
        graph =  management.berechnekuerzestenWegvomStartflughafen(graph, flughafenA);
        // keine AUswirkung für die Methode aber beim Graph print => Print Error: Null Exception Pointer
    }

    //Es werden mit Flughafen.fuegeZielhinzu() Kanten zwischen Flughäfen erstellt, aber diese Flughäfen werden nicht alle mit Graph.fuegeFlughafenhinzu() dem Graphen hinzugefügt
    @Test
    public void testAirportsNotAddedToGraph() throws Throwable {
        System.out.println("testAirportsNotAddedToGraph()");
        Flughafen flughafenG = new Flughafen("G");
        flughafenC.fuegeZielhinzu(flughafenG, 10);
        graph =  management.berechnekuerzestenWegvomStartflughafen(graph, flughafenA);
        //Distanz zu dem nicht hinzugefügeten Flughafen wird nicht berechnet
    }

    //Es gibt Schlingen (also Kanten bei denen Start- und Endknoten übereinstimmen)
    @Test
    public void testEdgesSameStartnodeAndEndNode() throws Throwable {
        System.out.println("testEdgesSameStartnodeAndEndNode()");
        flughafenB.fuegeZielhinzu(flughafenA, 10);
        //Wird ignoriert da immer der Kürzeste Pfad verwendet wird? A => A und nicht A => B => A
    }

    //Es gibt Parallelkanten (also Kanten die aus demselben Startknoten starten und in demselben Endknoten enden)
    @Test
    public void testparrallelEdges() throws Throwable {
        System.out.println("testparrallelEdges()");
        Flughafen flughafenG = new Flughafen("G");
        flughafenC.fuegeZielhinzu(flughafenG, 10);
        flughafenB.fuegeZielhinzu(flughafenG, 2);
        graph.fuegeFlughafenhinzu(flughafenG);
        // Nimmt den kürzeren Weg
    }

    //Nicht alle Flughäfen sind aus dem Startflughafen erreichbar
    @Test
    public void testNotReachableAirports() throws Throwable {
        System.out.println("testNotReachableAirports()");
        Flughafen flughafenG = new Flughafen("G");
        graph.fuegeFlughafenhinzu(flughafenG);
        // Distanz zu nicht erreichbarem Flughafen ist 2147483647

    }

}
