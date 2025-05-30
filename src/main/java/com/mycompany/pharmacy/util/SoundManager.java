package com.mycompany.pharmacy.util;

import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.IOException;

public class SoundManager {
    private static Player player;
    private static boolean isPlaying = false;
    private static Thread musicThread;

    public static void playBackgroundMusic() {
        try {
            if (!isPlaying) {
                FileInputStream fis = new FileInputStream("src/main/java/com/mycompany/pharmacy/util/toby fox - UNDERTALE Soundtrack - 04 Fallen Down.mp3");
                player = new Player(fis);
                
                musicThread = new Thread(() -> {
                    try {
                        while (isPlaying) {
                            if (!player.play(1)) {
                                // If the song ended, restart it
                                player.close();
                                fis.close();
                                FileInputStream newFis = new FileInputStream("src/main/java/com/mycompany/pharmacy/util/toby fox - UNDERTALE Soundtrack - 04 Fallen Down.mp3");
                                player = new Player(newFis);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Error in music thread: " + e.getMessage());
                    }
                });
                
                isPlaying = true;
                musicThread.start();
                System.out.println("✅ Background music started");
            }
        } catch (Exception e) {
            System.out.println("❌ Error playing background music: " + e.getMessage());
        }
    }

    public static void stopBackgroundMusic() {
        if (isPlaying) {
            isPlaying = false;
            if (player != null) {
                player.close();
                player = null;
            }
            if (musicThread != null) {
                musicThread.interrupt();
                musicThread = null;
            }
            System.out.println("✅ Background music stopped");
        }
    }

    public static void toggleBackgroundMusic() {
        if (isPlaying) {
            stopBackgroundMusic();
        } else {
            playBackgroundMusic();
        }
    }
} 