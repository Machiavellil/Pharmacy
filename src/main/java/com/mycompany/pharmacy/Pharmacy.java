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

public class Pharmacy {
    public static void main(String[] args) throws IOException {
        System.setProperty("file.encoding", "UTF-8");
        PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.setOut(ps);

        AnsiConsole.systemInstall();
        System.setProperty("jansi.passthrough", "true");

        System.out.println(Ansi.ansi().eraseScreen().fgBrightGreen().a("✅ UTF-8 & Colors Active!").reset());

        PharmacyCLI cli = new PharmacyCLI();
        cli.start();

        AnsiConsole.systemUninstall();
    }
}

