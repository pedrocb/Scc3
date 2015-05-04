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
                if(Modelo.returnstobase){
                    double distance = Modelo.distances[currentposition][Modelo.STATION];
                    double time = distance/speed;
                    sendTraceNote("MOVing to station");
                    hold(new TimeSpan(time, TimeUnit.SECONDS));
                }
                modelo.agvqueue.insert(this);
                passivate();
            }
            else{
                Job job = modelo.jobqueue.first();
                modelo.jobqueue.remove(job);
                double distance = Modelo.distances[currentposition][job.getCurrentPosition()];
                double time = distance/ speed;
                sendTraceNote("Going from " + currentposition + "to " + job.getCurrentPosition());
                hold(new TimeSpan(time, TimeUnit.SECONDS)); //Mover-se para a posição do job
                currentposition = job.getCurrentPosition();
                if(job.getCurrentPosition()!=Modelo.STATION){
                    modelo.estacao[job.getCurrentPosition()].desoccupy(job);
                    modelo.estacao[job.getCurrentPosition()].blockedtime+= presentTime().getTimeAsDouble() - modelo.estacao[job.getCurrentPosition()].startedblocking;
                    if(!modelo.estacao[job.getCurrentPosition()].iswaitlineempty()) {
                        Job job1 = modelo.estacao[job.getCurrentPosition()].getFirstJobWaiting();
                        sendTraceNote("Chegou a queue a " + presentTime()+ " e saiu a " + job1.arrivalinqueue + "Demorou " + (presentTime().getTimeAsDouble()-job1.arrivalinqueue));
                        job1.timeinqueue+=presentTime().getTimeAsDouble() - job1.arrivalinqueue;
                        if(modelo.estacao[job.getCurrentPosition()].occupy(job1)){
                            job1.activate();
                        }
                    }
                }
                sendTraceNote(" TAKING " + job + " from " + currentposition + " to " + job.getNextPosition());
                job.move();
                distance = Modelo.distances[currentposition][job.getCurrentPosition()];
                time = distance/speed;
                job.totaltimeinagv+=time;
                sendTraceNote(""+time);
                currentposition = job.getCurrentPosition();
                hold(new TimeSpan(time,TimeUnit.SECONDS)); //Levar Job para a proxima posição
                if(job.getCurrentPosition()!=Modelo.STATION) {
                    if (modelo.estacao[currentposition].occupy(job)) {
                        job.activate();
                    } else {
                        modelo.estacao[currentposition].wait(job);
                        job.arrivalinqueue = presentTime().getTimeAsDouble();
                    }
                }
                else{
                    job.activate();
                }
            }
        }
    }
}
