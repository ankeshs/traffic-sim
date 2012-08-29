import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math;
public class Junction extends JPanel implements ActionListener,Runnable
{
    Source north,south,east,west;
    Signal ns,ss,es,ws;
    int nc,sc,ec,wc;
    int pnc,psc,pec,pwc;
    int a[][]=new int[4][3];
    Thread t;
    int clock=100;
    boolean sf=true;
    ArrayList<Car> cars;
    int swt=20;
    boolean chMod=false;
    Random ran;
    AI ai;
    int aix=1;
    public Junction(int aix)
    {
        setSize(750,740);
        setMaximumSize(new Dimension(750,740));
        setLayout(new BorderLayout());
        Button pl,mi;
        String lvr[]=new String[]{BorderLayout.NORTH,BorderLayout.SOUTH,BorderLayout.EAST,BorderLayout.WEST};
        ran=new Random();
        nc=sc=ec=wc=0;
        pnc=psc=pec=pwc=0;
        for(int i=0;i<4;i++)
        {
            Panel p=new Panel();
            p.setBackground(Color.GREEN);
            if(i<2)
            {
                p.setLayout(new FlowLayout(FlowLayout.CENTER));
                p.add(pl=new Button("+"));
                p.add(mi=new Button("-"));
            }
            else
            {
                Panel p5=new Panel();
                p5.setLayout(new BorderLayout());
                Panel p6=new Panel();
                p6.setLayout(new BorderLayout());
                p.setLayout(new GridLayout(2,1));
                p5.add(new Label(" "));
                p6.add(new Label(" "));
                p5.add(pl=new Button("+"),lvr[1]);
                p6.add(mi=new Button("-"),lvr[0]);
                p.add(p5);
                p.add(p6);
            }
            pl.setActionCommand("+"+i);
            mi.setActionCommand("-"+i);
            pl.addActionListener(this);
            mi.addActionListener(this);
            add(p,lvr[i]);
        }
        
        
        cars=new ArrayList<Car>();
        north=new Source(Source.NORTH,5);
        south=new Source(Source.SOUTH,3);
        east=new Source(Source.EAST,4);
        west=new Source(Source.WEST,2);
        ns=new Signal(Signal.GREEN);
        ss=new Signal(Signal.GREEN);
        es=new Signal(Signal.RED);
        ws=new Signal(Signal.RED);
        ai=new Minmax(this);
        this.aix=aix;
        switch(aix)
        {
            case 0:
            ai=new TimidAI(this);
            break;
            case 2:
            ai=new Fuzzy(this);
            break;
            case 3:
            ai=new Learning(this);
            break;
        }
        t=new Thread(this);
        sf=false;
        t.start();
    }
    
    
    public void run()
    {
        for(;;)
        {            
            if(sf)break;
            System.out.println(nc+"-"+sc+"-"+wc+"-"+ec+"- "+"Running.."+cars.size());
            if(chMod)
            {
                if(swt==0)
                {
                    chMod=false;
                    swt=20;
                    if(ns.state==Signal.YELLOW)
                    {
                        ns.state=Signal.GREEN;
                        ss.state=Signal.GREEN;
                        es.state=Signal.RED;
                        ws.state=Signal.RED;
                    }
                    else if(es.state==Signal.YELLOW)
                    {
                        es.state=Signal.GREEN;
                        ws.state=Signal.GREEN;
                        ns.state=Signal.RED;
                        ss.state=Signal.RED;
                    }
                    repaint();
                }
                else swt--;
            }            
            nc=0;
            sc=0;
            wc=0;
            ec=0;
            for(int i=0;i<cars.size();i++)
            {
                Car c=cars.get(i); 
                
                if(c.sy>0)
                {
                    if(c.y>=100&&c.y<=280)nc++;
                    if(ns.state==Signal.GREEN||c.y>=300)
                    {
                        Car col=null;
                        for(int j=0;j<cars.size();j++)
                        {
                            Car cj=cars.get(j);
                            if(j!=i&&Math.abs(c.y-cj.y)<=20&&Math.abs(cj.x-c.x)<=20&&cj.sx!=0)col=cj;
                        }
                        if(col==null)
                        c.step();
                        else col.step();
                    }
                    else
                    {
                        boolean close=false;
                        for(int j=0;j<cars.size();j++)
                        {
                            Car cj=cars.get(j);
                            if(j!=i&&cj.y-c.y<=50&&cj.x==c.x&&cj.sy>0)close=true;
                        }
                        if(300-c.y<=50)close=true;
                        if(!close)c.step();
                    }
                }
                if(c.sy<0)
                {
                    if(c.y>=520&&c.y<=700)sc++;
                    if(ss.state==Signal.GREEN||c.y<=500)
                    {
                        Car col=null;
                        for(int j=0;j<cars.size();j++)
                        {
                            Car cj=cars.get(j);
                            if(j!=i&&Math.abs(c.y-cj.y)<=20&&Math.abs(cj.x-c.x)<=20&&cj.sx!=0)col=cj;
                        }
                        if(col==null)
                        c.step();
                        else col.step();
                    }
                    else
                    {
                        boolean close=false;
                        for(int j=0;j<cars.size();j++)
                        {
                            Car cj=cars.get(j);
                            if(j!=i&&c.y-cj.y<=50&&cj.x==c.x&&cj.sy<0)close=true;
                        }
                        if(c.y-500<=50)close=true;
                        if(!close)c.step();
                    }
                }
                if(c.sx>0)
                {
                    if(c.x>=100&&c.x<=280)wc++;
                    if(ws.state==Signal.GREEN||c.x>=300)
                    {
                        Car col=null;
                        for(int j=0;j<cars.size();j++)
                        {
                            Car cj=cars.get(j);
                            if(j!=i&&Math.abs(c.y-cj.y)<=20&&Math.abs(cj.x-c.x)<=20&&cj.sy!=0)col=cj;
                        }
                        if(col==null)
                        c.step();
                        else col.step();
                    }
                    else
                    {
                        boolean close=false;
                        for(int j=0;j<cars.size();j++)
                        {
                            Car cj=cars.get(j);
                            if(j!=i&&cj.x-c.x<=50&&cj.y==c.y&&cj.sx>0)close=true;
                        }
                        if(300-c.x<=50)close=true;
                        if(!close)c.step();
                    }
                }
                if(c.sx<0)
                {
                    if(c.y>=520&&c.y<=700)ec++;
                    if(es.state==Signal.GREEN||c.x<=500)
                    {
                        Car col=null;
                        for(int j=0;j<cars.size();j++)
                        {
                            Car cj=cars.get(j);
                            if(j!=i&&Math.abs(c.y-cj.y)<=20&&Math.abs(cj.x-c.x)<=20&&cj.sx!=0)col=cj;
                        }
                        if(col==null)
                        c.step();
                        else col.step();
                    }
                    else
                    {
                        boolean close=false;
                        for(int j=0;j<cars.size();j++)
                        {
                            Car cj=cars.get(j);
                            if(j!=i&&c.x-cj.x<=50&&cj.y==c.y&&cj.sx<0)close=true;
                        }
                        if(c.x-500<=50)close=true;
                        if(!close)c.step();
                    }
                }                        
                //System.out.println(i+"("+c.x+","+c.y+")"); 
                if(c.outOfScope()){cars.remove(i);i--;}
            }
            north.recoil--;
            if(north.recoil==0)
            {
                north.recoil=110-10*north.rate;
                if(north.rate>0)
                {
                    
                    Car c=north.newCar(5,ran.nextInt(4));
                    cars.add(c);                
                }
            }
            south.recoil--;
            if(south.recoil==0)
            {
                south.recoil=110-10*south.rate;
                if(south.rate>0)
                {
                    Car c=south.newCar(5,ran.nextInt(4));
                    cars.add(c);        
                }
            }
            west.recoil--;
            if(west.recoil==0)
            {
                west.recoil=110-10*west.rate;
                if(west.rate>0)
                {
                    
                    Car c=west.newCar(5,ran.nextInt(4));
                    cars.add(c);                
                }
            }
            east.recoil--;
            if(east.recoil==0)
            {
                east.recoil=110-10*east.rate;
                if(east.rate>0)
                {
                    
                    Car c=east.newCar(5,ran.nextInt(4));
                    cars.add(c);                
                }
            }
            /*
            repaint(10,0,290,310,510);
            repaint(10,490,290,810,510);
            repaint(10,290,0,510,310);
            repaint(10,290,490,510,810);
            repaint(10,290,290,510,510);
            */
            repaint(0,290,310,510);
            repaint(490,290,810,510);
            repaint(290,0,510,310);
            repaint(290,490,510,810);
            repaint(290,290,510,510);
            if(!chMod)ai.update();
            try
            {
                Thread.sleep(clock);
            }
            catch(Exception exc)
            {
            }
        }
    }
    
