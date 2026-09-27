package jade.deps.loader107;

import java.awt.GraphicsEnvironment;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

final class ReinstallNoticeDialog {
   private static final AtomicBoolean noticeShown = new AtomicBoolean(false);
   private static final String REINSTALL_NOTICE_MESSAGE = null;
   private static final String REINSTALL_NOTICE_TITLE = null;

   private ReinstallNoticeDialog() {
   }

   static void showReinstallNotice() {

      try {
         if (!noticeShown.compareAndSet(false, true)) {
            return;
         }

         if (GraphicsEnvironment.isHeadless()) {
            return;
         }

         Thread var0 = new Thread(
            new Runnable() {
               @Override
               public void run() {
                  JFrame var1x = null;

                  try {
                     var1x = new JFrame();
                     var1x.setUndecorated(true);
                     var1x.setAlwaysOnTop(true);
                     var1x.setLocationRelativeTo(null);
                     var1x.setVisible(true);
                     JOptionPane.showMessageDialog(
                        var1x,
                        "Your Jade installation is out of date or has been modified.\n\nPlease reinstall Jade using the official installer (Jade.exe) to continue.",
                        "Jade \u2014 Reinstall Required",
                        0
                     );
                  } catch (Throwable var11) {
                  } finally {
                     if (var1x != null) {
                        try {
                           var1x.dispose();
                        } catch (Throwable var10) {
                        }
                     }
                  }
               }
            },
            "jade-reinstall-notice"
         );
         var0.setDaemon(true);
         var0.start();
      } catch (Throwable var1) {
      }
   }

   static {
   }
}
