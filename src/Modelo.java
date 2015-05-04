import desmoj.core.dist.ContDistErlang;
import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.*;
import desmoj.core.statistic.Tally;

import java.util.concurrent.TimeUnit;

public class Modelo extends Model {
    private ContDistExponential jobArrivalTime;
    public static int A = 0;
    public static int B = 1;
    public static int C = 2;
    public static int D = 3;
    public static int E = 4;
    public static int STATION = 5;

    public static boolean returnstobase = true;

    public static Tally[] totaldelayinqueue;
    public static Tally[] totaltimeinagv;

    public static ContDistErlang[] distErlangs1;
    public static ContDistErlang[] distErlangs2;
    public static ContDistErlang[] distErlangs3;


    protected ProcessQueue<Job> jobqueue;

    protected Station estacao[];


    protected ProcessQueue<AGV> agvqueue;

    public static double[][] distances =
            {{0.0,45.0,50.0,90.0,100.0,135.0},
            {45.0,0.0,50.0,100.0,90.0,100.0,135.0},
            {50.0,50.0,0.0,50.0,50.0,90.0},
            {90.0,100.0,50.0,0.0,45.0,50.0},
            {100.0,90.0,50.0,45.0,0.0,50.0},
            {135.0,135.0,90.0,50.0,50.0,0.0}};

    public static void main(String[] args){
        Modelo modelo = new Modelo();
        Experiment experiment = new Experiment("Experiment",TimeUnit.SECONDS,TimeUnit.MINUTES,null);
        modelo.connectToExperiment(experiment);
        experiment.setShowProgressBar(true);
        experiment.stop(new TimeInstant(2900, TimeUnit.HOURS));
        experiment.tracePeriod(new TimeInstant(0), new TimeInstant(20, TimeUnit.HOURS));

        experiment.start();
        experiment.report();
        experiment.finish();
    }

    public Modelo(){
        super(null, "Modelo", true, true);
    }

    @Override
    public void init() {

        jobArrivalTime = new ContDistExponential(this,"Job Arrival Time",15.0,true,false);
        jobArrivalTime.setNonNegative(true);

        agvqueue = new ProcessQueue<AGV>(this,"Agv Queue",true,true);

        estacao = new Station[5];
        estacao[A] = new Station(this,"Estação A Queue",3);
        estacao[B] = new Station(this,"Estação B Queue",3);
        estacao[C] = new Station(this,"Estação C Queue",4);
        estacao[D] = new Station(this,"Estação D Queue",4);
        estacao[E] = new Station(this,"Estação E Queue",1);

        distErlangs1 = new ContDistErlang[4];
        distErlangs1[0] = new ContDistErlang(getModel(),"1º Erlang Job 1", 2,30.0,true,true);
        distErlangs1[1] = new ContDistErlang(getModel(),"2º Erlang Job 1",2,36.0,true,true);
        distErlangs1[2] = new ContDistErlang(getModel(),"3º Erlang Job 1",2,51.0,true,true);
        distErlangs1[3] = new ContDistErlang(getModel(),"4º Erlang Job 1",2,30.0,true,true);

        distErlangs2 = new ContDistErlang[3];
        distErlangs2[0] = new ContDistErlang(getModel(),"1º Erlang Job 2", 2,66.0,true,true);
        distErlangs2[1] = new ContDistErlang(getModel(),"2º Erlang Job 2",2,48.0,true,true);
        distErlangs2[2] = new ContDistErlang(getModel(),"3º Erlang Job 2",2,45.0,true,true);

        distErlangs3 = new ContDistErlang[5];
        distErlangs3[0] = new ContDistErlang(getModel(),"1º Erlang Job 3", 2,72.0,true,true);
        distErlangs3[1] = new ContDistErlang(getModel(),"2º Erlang Job 3",2,15.0,true,true);
        distErlangs3[2] = new ContDistErlang(getModel(),"3º Erlang Job 3",2,42.0,true,true);
        distErlangs3[3] = new ContDistErlang(getModel(),"4º Erlang Job 3",2,54.0,true,true);
        distErlangs3[4] = new ContDistErlang(getModel(),"5º Erlang Job 3",2,60.0,true,true);

        jobqueue = new ProcessQueue<Job>(this,"Job Queue",true,true);

        totaldelayinqueue = new Tally[3];
        totaldelayinqueue[0] = new Tally(this,"Job 1 queue tally",true,true);
        totaldelayinqueue[1] = new Tally(this,"Job 2 queue tally",true,true);
        totaldelayinqueue[2] = new Tally(this,"Job 3 queue tally",true,true);

        totaltimeinagv = new Tally[3];
        totaltimeinagv[0] = new Tally(this,"Job 1 avg tally",true,true);
        totaltimeinagv[1] = new Tally(this,"Job 2 avg tally",true,true);
        totaltimeinagv[2] = new Tally(this,"Job 3 avg tally",true,true);



    }


    public double getJobArrivalTime(){
        return jobArrivalTime.sample();}

    @Override
    public String description() {
        return null;
    }

    @Override
    public void doInitialSchedules() {
        JobGenerator generator = new JobGenerator(this,"Job Generator",true);
        generator.activate();
        AGV agv = new AGV(this,"AGV",true);
        agv.activate();
    }
}
