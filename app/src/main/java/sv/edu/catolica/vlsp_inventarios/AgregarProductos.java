package sv.edu.catolica.vlsp_inventarios;

import android.*;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import Clases.Categoria;
import Clases.Producto;

public class AgregarProductos extends Fragment {
    ConnectionClass con = new ConnectionClass();
    Connection cn = con.CONN();

    EditText NombreP, DescripP, CantP, CostoP, PrecioVentaP,FechaVencP;
    Spinner ListaCategorias;
    Button Add;
    String Nombre, Descrip, FechaV, Stock, Costo, PrecioV;
    ImageView imgCat;

    int idEmpresa;

    public List<String> ListS(){
        idEmpresa = getArguments().getInt("idEmpresa");
        List<String> values = new ArrayList<>();
        values.add("Seleccione");
        try{
            String query="SELECT * FROM CATEGORIA WHERE idEmpresa="+idEmpresa;
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
        Add=(Button)getView().findViewById(R.id.btnAggP);
        imgCat=(ImageView)getView().findViewById(R.id.imgShow);
        idEmpresa=(int)getArguments().getInt("idEmpresa");

        CantP.setFilters(new InputFilter[]{new InputFilter() {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                int indexPoint = spanned.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
                if (indexPoint == -1)
                    return charSequence;
                int decimals = i3 - (indexPoint+1);
                return decimals < 2 ? charSequence : "";
            }
        }});
        CostoP.setFilters(new InputFilter[]{new InputFilter() {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                int indexPoint = spanned.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
                if (indexPoint == -1)
                    return charSequence;
                int decimals = i3 - (indexPoint+1);
                return decimals < 2 ? charSequence : "";
            }
        }});
        PrecioVentaP.setFilters(new InputFilter[]{new InputFilter() {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                int indexPoint = spanned.toString().indexOf(decimalFormatSymbols.getDecimalSeparator());
                if (indexPoint == -1)
                    return charSequence;
                int decimals = i3 - (indexPoint+1);
                return decimals < 2 ? charSequence : "";
            }
        }});
        //------------------------------------------------------------
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nombre=NombreP.getText().toString();
                Descrip=DescripP.getText().toString();
                FechaV=FechaVencP.getText().toString();
                Stock=CantP.getText().toString();
                Costo=CostoP.getText().toString();
                PrecioV=PrecioVentaP.getText().toString();
                if(Nombre.isEmpty() || Descrip.isEmpty() || FechaV.isEmpty() || Stock.isEmpty() || Costo.isEmpty() || PrecioV.isEmpty()){
                    Toast.makeText(getActivity(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                }else if(ListaCategorias.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "Seleccione una categoría", Toast.LENGTH_SHORT).show();
                }else{
                    String itemText = (String) ListaCategorias.getSelectedItem();
                    String[] s =  itemText.split("-");
                    String idCat = s[0].trim();
                    try{
                        int Default=0;
                        String query = "INSERT INTO PRODUCTO (idEmpresa,idCat,producto_name,producto_stock,producto_price,producto_cost,producto_desc,producto_exp_date,eliminado) VALUES " +
                                "("+idEmpresa+","+idCat+",'"+Nombre+"',"+Stock+","+PrecioV+","+Costo+",'"+Descrip+"','"+FechaV+"',"+Default+")";
                        PreparedStatement pst=cn.prepareStatement(query);
                        pst.executeUpdate();
                        Toast.makeText(getActivity(), "Registro agregado con éxito", Toast.LENGTH_SHORT).show();
                    }catch (SQLException e){
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error al agregar registro "+e, Toast.LENGTH_LONG).show();
                    }finally {

                    }
                }
            }
        });
        ListaCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int resID = getResources().getIdentifier("r"+String.valueOf(i),"drawable",getActivity().getPackageName());
                if(i==0){
                    imgCat.setImageResource(R.drawable.producto);
                }else{
                    imgCat.setImageResource(resID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //------------------------------------------------------------
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,ListS());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ListaCategorias.setAdapter(dataAdapter);
    }
}