    public void paintComponent(Graphics gr)
    {
       super.paintComponent(gr);
       Graphics2D g = (Graphics2D)gr;   
       g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       g.scale(0.9,0.85);
        g.drawImage((new ImageIcon("terrain.jpg")).getImage(),0,0,this);
        /*g.setColor(Color.YELLOW);
        for(int i=1;i<9;i++)
        {
            g.drawLine(0,100*i,800,100*i);
            g.drawLine(100*i,0,100*i,800);
        }*/
        g.setColor(Color.BLACK);
        g.drawLine(0,300,800,300);
        g.drawLine(0,500,800,500);
        g.drawLine(300,0,300,800);
        g.drawLine(500,0,500,800);
        g.setColor(Color.GRAY);
        g.drawLine(0,400,800,400);
        g.drawLine(400,0,400,800);
        g.setColor(Color.red);
        for(int i=0;i<cars.size();i++)
        {
            Car car=cars.get(i);
            if(car.sx>0)
            {
                g.drawImage((new ImageIcon(car.color+"carwe.gif")).getImage(),car.x-18,car.y-13,this);
                //g.fillOval(car.x-18,car.y-13,36,26);
            }
            else if(car.sx<0)
            {
                g.drawImage((new ImageIcon(car.color+"carew.gif")).getImage(),car.x-18,car.y-13,this);
                //g.fillOval(car.x-18,car.y-13,36,26);
            }
            else if(car.sy>0)
            {
                g.drawImage((new ImageIcon(car.color+"carns.gif")).getImage(),car.x-12,car.y-18,this);
                //g.fillOval(car.x-13,car.y-18,26,36);
            }
            else
            {
                g.drawImage((new ImageIcon(car.color+"carsn.gif")).getImage(),car.x-12,car.y-18,this);
                //g.fillOval(car.x-13,car.y-18,26,36);
            }
        }
        g.setColor(Color.BLACK);
        g.fillOval(260,300-140,30,30);//nr
        g.fillOval(260,300-110,30,30);//ny
        g.fillOval(260,300-80,30,30);//ng
        g.fillOval(510,500+50,30,30);//sg
        g.fillOval(510,500+80,30,30);//sy
        g.fillOval(510,500+110,30,30);//sr
        g.fillOval(300-140,260,30,30);//wr
        g.fillOval(300-110,260,30,30);//wy
        g.fillOval(300-80,260,30,30);//wg
        g.fillOval(500+50,510,30,30);//eg
        g.fillOval(500+80,510,30,30);//ey
        g.fillOval(500+110,510,30,30);//er
        paintSignals(g);
        paintSensors(g);
    }
    
