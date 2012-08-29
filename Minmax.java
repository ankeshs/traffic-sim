class Minmax extends AI
{
    Junction jun;
    int d;
    Minmax(Junction jun)
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
       if(jun.es.state==Signal.RED)
       {
           if(ew==0)return;
           if(ns==0)
           {
               jun.changeSignal();
               d=jun.clock*100;
               return;
            }
            d=(int)(d-jun.clock*1.0*ew/ns);
       }
        else if(jun.ns.state==Signal.RED)
       {
           if(ew==0)
           {
               if(ns==0)return;
               jun.changeSignal();
               d=jun.clock*100;
               return;
            }
            d=(int)(d-jun.clock*1.0*ns/ew);
       }
    }
}
