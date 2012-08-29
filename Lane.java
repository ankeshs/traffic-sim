import java.util.*;
class Lane
{
Block pos[][];
boolean active=true;
Lane(boolean active)
{
this.active=active;
pos=new Block[3][];
}

}

class Block
{
int x,y;
boolean occ=false;
Car c=null;
}