    void paintSignals(Graphics2D g)
    {
        if(ns.state==Signal.RED)
        {
            g.setColor(Color.RED);
            g.fillOval(260,300-140,30,30);//nr
            g.fillOval(510,500+110,30,30);//sr
        }
        if(es.state==Signal.RED)
        {
            g.setColor(Color.RED);
            g.fillOval(500+110,510,30,30);//er
            g.fillOval(300-140,260,30,30);//wr
        }
        if(ns.state==Signal.GREEN)
        {
            g.setColor(Color.GREEN);
            g.fillOval(260,300-80,30,30);//ng
            g.fillOval(510,500+50,30,30);//sg
        }
        if(es.state==Signal.GREEN)
        {
            g.setColor(Color.GREEN);
            g.fillOval(300-80,260,30,30);//wg
            g.fillOval(500+50,510,30,30);//eg
        }
        if(ns.state==Signal.YELLOW)
        {
            g.setColor(Color.YELLOW);
            g.fillOval(260,300-110,30,30);//ny
            g.fillOval(510,500+80,30,30);//sy
        }
        if(es.state==Signal.YELLOW)
        {
            g.setColor(Color.YELLOW);
            g.fillOval(300-110,260,30,30);//wy
            g.fillOval(500+80,510,30,30);//ey
        }
    }
    
    void paintSensors(Graphics2D g)
    {
        g.setColor(Color.BLUE);
        g.drawLine(290,100,510,100);
        g.drawLine(290,280,510,280);
        g.drawLine(290,520,510,520);
        g.drawLine(290,700,510,700);
        g.drawLine(100,290,100,510);
        g.drawLine(280,290,280,510);
        g.drawLine(520,290,520,510);
        g.drawLine(700,290,700,510);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("+0"))
        {
            north.increase();            
        }
        if(e.getActionCommand().equals("+1"))
        {
            south.increase();
        }
        if(e.getActionCommand().equals("+2"))
        {
            east.increase();            
        }
        if(e.getActionCommand().equals("+3"))
        {
            west.increase();
        }
        if(e.getActionCommand().equals("-0"))
        {
            north.decrease();            
        }
        if(e.getActionCommand().equals("-1"))
        {
            south.decrease();
        }
        if(e.getActionCommand().equals("-2"))
        {
            east.decrease();            
        }
        if(e.getActionCommand().equals("-3"))
        {
            west.decrease();
        }
    }
    void changeSignal()
    {
        chMod=true;
        pnc+=nc;
        psc+=sc;
        pwc+=wc;
        pec+=ec;
        if(ns.state==Signal.RED)
        {
            ns.state=Signal.YELLOW;
            ss.state=Signal.YELLOW;
        }
        else if(es.state==Signal.RED)
        {
            es.state=Signal.YELLOW;
            ws.state=Signal.YELLOW;
        }
        repaint();
    }
}