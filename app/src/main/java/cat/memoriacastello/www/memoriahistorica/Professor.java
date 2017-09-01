package cat.memoriacastello.www.memoriahistorica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Professor extends AppCompatActivity {
    private EditText p6et1, p6et2;
    private TextView p6tv1, p6tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        p6et1 = (EditText) findViewById(R.id.p6et1);
        p6et2 = (EditText) findViewById(R.id.p6et2);

        p6tv1 = (TextView) findViewById(R.id.p6tv1);
        p6tv2 = (TextView) findViewById(R.id.p6tv2);
    }

    private static String generatePassword(String passwordToHash, String encrypt){
        String generatedPassword = null;
        String salt = "MemòriaHistòrica";
        try {
            MessageDigest md = MessageDigest.getInstance(encrypt);
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length; i++)
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static String generatePassword(String passwordToHash) {
        return generatePassword(passwordToHash, "SHA-256");
    }

    private static boolean comparePasswords(String s) {
        s = generatePassword(s);
        String password = "f48be460004dbea7b66f9965d464b341ff7c68b9e04ab5030ac1282caa0a78c0"; //Mem17
        return s.equals(password);
    }

    public void fesVisible(View v){
        if (p6et2.getText().toString().equals(""))
            Toast.makeText(this, "El camp de l'id de pregunta no pot estar buid", Toast.LENGTH_LONG).show();
        else {
            int idpreg = Integer.parseInt(p6et2.getText().toString());
            String errMsg = "", clau = p6et1.getText().toString();
            if (clau.equals("")) {
                errMsg = "Per favor, introduïu la contrassenya facilitada al professorat.";
            } else if (!comparePasswords(clau)) {
                errMsg = "La contrasenya és incorrecta.";
            } else if (idpreg < 1 || idpreg > MainActivity.preguntesJoc.length) {
                errMsg = String.format("L'id de la pregunta ha de ser un nombre entre 1 i %d.", MainActivity.preguntesJoc.length);
            }
            if (errMsg.length() > 0)
                Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show();

            if (comparePasswords(clau) && idpreg > 0 && idpreg < MainActivity.preguntesJoc.length) {
                p6tv1.setVisibility(View.VISIBLE);
                p6tv2.setVisibility(View.VISIBLE);
                DadesPregunta preg = MainActivity.preguntesJoc[idpreg - 1];
                String resp;
                p6tv1.setText(preg.getEnunciat());
                switch (preg.getRespCorrecta()) {
                    case (1):
                        resp = preg.getResposta1();
                        break;
                    case (2):
                        resp = preg.getResposta2();
                        break;
                    case (3):
                        resp = preg.getResposta3();
                        break;
                    default:
                        resp = "Error";
                }
                p6tv2.setText(resp);
            }
        }
    }

    public void arrere(View v){
        finish();
    }
}
