package cat.memoriacastello.www.memoriahistorica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    //Atributs
    protected static String nomUsuari;
      //Vector amb totes les preguntesJoc.
    protected static final int MAX_PREGUNTES = 42;
    protected static DadesPregunta preguntesJoc[] = new DadesPregunta[MAX_PREGUNTES];
      //Vector amb només 20 preg. del joc en curs.
    protected static final int MAX_PREG_PER_PARTIDA = 20;
    protected static DadesPregunta preguntesPartida[] = new DadesPregunta[MAX_PREG_PER_PARTIDA];
    protected static long horaInici;
    protected static long horaFi;
    protected static int contestades;
    protected static ArrayList<Integer> benContestades = new ArrayList<>();
    protected static int puntuació;
    protected static boolean reset = true;

    private EditText p1et1;

    Fitxer f = new Fitxer(this);

    //Metodes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Codi per a tancar l'aplicació (relacionada amb el botó 'IX DEL JOC'
        //de la funció surt() al fitxer Resultats.java).
        if (getIntent().getBooleanExtra("surt", false)){
            finish();
            return;
        }

        p1et1 = (EditText) findViewById(R.id.p1et1);

        lligDades();

        f.mostraContingut("historial");

        String vector[] = f.lligFitxer("historial");
        for(String línia : vector)
            if (línia.startsWith("[u:")) {
                String s = "\\[u:(\\w+)\\]";
                Pattern p = Pattern.compile(s);
                Matcher m = p.matcher(línia);
                String usuari = "";
                while(m.find()){
                    usuari = m.group(1);
                    break;
                }
                p1et1.setText(usuari);
                break;
            }
        if (!vector[vector.length-1].startsWith("[begin:"))
            f.desaFitxer(
                    "historial",
                    String.format(
                            "[begin:%s]",
                            new SimpleDateFormat(
                                    "yyy/MM/dd HH:mm:ss"
                            ).format(new Date())
                    )
            );
    }

    private void lligDades() {
        /*
        TODO: fer que després de llegir les dades canvie l'ordre tant de les preguntesJoc com
        de les respostes. Estaria molt bé que n'hi haja més de 20 preguntesJoc, jo en posaria
        40, encara que cada preguntesPartida només en mostraria 20. Quan es reiniciara el joc canviarien
        les 20 preguntesJoc. L'únic que sabria les preguntesJoc seria el profe.

        TODO: Estaria bé que el profe tinguera un accés especial per a conéixer la resposta a la
        pregunta.
        */

        String msg = "";
        Scanner fitxer;
        try {
            fitxer = new Scanner(getResources().openRawResource(R.raw.preguntes));
            String línia;
            int i = 0;
            while (fitxer.hasNextLine()) {
                línia = fitxer.nextLine();
                if (línia.equals("") || línia.charAt(0) == '#') continue;
                String vector[] = línia.split("\t");
                if (vector.length < 5 || vector.length > 6) {
                    //Hi ha hagut un error en la construcció de la pregunta
                    //falten o sobren tabulacions.
                    preguntesJoc[i++] = new DadesPregunta(
                            i+1,
                            String.format("#ERROR DE LECTURA ([#%d] %d tabs)", i, vector.length),
                            "#resp1",
                            "#resp2",
                            "#resp3",
                            1
                    );
                    continue;
                }
                String enunciat = vector[0];
                String resp1 = vector[1];
                String resp2 = vector[2];
                String resp3 = vector[3];
                int iRespCorr = Integer.parseInt(vector[4]);
                DadesPregunta dp;
                if (vector.length==5)
                    dp = new DadesPregunta(
                            i+1, enunciat, resp1, resp2, resp3, iRespCorr
                    );
                else {
                    String imatge = vector[5];
                    dp = new DadesPregunta(
                            i+1, enunciat, resp1, resp2, resp3, iRespCorr, imatge
                    );
                }
                preguntesJoc[i++] = dp;
            }
        } catch (Exception e) {
            msg = "No s'ha pogut accedir al fitxer.";
        }
        if (msg.length()>0) Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void inicia(View v){
        nomUsuari = p1et1.getText().toString();
        if (nomUsuari.length() == 0){
            String msg = "El camp de l'usuari no pot quedar buid.";
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(this, PaginaPrincipal.class));
        }
    }

    public void apropDe(View v) {
        startActivity(new Intent(this, ApropDe.class));
    }
}
