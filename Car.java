class Car
{
int x,y;
double sx=0.0,sy=0.0;
int color;
void step()
{
x=(int)(x+sx);
y=(int)(y+sy);
}
boolean outOfScope()
{
if(x>800||x<0||y>800||y<0)
return true;
return false;
}
}