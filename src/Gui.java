import Stack.PrintStack;

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
        ide.setPreferredSize(new Dimension(900,600));
        ide.setBackground(Color.GRAY);

        run = getRunButton();
        codeArea = getCodeArea();
        logArea = getLogArea();
        JScrollPane codeScrollPane = new JScrollPane(codeArea);
        JScrollPane logScrollPane = new JScrollPane(logArea);


        JPanel topElementsPanel = new JPanel();
        GridLayout topElementsGrid = new GridLayout(1, 2);
        topElementsGrid.setHgap(10);
        topElementsGrid.setVgap(10);

        topElementsPanel.setLayout(topElementsGrid);
        topElementsPanel.add(codeScrollPane, BorderLayout.WEST);
        topElementsPanel.add(run, BorderLayout.EAST);

        GridLayout ideGrid = new GridLayout(2, 1);
        ideGrid.setHgap(10);
        ideGrid.setVgap(10);



        ide.setLayout(ideGrid);
        ide.add(topElementsPanel);
        ide.add(logScrollPane);
        ide.pack();
        ide.setVisible(true);
    }

    //todo доделать
    private static JButton getRunButton(){

        JButton run = new JButton(new AbstractAction("Run") {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrintStack.printStream.clear();
                logArea.setText("");

                String code = codeArea.getText();

                String result = "";
                try{
                    Parser.run(code);
                    for(String line: PrintStack.printStream){
                        result += line + "\n";
                    }

                } catch (Exceptions.ParseException ex){
                    result = ex.getMessage() + "  " + ex.index;
                }

                logArea.append(result + "\n");

            }
        });

        //run.setSize(100, 400);
        return run;
    }

    private static JTextArea getCodeArea(){
        JTextArea codeArea = new JTextArea();
        codeArea.setFont(new Font(".SN FS Text", Font.PLAIN, 16));
        //codeArea.setPreferredSize(new Dimension(100, 400));
        Border text_border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        codeArea.setBorder(text_border);

        return codeArea;
    }

    private static JTextArea getLogArea(){
        JTextArea logArea = new JTextArea();
        //logArea.setPreferredSize(new Dimension(100, 400));
        logArea.setFont(new Font("Helvetica", Font.PLAIN, 16));
        Border text_border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        logArea.setBorder(text_border);
        logArea.setEditable(false);

        return logArea;
    }

    private static void save(String text){
        //todo
    }

}
