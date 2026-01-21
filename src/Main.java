import dao.FilmeDAOImpl;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;

    private static class Console {
        private final JFrame frame;

        public Console() {
            frame = new JFrame();
            final JTextArea textArea = new JTextArea(28, 120);
            textArea.setBackground(Color.BLACK);
            textArea.setForeground(Color.LIGHT_GRAY);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
            System.setOut(new PrintStream(new OutputStream() {

                @Override
                public void write(int b) throws IOException {
                    textArea.append(String.valueOf((char) b));
                }
            }));
            frame.add(textArea);
        }

        public void init() {
            frame.pack();
            frame.setVisible(true);
        }

        public JFrame getFrame() {
            return frame;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Console console = new Console();
                console.init();
                Main launcher = new Main();
                launcher.setVisible(true);
                console.getFrame().setLocation(
                        launcher.getX() + launcher.getWidth()
                                + launcher.getInsets().right, launcher.getY());
                System.out.println("Started Launcher...");
                System.out.println("Hello");

 // ---------------- My Programm --------------------------------------------
                FilmeDAOImpl dao = new FilmeDAOImpl();
                //System.out.println(dao.findMovieById(1));
               System.out.println(dao.findMovieByName("Alien"));

                //System.out.println(dao.addMovieSimple("Avatar: Fire and Ash"));
              //  System.out.println(new String(new char[50]).replace("", "\r\n"));
                //System.out.println(dao.checkFsk(18));
               // System.out.println(dao.updateBeschreibung(3,"Während die Familie mit ihrer Trauer umgehen und Spider in Sicherheit bringen muss, werden sie mit einem neuen Klan konfrontiert, der von der hitzköpfigen Varang angeführt wird. Zudem versammelt die RDA nach ihrer Niederlage neue Kräfte."));
               // System.out.println(dao.updateRelease(3,2027));
               // System.out.println(dao.updateFsk(3,12));
                // System.out.println(dao.updateDauer(3,197));
                //System.out.println(dao.updateGenres(1,"Horror","Science-Fiction"));
                System.out.println(dao.updateLanguages(1,"Englisch", "Deutsch", "Chinesisch"));















            }



        });
    }

    private Main() {
        super("DevCity13 - Launcher (Alpha)");
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}