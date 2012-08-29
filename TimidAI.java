class TimidAI extends AI
{
   Junction jun;
   int d;
   TimidAI(Junction jun)
   {
       super(jun);
       this.jun=jun;
       d=jun.clock*100;
   }
   void update()
   {
       d-=jun.clock;
       if(d<=0)
       {
           jun.changeSignal();
           d=jun.clock*100;
       }
    }
}
