import java.util.Random;
class Source
{
static final int MAX=6,NORTH=0,SOUTH=1,EAST=2,WEST=3;
int rate;
int x,y;
int dir;
int xr,yr;
int recoil;
Source(int dir,int rate)
{
this.dir=dir;
this.rate=rate;
recoil=110-rate*10;
if(dir==NORTH)
{
yr=10;
xr=320;
}
if(dir==WEST)
{
xr=10;
yr=320;
}
if(dir==SOUTH)
{
yr=790;
xr=420;
}
if(dir==EAST)
{
xr=790;
yr=420;
}
}
void updatePosition()
{
Random r=new Random();
int id=r.nextInt(3);
if(dir==NORTH)
{
int a[]=new int[]{320,350,380};
xr=a[id];
/*if(xr==320)xr=340;
else if(xr==340)xr=360;
else if(xr==360)xr=380;
else xr=320;*/
}
if(dir==WEST)
{
int a[]=new int[]{320,350,380};
yr=a[id];
/*if(yr==320)yr=340;
else if(yr==340)yr=360;
else if(yr==360)yr=380;
else yr=320;*/
}
if(dir==SOUTH)
{
int a[]=new int[]{420,450,480};
xr=a[id];
/*if(xr==420)xr=440;
else if(xr==440)xr=460;
else if(xr==460)xr=480;
else xr=420;*/
}
if(dir==EAST)
{
int a[]=new int[]{420,450,480};
yr=a[id];
/*if(yr==420)yr=440;
else if(yr==440)yr=460;
else if(yr==460)yr=480;
else yr=420;*/
}
}
void increase()
{
if(rate<Source.MAX)
rate++;
}
void decrease()
{
if(rate>0)
rate--;
}
Car newCar(int speed,int color)
{
Car c=new Car();
c.x=xr;
c.y=yr;
updatePosition();
if(dir==NORTH)c.sy=speed;
if(dir==SOUTH)c.sy=-1*speed;
if(dir==WEST)c.sx=speed;
if(dir==EAST)c.sx=-1*speed;
c.color=color;
return c;
}
}