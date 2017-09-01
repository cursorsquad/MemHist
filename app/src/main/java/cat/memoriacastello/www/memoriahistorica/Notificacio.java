package cat.memoriacastello.www.memoriahistorica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

    /*
        Esta classe i el seu respectiu xml són per a mostrar missatges
        llargs que necessiten un temps considerable per a llegir-se. En
        principi està pensada per a la depuració i mostrar els errors
        que poden aparéixer mitjançant la captura d'errors.

        Utilitzeu el codi següent:
        ...
            } catch (Exception e) {
                StackTraceElement ste[] = e.getStackTrace();
                String s = "";
                for (StackTraceElement e : ste)
                    s += String.format("\n\t\t%s", e);
                f.enviaMissatge(s);
            }
        ...

    */
public class Notificacio extends AppCompatActivity {
    private TextView p8tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacio);

        p8tv1 = (TextView) findViewById(R.id.p8tv1);

        ompleText();
    }

    private void ompleText(){
        Bundle b = getIntent().getExtras();
        String text = b.getString("text");
        p8tv1.setText(text);
    }

    public void arrere(View v){
        finish();
    }
}
