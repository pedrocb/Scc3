import desmoj.core.dist.ContDistErlang;
import desmoj.core.simulator.Model;

/**
 * Created by Pedro on 24/04/2015.
 */
public class Job1 extends Job {

    public Job1(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        positions = new int[]{Modelo.STATION,Modelo.C, Modelo.A, Modelo.B, Modelo.E,Modelo.STATION};
        distErlangs = Modelo.distErlangs1;
        totaldelayinqueue = Modelo.totaldelayinqueue[0];

    }

}
