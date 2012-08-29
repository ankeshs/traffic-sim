import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class JunctionF extends JFrame
{

int aix;
BotSelect jd;
    public JunctionF()
    {
        addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    exit();
                }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();        
        setSize(750,740);
        setLocation(0,0);
        setLayout(new BorderLayout());
        aix=1;
        jd=new BotSelect(this);
        jd.setVisible(true);
        add(new Junction(jd.aix),BorderLayout.CENTER);
}
public void exit()
    {
        System.exit(0);
    }
}
