package sv.edu.catolica.vlsp_inventarios;

import android.*;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Clases.Categoria;
import Clases.Producto;

public class AgregarProductos extends Fragment {
    ConnectionClass con = new ConnectionClass();
    Connection cn = con.CONN();

    EditText NombreP, DescripP, CantP, CostoP, PrecioVentaP,FechaVencP;
    Spinner ListaCategorias;
    Button Add, BtnImg;
    String Nombre, Descrip, FechaV;
    float Stock;
    Double Costo, PrecioV;

    int idEmpresa;

    public List<String> ListS(){
        List<String> values = new ArrayList<>();
        try{
            String query="SELECT * FROM CATEGORIA WHERE idEmpresa=1";
            Statement st=cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                values.add(rs.getInt("idCat") + " - " + rs.getString("cat_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return values;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        idEmpresa = getArguments().getInt("idEmpresa");

        return inflater.inflate(R.layout.fragment_agregarproductos, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //------------------------------------------------------------
        NombreP=(EditText)getView().findViewById(R.id.txtPnombre);
        DescripP=(EditText)getView().findViewById(R.id.txtPdescrip);
        CantP=(EditText)getView().findViewById(R.id.txtCant);
        CostoP=(EditText)getView().findViewById(R.id.txtPcosto);
        PrecioVentaP=(EditText)getView().findViewById(R.id.txtPprecio);
        FechaVencP=(EditText)getView().findViewById(R.id.txtPvence);
        ListaCategorias=(Spinner)getView().findViewById(R.id.spnCat);
        BtnImg=(Button)getView().findViewById(R.id.imgProd);
        Add=(Button)getView().findViewById(R.id.btnAggP);
        //------------------------------------------------------------
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemText = (String) ListaCategorias.getSelectedItem();
                String[] s =  itemText.split("-");
                String idCat = s[0].trim();
                Nombre=NombreP.getText().toString();
                Descrip=DescripP.getText().toString();
                FechaV=FechaVencP.getText().toString();
                Stock=Float.parseFloat(CantP.getText().toString());
                Costo=Double.parseDouble(CostoP.getText().toString());
                PrecioV=Double.parseDouble(PrecioVentaP.getText().toString());
                try{
                    String query = "INSERT INTO PRODUCTO (idEmpresa,idCat,producto_name,producto_stock,producto_price,producto_cost,producto_desc,producto_exp_date) VALUES " +
                            "("+idEmpresa+","+idCat+",'"+Nombre+"',"+Stock+","+PrecioV+","+Costo+",'"+Descrip+"','"+FechaV+"')";
                    PreparedStatement pst=cn.prepareStatement(query);
                    pst.executeUpdate();
                    Toast.makeText(getActivity(), "Registro agregado con éxito", Toast.LENGTH_SHORT).show();
                }catch (SQLException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error al agregar registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //------------------------------------------------------------
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,ListS());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ListaCategorias.setAdapter(dataAdapter);
    }
}
