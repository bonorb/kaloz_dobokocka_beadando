package com.example.beadando;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView diceImage;
    TextView soronJovoText, korAranyText, kaloz1Text, kaloz2Text;
    Button dobasButton, stopButton;

    int[] diceImages = {
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };

    int currentPlayer = 0; // 0 = Kalóz 1, 1 = Kalóz 2
    int korArany = 0;
    int[] osszPont = {0, 0};

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Összekötés az XML elemekkel
        diceImage = findViewById(R.id.dice_id);
        soronJovoText = findViewById(R.id.soron_jovo_id);
        korAranyText = findViewById(R.id.pontszam_id);
        kaloz1Text = findViewById(R.id.textView5);
        kaloz2Text = findViewById(R.id.textView6);
        dobasButton = findViewById(R.id.dobas_id);
        stopButton = findViewById(R.id.stop_id);

        dobasButton.setOnClickListener(v -> dobas());
        stopButton.setOnClickListener(v -> megall());

        frissitUI();
    }

    void dobas() {
        int dobott = random.nextInt(6) + 1;
        diceImage.setImageResource(diceImages[dobott - 1]);

        if (dobott == 1) {
            korArany = 0;
            jatekosValtas();
        } else {
            korArany += dobott;
        }
        frissitUI();
    }

    void megall() {
        osszPont[currentPlayer] += korArany;
        korArany = 0;

        if (osszPont[currentPlayer] >= 100) {
            gyozelem();
        } else {
            jatekosValtas();
        }
        frissitUI();
    }

    void jatekosValtas() {
        currentPlayer = (currentPlayer == 0) ? 1 : 0;
    }

    void frissitUI() {
        soronJovoText.setText("Kalóz " + (currentPlayer + 1) + " jön");
        korAranyText.setText("Kör arany: " + korArany);
        kaloz1Text.setText("🏴‍☠️ Kalóz 1: " + osszPont[0]);
        kaloz2Text.setText("🏴‍☠️ Kalóz 2: " + osszPont[1]);
    }

    void gyozelem() {
        new AlertDialog.Builder(this)
                .setTitle("Győzelem!")
                .setMessage("Kalóz " + (currentPlayer + 1) + " nyert!")
                .setPositiveButton("Új játék", (dialog, which) -> ujJatek())
                .setCancelable(false)
                .show();
    }

    void ujJatek() {
        osszPont[0] = 0;
        osszPont[1] = 0;
        korArany = 0;
        currentPlayer = 0;
        frissitUI();
    }
}
