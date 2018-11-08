package sv.edu.catolica.vlsp_inventarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrarEmpresa extends AppCompatActivity {

    EditText txtEmpresa;
    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_empresa_layout);
        ConnectionClass con = new ConnectionClass();
        conn = con.CONN();
        txtEmpresa = findViewById(R.id.txtEmpresa);
    }

    public void newUser(View view) throws SQLException {
        String empresa = txtEmpresa.getText().toString();
        if(!empresa.equals("") && empresa.length() > 5){

            String query = "SELECT count(*) FROM empresa WHERE empresa_name = ?";
            PreparedStatement st = null;
            st = this.conn.prepareStatement(query);
            st.setString(1,empresa);

            ResultSet rs = st.executeQuery();
            int result = -1;
            while(rs.next()){
                result = rs.getInt(1);
            }
            if(result == 0){
                Intent intent = new Intent(this, NewUser.class);
                intent.putExtra("empresa", empresa);
                startActivity(intent);
            } else Toast.makeText(this,"Esta empresa ya esta registrada", Toast.LENGTH_SHORT).show();


        } else Toast.makeText(this,"Llene correctamente los campos", Toast.LENGTH_SHORT).show();
    }

}
