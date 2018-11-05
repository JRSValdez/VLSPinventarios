package sv.edu.catolica.vlsp_inventarios;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import Clases.Usuario;

public class LoginActivity extends AppCompatActivity {

    ConnectionClass conn;
    private EditText txtUser, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        this.conn = new ConnectionClass();

    }

    public void iniciar(View v) throws SQLException {
        if(this.conn.CONN() != null){
            this.txtUser = findViewById(R.id.txtUser);
            this.txtPass = findViewById(R.id.txtPass);

            Usuario user = new Usuario(txtUser.getText().toString(), txtPass.getText().toString());
            user = this.conn.iniciar_sesion(user);
            if(user.idEmpresa > 0){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Revise su conexión", Toast.LENGTH_LONG).show();
        }
    }
}
