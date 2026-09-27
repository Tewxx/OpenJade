package jade.loader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

final class LoaderWindow {
    static void open() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Jade Loader");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextArea output = new JTextArea(12, 60);
            output.setEditable(false);
            output.setLineWrap(true);
            output.setWrapStyleWord(true);
            output.setText("Place jade.jar beside jade-loader.jar, then start Minecraft and click Load Jade.\n"
                + "Minecraft is detected by its game window. Wait until the title screen before loading.\n");
            JButton load = new JButton("Load Jade");
            JButton list = new JButton("List Java processes");
            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actions.add(list);
            actions.add(load);
            JPanel content = new JPanel(new BorderLayout(8, 8));
            content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            content.add(new JScrollPane(output), BorderLayout.CENTER);
            content.add(actions, BorderLayout.SOUTH);
            frame.setContentPane(content);
            load.addActionListener(event -> run(new String[0], output, load, list));
            list.addActionListener(event -> run(new String[]{"--list"}, output, load, list));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void run(String[] args, JTextArea output, JButton load, JButton list) {
        load.setEnabled(false);
        list.setEnabled(false);
        output.append("\nWorking...\n");
        new SwingWorker<Void, String>() {
            protected Void doInBackground() throws Exception {
                JadeLoader.run(args, message -> publish(message));
                return null;
            }
            protected void process(List<String> messages) {
                for (String message : messages) output.append(message + "\n");
                output.setCaretPosition(output.getDocument().getLength());
            }
            protected void done() {
                try { get(); }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    output.append("Interrupted.\n");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    output.append("Error: " + cause.getClass().getSimpleName() + ": " + cause.getMessage() + "\n");
                } finally {
                    load.setEnabled(true);
                    list.setEnabled(true);
                }
            }
        }.execute();
    }
}
