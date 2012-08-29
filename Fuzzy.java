class Fuzzy extends AI
{
    Junction jun;
    int d;
    Fuzzy(Junction jun)
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
       if(jun.ns.state==Signal.GREEN)
       {
           if(ew==0)return;
           if(ns==0)
           {
               jun.changeSignal();
               d=jun.clock*100;
               return;
            }
           double ratio=1.0*ew/ns;
           double dr=1.0;
           if(ratio<=0.4)dr=0.4;
           else if(ratio<=0.8)dr=0.8;
           else if(ratio<=1.6)dr=1.6;
           else dr=3.0;
            d=(int)(d-jun.clock*dr);
       }
        else if(jun.es.state==Signal.GREEN)
       {
           if(ns==0)return;
           if(ew==0)
           {
               jun.changeSignal();
               d=jun.clock*100;
               return;
            }
           double ratio=1.0*ns/ew;
           double dr=1.0;
           if(ratio<=0.4)dr=0.4;
           else if(ratio<=0.8)dr=0.8;
           else if(ratio<=1.6)dr=1.6;
           else dr=3.0;
            d=(int)(d-jun.clock*dr);
       }
    }
}
