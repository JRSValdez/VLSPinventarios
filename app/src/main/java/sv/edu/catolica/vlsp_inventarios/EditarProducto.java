package sv.edu.catolica.vlsp_inventarios;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import Clases.ClassListProductsItems;
import Clases.Producto;

public class EditarProducto extends Fragment {
    ConnectionClass con = new ConnectionClass();
    Connection cn = con.CONN();

    EditText NombreP, DescripP, CantP, CostoP, PrecioVentaP;
    Spinner ListaCategorias;
    Button Edit, BtnDelete;
    String Nombre, Descrip, Stock, Costo, PrecioV, Categ;
    ImageView imgCat;

    int idEmpresa, idPro;

    public List<String> ListS(){
        idEmpresa = getArguments().getInt("idEmpresa");
        List<String> values = new ArrayList<>();
        values.add("Seleccione");
        try{
            String query="SELECT * FROM CATEGORIA";
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
        idPro = getArguments().getInt("idPro");
        Log.e("idpro",".-.-.-.-.-.-.-.--.---.-.----.--.-.-."+String.valueOf(idPro));
        return inflater.inflate(R.layout.fragment_editarproductos, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //------------------------------------------------------------
        NombreP=(EditText)getView().findViewById(R.id.txtPnombre);
        NombreP.setText(String.valueOf(idPro));
        DescripP=(EditText)getView().findViewById(R.id.txtPdescrip);
        CantP=(EditText)getView().findViewById(R.id.txtCant);
        CostoP=(EditText)getView().findViewById(R.id.txtPcosto);
        PrecioVentaP=(EditText)getView().findViewById(R.id.txtPprecio);
        ListaCategorias=(Spinner)getView().findViewById(R.id.spnCat);
        BtnDelete=(Button)getView().findViewById(R.id.btnDelete);
        imgCat=(ImageView)getView().findViewById(R.id.imgShow);
        Edit=(Button)getView().findViewById(R.id.btnAggP);

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
        //-----------------------ELIMINAR REGISTRO-------------------------------------

        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setCancelable(false);
                b.setTitle("Confirmación");
                b.setMessage("¿Realmente desea eliminar este producto?");
                b.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            String query = "UPDATE PRODUCTO SET eliminado=1 WHERE idProducto="+idPro;
                            PreparedStatement pst=cn.prepareStatement(query);
                            pst.executeUpdate();
                            Toast.makeText(getActivity(), "Registro eliminado con éxito", Toast.LENGTH_SHORT).show();

                            ListarProductos nextFrag= new ListarProductos();

                            Bundle bundle=new Bundle();
                            nextFrag.setArguments(bundle);

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, nextFrag).commit();
                        }catch (SQLException e){
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error al eliminar registro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                b.create();
                b.show();
            }
        });

        //--------------------------------------------------

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nombre=NombreP.getText().toString();
                Descrip=DescripP.getText().toString();
                Stock=CantP.getText().toString();
                Costo=CostoP.getText().toString();
                PrecioV=PrecioVentaP.getText().toString();
                if(Nombre.isEmpty() || Descrip.isEmpty() || Stock.isEmpty() || Costo.isEmpty() || PrecioV.isEmpty()){
                    Toast.makeText(getActivity(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                }else if(ListaCategorias.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "Seleccione una categoría", Toast.LENGTH_SHORT).show();
                }else{
                    String itemText = (String) ListaCategorias.getSelectedItem();
                    String[] s =  itemText.split("-");
                    String idCat = s[0].trim();
                    try{
                        String query = "UPDATE PRODUCTO SET idCat="+idCat+",producto_name='"+Nombre+"',producto_stock="+Stock+",producto_price="+PrecioV+",producto_cost="+Costo+",producto_desc='"+Descrip+"' WHERE idProducto="+idPro;
                        PreparedStatement pst=cn.prepareStatement(query);
                        pst.executeUpdate();
                        Toast.makeText(getActivity(), "Registro actualizado con éxito", Toast.LENGTH_SHORT).show();
                    }catch (SQLException e){
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error al actualizar registro", Toast.LENGTH_SHORT).show();
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
        //-------llenar datos de acuerdo a la bd----------
        try{
            ConnectionClass connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();
            Statement st = conn.createStatement();

            idEmpresa = (int)getArguments().getInt("idEmpresa");
            ResultSet rs = st.executeQuery( "select p.producto_name,CONCAT(c.idCat, ' - ', c.cat_name),p.producto_stock,p.producto_cost,p.producto_price,p.producto_desc from producto p inner join categoria c on c.idCat = p.idCat where p.idEmpresa="+idEmpresa+" and p.idProducto="+idPro);
            while ( rs.next() ){
                Nombre=rs.getString(1);
                Categ=rs.getString(2);
                Stock=rs.getString(3);
                Costo=rs.getString(4);
                PrecioV=rs.getString(5);
                Descrip=rs.getString(6);
            }
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        NombreP.setText(Nombre);
        DescripP.setText(Descrip);
        CantP.setText(Stock);
        CostoP.setText(Costo);
        PrecioVentaP.setText(PrecioV);
        ListaCategorias.setSelection(obtenerPosicionItem(ListaCategorias, Categ));
    }

    public static int obtenerPosicionItem(Spinner spinner, String fruta) {
        int posicion = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(fruta)) {
                posicion = i;
            }
        }
        return posicion;
    }

}
