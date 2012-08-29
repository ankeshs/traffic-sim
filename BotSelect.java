import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class BotSelect extends JDialog implements ActionListener
{
    JComboBox jcb;
    int aix=1;
    BotSelect(JunctionF jf)
    {
        super(jf,"Select Bot",true);
        JButton b=new JButton("Ok");
        jcb=new JComboBox();
        jcb.addItem("Timed");
        jcb.addItem("Minmax");
        jcb.addItem("Fuzzy");
        jcb.addItem("Learning");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(jcb);
        add(b);
        jcb.setSelectedIndex(aix);
        b.addActionListener(this);
        setSize(250,100);
        setLocationRelativeTo(jf);
        addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    System.exit(0);
                }
            });
        }
public void actionPerformed(ActionEvent e)
{
    aix=jcb.getSelectedIndex();
    dispose();
}
}


