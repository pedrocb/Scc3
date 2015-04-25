import com.sun.javafx.sg.prism.NGShape;
import desmoj.core.simulator.Model;

/**
 * Created by Pedro on 24/04/2015.
 */
public class Job1 extends Job {
    public Job1(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        positions = new int[]{Modelo.C, Modelo.A, Modelo.B, Modelo.E};
    }

}
