import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Gui extends JFrame{
    private static JTextArea codeArea;
    private static JTextArea logArea;
    private static JButton run;
    private static JButton save;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> generateFrame());
    }

    private static void generateFrame() {
        JFrame ide = new JFrame("IDE");
        ide.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ide.setLocation(200, 200);


        run = getRunButton();
        codeArea = getCodeArea();
        logArea = getLogArea();


        ide.add(codeArea, BorderLayout.WEST);
        ide.add(run, BorderLayout.EAST);
        ide.add(logArea, BorderLayout.SOUTH);
        ide.pack();
        ide.setVisible(true);
    }

    private static JButton getRunButton(){
        JButton run = new JButton(new AbstractAction("Run") {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*String code = codeArea.getText();

                String result;
                try{
                    result = Parser5.run(code);
                } catch (Exceptions.ParseException ex){
                    result = ex.getMessage() + "  " + ex.index;
                }

                 logArea.append(result + "\n");
                 */
            }
        });
        return run;
    }

    private static JTextArea getCodeArea(){
        JTextArea codeArea = new JTextArea();
        codeArea.setFont(new Font("Helvetica", Font.PLAIN, 15));
        codeArea.setPreferredSize(new Dimension(800, 400));
        Border text_border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        codeArea.setBorder(text_border);

        return codeArea;
    }

    private static JTextArea getLogArea(){
        JTextArea logArea = new JTextArea();
        logArea.setPreferredSize(new Dimension(800, 300));
        logArea.setFont(new Font("Helvetica", Font.PLAIN, 15));
        Border text_border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        logArea.setBorder(text_border);

        return logArea;
    }

    private static void save(String text){
        //todo
    }

}
