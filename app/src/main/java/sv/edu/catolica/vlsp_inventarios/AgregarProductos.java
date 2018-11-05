package sv.edu.catolica.vlsp_inventarios;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AgregarProductos extends Fragment {
    Spinner ListaCategorias;
    ArrayList<String> values;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregarproductos, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListaCategorias=(Spinner)getView().findViewById(R.id.spnCat);
        ConnectionClass con = new ConnectionClass();
        Connection cn = con.CONN();
        Statement pst;
        values = new ArrayList<String>();

        try{
            pst=(Statement)cn.createStatement();
            String query="SELECT cat_name FROM CATEGORIA WHERE idEmpresa=1";
            ResultSet rs = pst.executeQuery(query);
            values.add("Seleccione");
            while (rs.next()){
                rs.getInt(0);
                rs.getInt(1);
                Toast.makeText(getActivity(), rs.getString(2), Toast.LENGTH_SHORT).show();
                values.add(rs.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ListaCategorias.setAdapter(dataAdapter);
    }
}
