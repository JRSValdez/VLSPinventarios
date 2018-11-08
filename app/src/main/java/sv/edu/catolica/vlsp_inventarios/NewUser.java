package sv.edu.catolica.vlsp_inventarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Usuario;

public class NewUser extends AppCompatActivity {

    EditText txtNombre, txtApellido, txtUser, txtEmail, txtPass1, txtPass2;
    Connection conn;
    String empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtUser = findViewById(R.id.txtUser);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass1= findViewById(R.id.txtPass1);
        txtPass2 = findViewById(R.id.txtPass2);

        ConnectionClass con = new ConnectionClass();
        conn = con.CONN();
        empresa = getIntent().getStringExtra("empresa");
    }

    public void registarEmpresaUsuario(View view){

        Usuario user = new Usuario();
        user.nombre = txtNombre.getText().toString();
        user.apellido = txtApellido.getText().toString();
        user.usario = txtUser.getText().toString();
        user.email = txtEmail.getText().toString();
        user.pass = txtPass1.getText().toString();
        user.pass2 = txtPass2.getText().toString();


        if(!user.nombre.equals("") && !user.apellido.equals("") && !user.usario.equals("") && !user.email.equals("") && !user.pass.equals("") && !user.pass2.equals("")){
            if(user.nombre.length() > 4 && user.apellido.length() > 4 && user.usario.length() > 4 && user.email.length() > 10){
                if(user.pass.length() > 6 && user.pass2.length() > 6 && user.pass.equals(user.pass2)){
                    try {
                        this.crearEmpresaUsuario(empresa,user);
                    } catch (SQLException e) {
                        Log.e("crearEmpresaUsuario", e.toString());
                    }
                }
            }
        }
    }

    private void crearEmpresaUsuario(String empresa, Usuario user) throws SQLException {
        //Agreggando la empresa
        String query = "INSERT INTO EMPRESA VALUES(?,?)";
        PreparedStatement st = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        st.setString(1,empresa);
        st.setString(2,"");

        if(st.executeUpdate() > 0){
            int idEmpresa = 0;
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                idEmpresa = generatedKeys.getInt(1);

                //insertando usuario
                query = "INSERT INTO USUARIO(idEmpresa, user_name, user_lname, user_user,user_mail, user_password) VALUES(?,?,?,?,?,?)";
                PreparedStatement st2 = this.conn.prepareStatement(query);
                st2.setInt(1,idEmpresa);
                st2.setString(2,user.nombre);
                st2.setString(3,user.apellido);
                st2.setString(4,user.usario);
                st2.setString(5,user.email);
                st2.setString(6,user.pass);

                if(st2.executeUpdate() > 0){
                    Toast.makeText(this,"Usuario Creado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
            } else Toast.makeText(this,"Error al obtener id empresa", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this,"Error al insertar empresa", Toast.LENGTH_SHORT).show();
    }

}
