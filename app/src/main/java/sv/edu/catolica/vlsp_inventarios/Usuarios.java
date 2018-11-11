package sv.edu.catolica.vlsp_inventarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Usuario;


public class Usuarios extends Fragment {
    ConnectionClass con = new ConnectionClass();
   Connection conn = con.CONN();

    EditText txtNombre, txtApellido, txtUser, txtEmail, txtPass1, txtPass2;
    int idEmpresa;
    Button btnOK;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        idEmpresa = getArguments().getInt("idEmpresa");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuarios, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtNombre = getActivity().findViewById(R.id.txtNombre);
        txtApellido = getActivity().findViewById(R.id.txtApellido);
        txtUser = getActivity().findViewById(R.id.txtUser);
        txtEmail = getActivity().findViewById(R.id.txtEmail);
        txtPass1= getActivity().findViewById(R.id.txtPass1);
        txtPass2 = getActivity().findViewById(R.id.txtPass2);
        btnOK=getActivity().findViewById(R.id.btnNewUser);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario user = new Usuario();
                user.nombre = txtNombre.getText().toString();
                user.apellido = txtApellido.getText().toString();
                user.usario = txtUser.getText().toString();
                user.email = txtEmail.getText().toString();
                user.pass = txtPass1.getText().toString();
                user.pass2 = txtPass2.getText().toString();
                user.type=1;

                if(!user.nombre.equals("") && !user.apellido.equals("") && !user.usario.equals("") && !user.email.equals("") && !user.pass.equals("") && !user.pass2.equals("")){
                    if(user.nombre.length() > 4 && user.apellido.length() > 4 && user.usario.length() > 4 && user.email.length() > 10){
                        if(user.pass.length() > 6 && user.pass2.length() > 6 && user.pass.equals(user.pass2)){
                            try {
                                crearEmpresaUsuario(idEmpresa,user);
                            } catch (SQLException e) {
                                Log.e("crearEmpresaUsuario", e.toString());
                            }
                        }
                    }
                }
            }
        });

    }





    public void crearEmpresaUsuario(int id, Usuario user) throws SQLException {
try{
                //insertando usuario
               String query = "INSERT INTO USUARIO(idEmpresa, user_name, user_lname, user_user,user_mail, user_password,user_type) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement st2 = this.conn.prepareStatement(query);
                st2.setInt(1,id);
                st2.setString(2,user.nombre);
                st2.setString(3,user.apellido);
                st2.setString(4,user.usario);
                st2.setString(5,user.email);
                st2.setString(6,user.pass);
                st2.setInt(7,user.type);

    if(st2.executeUpdate() > 0){
        txtApellido.setText("");
        txtEmail.setText("");
        txtNombre.setText("");
        txtPass1.setText("");
        txtPass2.setText("");
        txtUser.setText("");
        Toast.makeText(getContext(),"Usuario Creado correctamente", Toast.LENGTH_SHORT).show();
    }


    }catch (Exception e)
{
    Toast.makeText(getContext(), e.toString(),Toast.LENGTH_SHORT).show();
}
    }

    }

