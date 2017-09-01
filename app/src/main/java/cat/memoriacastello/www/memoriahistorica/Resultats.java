package cat.memoriacastello.www.memoriahistorica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Resultats extends AppCompatActivity {
    //Atributs
    private TextView p4tv1;

    //Instància
    Fitxer f = new Fitxer(this);

    //Mètodes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        p4tv1 = (TextView) findViewById(R.id.p4tv1);
    }

    @Override
    protected void onStart(){
        super.onStart();

        int suma = MainActivity.benContestades.size();
        for (DadesPregunta pregunta: MainActivity.preguntesPartida)
            if (pregunta != null && pregunta.getEstat()>0) suma += 1;

        if (MainActivity.contestades == MainActivity.MAX_PREG_PER_PARTIDA && MainActivity.horaFi == 0)
            MainActivity.horaFi = java.util.Calendar.getInstance().getTimeInMillis();
        long tempsFinal = MainActivity.horaFi == 0 ?
                java.util.Calendar.getInstance().getTimeInMillis() :
                MainActivity.horaFi;
        String temps = obtéDifTemps(tempsFinal, MainActivity.horaInici);
        p4tv1.setText(
                String.format("Heu obtingut una puntuació de %d punts\nen %s", suma, temps)
        );
    }

    public void seguix(View v){
        finish();
    }

    public void nova(View v){
        /*
        S'ha de reiniciar:
        - l'hora d'inici (camp horaInici del MainActivity).
        - el nombre de preguntesJoc contestades (camp contestades del MainActivity).

        S'ha de modificar la variable reset que indica si estem reiniciat.
         */

        MainActivity.horaInici = java.util.Calendar.getInstance().getTimeInMillis();
        MainActivity.contestades = 0;
        MainActivity.reset = true;
        String ara = new SimpleDateFormat("yyy/MM/dd HH:mm:ss").format(new Date());

        f.desaFitxer(
                "historial",
                String.format(
                        "[end:%1$s]\n[begin:%1$s]",
                        ara
                )
        );
        finish();
    }

    public void surt(View v){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("surt", true);

        f.desaFitxer(
                "historial",
                String.format(
                        "[end:%s]",
                        new SimpleDateFormat(
                                "yyy/MM/dd HH:mm:ss"
                        ).format(
                                new Date()
                        )
                )
        );

        startActivity(intent);
        Frases.acomiada(this);
    }

    public void reinicia(View v){
        MainActivity.horaInici = java.util.Calendar.getInstance().getTimeInMillis();
        MainActivity.contestades = 0;
        for (int i = 0; i < MainActivity.preguntesPartida.length; i++) {
            if (MainActivity.preguntesPartida[i] != null)
                MainActivity.preguntesPartida[i].neteja();
            MainActivity.preguntesPartida[i] = null;
        }
        MainActivity.reset = true;
        f.esborraFitxer("historial");
        finish();
    }

    private String concatena(String vector[]) {
        /*
         *  Este mètode permet concatenar els elements d'una llista amb la coma
         *  de separador i la conjunció entre el darrer i el penúltim element si s'escau.
         */
        StringBuilder sb = new StringBuilder();
        int i = 0, e = 0; //'i' es l'index dels vectors i 'e' és el nombre d'elements no nuls.

        for (String s: vector) {
            if (s == null) {
                i++;
                continue;
            }
            e++;

            if (i + 1 == vector.length && e > 1)
                sb.append(" i ");

            sb.append(s);
            i++;
            if (i < vector.length-1)
                sb.append(", ");
            else if (i == vector.length)
                sb.append(".");
        }

        return sb.toString();
    }

    private String obtéDifTemps(long t1, long t2){
        long diff = t1 - t2;
        long vector[] = {
                diff / (60 * 60 * 1000) % 24,
                diff / (60 * 1000) % 60,
                diff / 1000 % 60
        };
        String unitats_sg[] = {"hora", "minut", "segon"};
        String unitats_pl[] = {"hores", "minuts", "segons"};
        String resultat[] = {null, null, null};
        for (int i = 0; i < vector.length; i++)
            if (vector[i]>0) resultat[i] = String.format("%d %s", vector[i], vector[i] != 1 ? unitats_pl[i] : unitats_sg[i]);
        return concatena(resultat);
    }
}
