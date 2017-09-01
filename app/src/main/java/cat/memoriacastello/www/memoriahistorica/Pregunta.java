package cat.memoriacastello.www.memoriahistorica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Pregunta extends AppCompatActivity {
    //Atributs
    private int index;
    private int respCorr;
    private TextView p3tv1;
    private RadioButton p3rb1;
    private RadioButton p3rb2;
    private RadioButton p3rb3;

    //Instància
    Fitxer f = new Fitxer(this);

    //Mètodes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        index = Integer.parseInt(getIntent().getExtras().getString("index"));

        p3tv1 = (TextView) findViewById(R.id.p3tv1);
        p3rb1 = (RadioButton) findViewById(R.id.p3rb1);
        p3rb2 = (RadioButton) findViewById(R.id.p3rb2);
        p3rb3 = (RadioButton) findViewById(R.id.p3rb3);

        DadesPregunta pregunta = MainActivity.preguntesPartida[index];

        p3tv1.setText(String.format("[%02d] %s", pregunta.getId(), pregunta.getEnunciat()));
        p3rb1.setText(pregunta.getResposta1());
        p3rb2.setText(pregunta.getResposta2());
        p3rb3.setText(pregunta.getResposta3());
        respCorr = pregunta.getRespCorrecta()-1;
    }

    public void comprova(View v){
        int estat;
        if (!p3rb1.isChecked() && !p3rb2.isChecked() && !p3rb3.isChecked()) {
            estat = 0;
            Frases.espenta(this);
        } else if (
                p3rb1.isChecked() && respCorr == 0 ||
                p3rb2.isChecked() && respCorr == 1 ||
                p3rb3.isChecked() && respCorr == 2
                ) {
            estat = 1;
            Frases.felicita(this);
            MainActivity.contestades += 1;
        } else {
            estat = -1;
            Frases.anima(this);
            MainActivity.contestades += 1;
        }
        MainActivity.preguntesPartida[index].setEstat(estat);
        f.desaFitxer("historial", String.format("[id:%d\te:%d]", MainActivity.preguntesPartida[index].getId()-1, estat));
        finish();
    }
}
