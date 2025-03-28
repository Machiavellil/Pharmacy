package com.mycompany.pharmacy;
import com.mycompany.pharmacy.ui.PharmacyCLI;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.PrintStream;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.PrintStream;

/*  
 *  🌟 Welcome, Dr Rodina! 🌟  
 *  
 *  Here’s a quick guide on how to use the system.  
 *  
 *  📌 We have **five** different types of users:  
 *  ───────────────────────────────────────  
 *  🔹 Admin  
 *  🔹 Customer  
 *  🔹 Pharmacist  
 *  🔹 Doctor  
 *  🔹 Delivery Agent  
 *  ───────────────────────────────────────  
 *  
 *  🔑 Below are test login credentials for each user.  
 *  (Note: All actual passwords in the TXT file are **hashed**.)  
 *  
 *  🏆 **Admin**  
 *  📧 Email: `root`  
 *  🔒 Password: `root`  
 *  
 *  👤 **Customer**  
 *  📧 Email: `ali`  
 *  🔒 Password: `123`  
 *  
 *  💊 **Pharmacist**  
 *  📧 Email: `mo`  
 *  🔒 Password: `123`  
 *  
 *  🩺 **Doctor**  
 *  📧 Email: `doc`  
 *  🔒 Password: `123`  
 *  
 *  🚚 **Delivery Agent**  
 *  📧 Email: `osama`  
 *  🔒 Password: `123`  
 *  
 *  ⚙️ **System Workflow Overview:**  
 *  ───────────────────────────────────────  
 *  🖥️ `PharmacyCLI` → Handles **all UI elements** for the command-line interface, including printing and user interactions.  
 *  🔄 It calls appropriate functions from `MenuManager` based on the selected user type.  
 *  🛠️ `MenuManager` → Acts as a **controller**, calling functions from the **class handler** or directly interacting with specific classes.  
 *  
 *  ⚠️ **Note:**  
 *  Colors in the terminal may not work in NetBeans. Apologies for any inconvenience!  
 *  
 *  Enjoy using the system! 🚀  
 */


public class Pharmacy {
    public static void main(String[] args) throws IOException {
        System.setProperty("file.encoding", "UTF-8");
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.setOut(ps);

  
        System.out.println(Ansi.ansi().eraseScreen().fgBrightGreen().a("✅ UTF-8").reset());

        PharmacyCLI cli = new PharmacyCLI();
        cli.start();

    }
}

