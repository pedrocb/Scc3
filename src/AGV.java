import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pedro on 20/04/2015.
 */
public class AGV extends SimProcess {
    Modelo modelo;
    double speed;

    public AGV(Model model, String s, boolean b) {
        super(model, s, b);
        modelo = (Modelo)model;
        speed = 2.5;
    }

    @Override
    public void lifeCycle() {
        while(true){
            if(modelo.jobqueue.isEmpty()){
                modelo.agvqueue.insert(this);
                passivate();
            }
            else{
                Job job = modelo.jobqueue.first();
                modelo.jobqueue.remove(job);
                double distance = Modelo.distances[job.getNextPosition()][Modelo.STATION];
                double time = distance/ speed;
                System.out.println(distance);
                System.out.println(time);
                hold(new TimeSpan(time, TimeUnit.SECONDS));
                modelo.estacaoA.insert(job);
            }
        }
    }
}
