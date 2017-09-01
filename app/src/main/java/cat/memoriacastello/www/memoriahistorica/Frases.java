package cat.memoriacastello.www.memoriahistorica;

import android.content.Context;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by coet on 18/08/2017.
 *
 * En esta classe obtenim els missatges que apareixen al llarg del
 * desenvolupament del joc.
 */

public class Frases {
    //Atributs
    private static Random random = new Random();


    private static List<String> benvingudes = Arrays.asList(
            //Quan s'inicia el joc i no hi ha preguntesJoc conetestades
            "Hola %s, comencem!",
            "Hola %s, juguem!",
            "Que comence el joc, %s!"
    );


    private static List<String> comiats = Arrays.asList(
            //Quan l'usuari prem el botó "ix del joc".
            "Adéu %s, torna prompte!",
            "Adéu %s, has jugat molt bé!",
            "Adéu %s, ben jugat!",
            "Fins una altra %s!"
    );


    private static List<String> espentes = Arrays.asList(
            //Quan l'usuari ix d'alguna pregunta sense tria cap opcio.
            "No et deixes una tan fàcil!",
            "Hauries de tornar a provar!",
            "%s, que no és tan dificil!"
    );


    private static List<String> felicitacions = Arrays.asList(
            //Quan l'usuari encerta una resposta
            "Enhorabona!",
            "Enhorabona %s!",
            "Molt bé!",
            "Molt bé, tu sí que saps!",
            "Ups!, de pura potra, tat? xD"
    );


    private static List<String> ànims = Arrays.asList(
            //Quan l'usuari s'enganya de resposta.
            "Llàstima, a la propera ho faràs millor!",
            "Quasi, pero no!",
            "Agh, no! Era precisament l'altra!",
            "Segur que no volies dir l'altra? 9_9",
            "Llàstima, intenta millorar a la següent!"
    );

    //Quan l'usuari vol tornar a intenatr respondre una pregunta.
    private static List<String> prohibicions = Arrays.asList(
            "Ja heu contestat a esta pregunta.",
            "Ja has entrat per aquí.",
            "Ja la tens contestada.",
            "Ha! ha! ha! que vols repetir la pregunta?"
    );


    private static List<String> finalitzacions = Arrays.asList(
            //Quan l'usuari ha contestat a totes les preguntesJoc
            "Bé, sembla que ja ho tens tot, és hora de marxar ;)",
            "Bé %s, ja has contestat a tot! Reinicia i contesta a noves preguntesJoc!"
    );


    private static List<String> continuacions = Arrays.asList(
            //Quan l'usuari encara no ha respost a totes les preguntesJoc.
            "Ho estàs fent bé, %s, a vore si ho acabes!",
            "Segur que ho aconseguixes, endavant!",
            "Molt bé %s!, seguix així!"
    );

    //Mètodes
    private static void notifica(Context c, List<String> l){
        int index = random.nextInt(l.size());
        String frase = String.format(l.get(index), MainActivity.nomUsuari);
        Toast.makeText(c, frase, Toast.LENGTH_LONG).show();
    }

    public static void acomiada(Context context){
        notifica(context, comiats);
    }

    public static void saluda(Context context){
        notifica(context, benvingudes);
    }

    public static void espenta(Context context){
        notifica(context, espentes);
    }

    public static void felicita(Context context){
        notifica(context, felicitacions);
    }

    public static void anima(Context context){
        notifica(context, ànims);
    }

    public static void prohibeix(Context context){
        notifica(context, prohibicions);
    }

    public static void finalitza(Context context){
        notifica(context, finalitzacions);
    }

    public static void continua(Context context){
        notifica(context, continuacions);
    }
}
