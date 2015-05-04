import com.sun.org.apache.xpath.internal.operations.Mod;
import desmoj.core.dist.ContDistErlang;
import desmoj.core.simulator.Model;

/**
 * Created by Pedro on 24/04/2015.
 */
public class Job3 extends Job {
    public Job3(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        positions = new int[]{Modelo.STATION,Modelo.B, Modelo.E, Modelo.A, Modelo.D,Modelo.C, Modelo.STATION};
        distErlangs = Modelo.distErlangs3;
        totaldelayinqueue = Modelo.totaldelayinqueue[2];
        timeinagv = Modelo.totaltimeinagv[2];
    }
}
