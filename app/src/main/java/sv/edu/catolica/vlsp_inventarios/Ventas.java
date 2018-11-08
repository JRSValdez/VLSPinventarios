package sv.edu.catolica.vlsp_inventarios;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Clases.Producto;
import Clases.ProductoModel;
import Clases.Venta;


public class Ventas extends Fragment {

    ConnectionClass ConexionDB = new ConnectionClass();
    Connection conn = ConexionDB.CONN();

    Spinner ProductList;
    Button btnOK, btnDetalle;
    EditText etCantidad;
    TextView tvnombre,tvprecio,tvdescrip;
    ArrayList<ProductoModel> productoInfo=new ArrayList<>();
    ArrayList<ProductoModel> lsProdVenta=new ArrayList<>();

    TableLayout tbProductos;
    public double total=0;
    TextView tvTotal;
    public Button btnAgg;

    public int IndSelected=-1;

    public List<String> Obt_Productos(){
        List<String> values = new ArrayList<>();
        try{
            String query="SELECT * FROM producto WHERE idEmpresa=1";
            Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                ProductoModel prod=new ProductoModel();
                values.add( rs.getString("producto_name"));
                prod.idProducto=rs.getInt("idProducto");
                prod.producto_name=rs.getString("producto_name");
                prod.producto_desc=rs.getString("producto_desc");
                prod.producto_price=rs.getDouble("producto_price");
                prod.producto_stock=rs.getInt("producto_stock");
                productoInfo.add(prod);
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
        return inflater.inflate(R.layout.fragment_ventas, container, false);
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //------------------------------------------------------------

        ProductList=(Spinner)getView().findViewById(R.id.spProductos);
        btnOK=(Button)getView().findViewById(R.id.btnOk);
        btnDetalle=(Button)getView().findViewById(R.id.btnDetalle);
        tvnombre=(TextView) getView().findViewById(R.id.tvnameP);
        tvdescrip=(TextView) getView().findViewById(R.id.tvdescP);
        tvprecio=(TextView) getView().findViewById(R.id.tvprecioP);
        etCantidad=(EditText) getView().findViewById(R.id.etCantidad);

        btnAgg=(Button) getView().findViewById(R.id.btnAggP);
        tbProductos=(TableLayout) getView().findViewById(R.id.tblDetalleVentas);
        tvTotal=(TextView) getView().findViewById(R.id.tvTotal);
        //------------------------------------------------------------
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         tbProductos.removeAllViews();
         int suma=0;
         boolean producoExistente=false;
         int pd=-1;
                String itemS= ProductList.getSelectedItem().toString();

         for (int j=0; j<lsProdVenta.size();j++) {
               if (itemS.equals(productoInfo.get(j).producto_name)) {
                        producoExistente=true;
                        suma=productoInfo.get(j).cant+Integer.parseInt(etCantidad.getText().toString());
                        pd=j;
                    }
                }
                if(producoExistente==true && pd>-1){
                    if(suma<productoInfo.get(pd).producto_stock){
                        lsProdVenta.get(pd).cant=suma;
                    }
         }
                    if (productoInfo.get(IndSelected).producto_stock > Integer.parseInt(etCantidad.getText().toString()) && etCantidad.getText().length() > 0) {

                        //Guardando registro seleccionado en la lista de productos a vender
                        if (producoExistente==false){
                        productoInfo.get(IndSelected).cant = Integer.parseInt(etCantidad.getText().toString());
                        lsProdVenta.add(productoInfo.get(IndSelected));
                        }
                    //jajaja
                    for (int x = 0; x < lsProdVenta.size(); x++) {
                        TableRow row = new TableRow(getContext());
                        int id = lsProdVenta.get(x).idProducto;
                        String nomb = lsProdVenta.get(x).producto_name;
                        String desc = lsProdVenta.get(x).producto_desc;
                        String price = Double.toString(lsProdVenta.get(x).producto_price);
                        int cant=0;
                        if(producoExistente==true && lsProdVenta.get(pd).producto_name.equals(lsProdVenta.get(x).producto_name)){
                        cant=suma;
                        }else{
                        cant = lsProdVenta.get(x).cant;
                        }
                        TextView tvid = new TextView(getContext());
                        tvid.setText("" + id);
                        TextView tvNomb = new TextView(getContext());
                        tvNomb.setText("" + nomb);
                        TextView tvPrice = new TextView(getContext());
                        tvPrice.setText(" $" + price);
                        TextView tvCant = new TextView(getContext());
                        tvCant.setText("" + cant);

                        row.addView(tvid);
                        row.addView(tvNomb);
                        row.addView(tvPrice);
                        row.addView(tvCant);
                        tbProductos.addView(row);

                        total = total + (lsProdVenta.get(x).producto_price * cant);
                    }
                    tvTotal.setText("Total de la venta: $" + total);
                    producoExistente=false;
                    total=0;
    Toast.makeText(getContext(), "Item agregado",Toast.LENGTH_SHORT).show();
    }else{
    Toast.makeText(getContext(), "Stock Insuficiente",Toast.LENGTH_SHORT).show();
}

//aqui va el fucking corchete
            }
        });

    btnAgg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),"venta realizada",Toast.LENGTH_SHORT).show();
        }
    });

    //jajaja


        btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if (lsProdVenta.size()>0){

             Intent intent = new Intent(getActivity(), VentasFin2.class);
    intent.putParcelableArrayListExtra("productos",  lsProdVenta);
             getActivity().startActivity(intent);




}else{

    Toast.makeText(getContext(), "No hay items aún",Toast.LENGTH_SHORT).show();
}
            }
        });


        ProductList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               String itemS= ProductList.getSelectedItem().toString();
                int nItems=productoInfo.size();

                for (int x=0; x<nItems;x++){
                    if(itemS.equals(productoInfo.get(x).producto_name)){
                        tvnombre.setText(productoInfo.get(x).producto_name);
                        tvdescrip.setText(productoInfo.get(x).producto_desc);
                        tvprecio.setText("$"+Double.toString(productoInfo.get(x).producto_price));
                        IndSelected=x;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        //------------------------------------------------------------
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,Obt_Productos());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ProductList.setAdapter(dataAdapter);
    }


}
