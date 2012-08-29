class Learning extends AI
{
    static final double LEARNING_FACTOR=0.1;
    Junction jun;
    int d;
    Learning(Junction jun)
    {
       super(jun);
       this.jun=jun;
       d=jun.clock*100;
    }

    void update()
    {
       if(d<=0)
       {
           jun.changeSignal();
           d=jun.clock*100;
           return;
       }
       int ns=jun.nc+jun.sc, ew=jun.ec+jun.wc;
       int pns=jun.pnc+jun.psc, pew=jun.pec+jun.pwc;
       if(jun.ns.state==Signal.GREEN)
       {
           if(ns==0)
           {
               jun.changeSignal();
               d=jun.clock*100;
               return;
            }
            double ratio=(1.0-LEARNING_FACTOR)*ew/ns;
            double lr=0;
            if(pns!=0)lr=LEARNING_FACTOR*pew/pns;
            d=(int)(d-jun.clock*ratio-jun.clock*lr);
       }
        else if(jun.es.state==Signal.GREEN)
       {
           if(ew==0)
           {
               jun.changeSignal();
               d=jun.clock*100;
               return;
            }
            double ratio=(1.0-LEARNING_FACTOR)*ns/ew;
            double lr=0;
            if(pew!=0)lr=LEARNING_FACTOR*pns/pew;
            d=(int)(d-jun.clock*ratio-jun.clock*lr);
       }
    }
}
