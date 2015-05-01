import com.sun.javafx.sg.prism.NGShape;
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
    int currentposition;


    public AGV(Model model, String s, boolean b) {
        super(model, s, b);
        modelo = (Modelo)model;
        currentposition = Modelo.STATION;
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
                double distance = Modelo.distances[currentposition][job.getCurrentPosition()];
                double time = distance/ speed;
                sendTraceNote(time + "MOVING FROM " + currentposition+" TO " + job.getCurrentPosition());
                hold(new TimeSpan(time, TimeUnit.SECONDS)); //Mover-se para a posição do job
                currentposition = job.getCurrentPosition();
                if(job.getNextPosition()!=-1){
                    distance = Modelo.distances[currentposition][job.getCurrentPosition()];
                    time = distance/speed;
                    sendTraceNote(time +" TAKING " + job + " from " + currentposition +" to "+job.getCurrentPosition());
                    hold(new TimeSpan(time,TimeUnit.SECONDS)); //Levar Job para a proxima posição
                }
                job.activate();
            }
        }
    }
}
