import desmoj.core.dist.ContDistErlang;
import desmoj.core.simulator.Model;

/**
 * Created by Pedro on 24/04/2015.
 */
public class Job2 extends Job {
    public Job2(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        positions = new int[]{Modelo.STATION,Modelo.D, Modelo.A,Modelo.C,Modelo.STATION};
        distErlangs = Modelo.distErlangs2;
        totaldelayinqueue = Modelo.totaldelayinqueue[1];
        timeinagv = Modelo.totaltimeinagv[1];
    }
}
