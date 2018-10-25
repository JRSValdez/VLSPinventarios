package sv.edu.catolica.vlsp_inventarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ConnectionClass conn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        this.conn = new ConnectionClass();

    }

    public void iniciar(View v){
        if(this.conn.CONN() != null){
            Toast.makeText(this, "LIIIIIISTOOOOO.mp3", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(LoginActivity.this, Configuracion.class);
            //startActivity(intent);
            startActivity(new Intent(this,MainActivity.class));
        }
        else{
            Toast.makeText(this, "NOUUUUUUUUUUUUU", Toast.LENGTH_LONG).show();
        }
    }
}
