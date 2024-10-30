import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui_default extends JFrame{

    JPanel jp;
    JLabel jl;

    JTextArea jta;
    JTextField jtf;

    String menuString01 =
            "\n" +
                    "Hello. Graphics test.\n "+ "\n"
                    + "2019-07-14 making start, this program \n " + "\n"
                    + "1. Who use this program. \n 2.drawing " + "\n"
                    + "\n Please input number,"+"\n"+" textfield on bottom. "
                    + "and Enter key.";

    public Gui_default() {

        //	jp = new JPanel();
        //	jl = new JLabel("Hello world~.");

        //jp.add(jl);

        jta = new JTextArea(10,10);
        jta.setText(menuString01);
        jtf = new JTextField(20);

        this.add("West", jta);
        this.add("South", jtf);

        setBounds(300, 300, 700, 370);
        setTitle("Graphics...");
        setVisible(true);
        setResizable(true);
        //add(jp);
    }



    public static void main(String[] args) {

        new Gui_default();
    }

}