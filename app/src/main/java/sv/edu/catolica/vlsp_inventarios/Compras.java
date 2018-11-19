package sv.edu.catolica.vlsp_inventarios;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Clases.ProductoModel;


public class Compras extends Fragment {

    ConnectionClass ConexionDB = new ConnectionClass();
  Connection conn = ConexionDB.CONN();

    Spinner ProductList;
    Button btnOK, btnEliminar, btnReinicio, btnTerminar;
    EditText etCantidad;
    TextView tvnombre,tvprecio,tvdescrip;
    ArrayList<ProductoModel> productoInfo=new ArrayList<>();
    ArrayList<ProductoModel> lsProdVenta=new ArrayList<>();
    int idEmpresa;
    TableLayout tbProductos;
    public double total=0;
    TextView tvTotal;
    public Button btnAgg;

    public int IndSelected=-1;

    public List<String> Obt_Productos(){
        List<String> values = new ArrayList<>();
        try{
            String query="SELECT * FROM producto WHERE producto_stock>=1 and idEmpresa="+idEmpresa + " and eliminado = 0";
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
        idEmpresa = getArguments().getInt("idEmpresa");
        return inflater.inflate(R.layout.fragment_compras, container, false);
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //------------------------------------------------------------

        ProductList=(Spinner)getView().findViewById(R.id.spProductos);
        btnOK=(Button)getView().findViewById(R.id.btnOk);
        btnEliminar=(Button)getView().findViewById(R.id.btnEliminar);
        btnReinicio=(Button)getView().findViewById(R.id.btnReiniciar);
        tvnombre=(TextView) getView().findViewById(R.id.tvnameP);
        tvdescrip=(TextView) getView().findViewById(R.id.tvdescP);
        tvprecio=(TextView) getView().findViewById(R.id.tvprecioP);
        etCantidad=(EditText) getView().findViewById(R.id.etCantidad);
        btnTerminar=(Button) getView().findViewById(R.id.btnAggP);
        btnAgg=(Button) getView().findViewById(R.id.btnAggP);
        tbProductos=(TableLayout) getView().findViewById(R.id.tblDetalleVentas);
        tvTotal=(TextView) getView().findViewById(R.id.tvTotal);
        //------------------------------------------------------------
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         int suma=0;
         boolean producoExistente=false;
         int pd=-1;
                String itemS= ProductList.getSelectedItem().toString();

         for (int j=0; j<lsProdVenta.size();j++) {
               if (itemS.equals(lsProdVenta.get(j).producto_name)) {
                        producoExistente=true;
                        suma=lsProdVenta.get(j).cant+Integer.parseInt(etCantidad.getText().toString());
                        pd=j;
                    }
                }
                int cantidad;
                try{
                    cantidad = Integer.parseInt(etCantidad.getText().toString());

                    if (Integer.parseInt(etCantidad.getText().toString())>0 && cantidad > 0) {

                        //Guardando registro seleccionado en la lista de productos a vender
                        if (producoExistente==false){
                        productoInfo.get(IndSelected).cant = Integer.parseInt(etCantidad.getText().toString());
                        lsProdVenta.add(productoInfo.get(IndSelected));
                        }
                    //jajaja
                        tbProductos.removeAllViews();
                    for (int x = 0; x < lsProdVenta.size(); x++) {
                        TableRow row = new TableRow(getContext());
                        int id = lsProdVenta.get(x).idProducto;
                        String nomb = lsProdVenta.get(x).producto_name;
                        String desc = lsProdVenta.get(x).producto_desc;
                        String price = Double.toString(lsProdVenta.get(x).producto_price);
                        int cant=0;

                        cant = lsProdVenta.get(x).cant;
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
                    tvTotal.setText("Total de la compra: $" + total);
                    producoExistente=false;
                    total=0;
    Toast.makeText(getContext(), "Item agregado",Toast.LENGTH_SHORT).show();
    }else{
    Toast.makeText(getContext(), "cantidad no válida",Toast.LENGTH_SHORT).show();
}
                }catch (NumberFormatException ex){
                    Toast.makeText(getContext(), "Cantidad Vacia", Toast.LENGTH_SHORT).show();
                }

            }
        });

    btnAgg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),"Compra realizada",Toast.LENGTH_SHORT).show();
        }
    });

    //jajaja


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if (lsProdVenta.size()>0){
  //  tbProductos.removeAllViews();
    int cant=Integer.parseInt(etCantidad.getText().toString());
    int pd=-1;
    String itemS= ProductList.getSelectedItem().toString();

    for (int j=0; j<lsProdVenta.size();j++) {
        if (itemS.equals(lsProdVenta.get(j).producto_name) ) {
            lsProdVenta.remove(j);
        }
    }

    //////////////////////////////////////
    tbProductos.removeAllViews();
    for (int x = 0; x < lsProdVenta.size(); x++) {

        TableRow row = new TableRow(getContext());
        int id = lsProdVenta.get(x).idProducto;
        String nomb = lsProdVenta.get(x).producto_name;
        String desc = lsProdVenta.get(x).producto_desc;
        String price = Double.toString(lsProdVenta.get(x).producto_price);
        int cantidad= lsProdVenta.get(x).cant;
        TextView tvid = new TextView(getContext());
        tvid.setText("" + id);
        TextView tvNomb = new TextView(getContext());
        tvNomb.setText("" + nomb);
        TextView tvPrice = new TextView(getContext());
        tvPrice.setText(" $" + price);
        TextView tvCant = new TextView(getContext());
        tvCant.setText("" + cantidad);

        row.addView(tvid);
        row.addView(tvNomb);
        row.addView(tvPrice);
        row.addView(tvCant);
        tbProductos.addView(row);

        total = total + (lsProdVenta.get(x).producto_price * cantidad);
    }
    tvTotal.setText("Total de la venta: $" + total);
    total=0;
    Toast.makeText(getContext(), "Item borrado",Toast.LENGTH_SHORT).show();
    /////////////////////////////////////

}else {
    Toast.makeText(getContext(),"lista vacia", Toast.LENGTH_SHORT).show();
}
            }
        });


        btnReinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lsProdVenta.size()>0){
                    lsProdVenta.removeAll(lsProdVenta);
                    tbProductos.removeAllViews();

                    total=0;
                    tvTotal.setText("Total de la venta: $" + total);
                    etCantidad.setText("1");
                    Toast.makeText(getContext(),"venta cancelada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"sin elementos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalV=0;
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                for (int x = 0; x < lsProdVenta.size(); x++) {
                   totalV=totalV+lsProdVenta.get(x).cant*lsProdVenta.get(x).producto_price;
                }
                int idv=-2;
                if(lsProdVenta.size()>0){
                    try{
                        String query = "Insert into Compras (idEmpresa,compra_date,compra_total) values ("+idEmpresa+",'"+date+"',"+totalV+")";
                        PreparedStatement pst=conn.prepareStatement(query);
                        pst.executeUpdate();

                        double subtotal=0;
                        idv =ConexionDB.ultimaCompra(1);
                        for (int x = 0; x < lsProdVenta.size(); x++) {
                            subtotal=lsProdVenta.get(x).cant*lsProdVenta.get(x).producto_price;
                            String query2 = "insert into det_compra (idEmpresa,idCompra, idProducto,cantidad, subtotal) values "+
                            "("+idEmpresa+","+idv+","+lsProdVenta.get(x).idProducto+","+lsProdVenta.get(x).cant+","+subtotal+")";
                            PreparedStatement pst2=conn.prepareStatement(query2);
                            pst2.executeUpdate();

                            // disminuyendo Stock

                            String query3="update PRODUCTO set producto_stock=producto_stock+"+lsProdVenta.get(x).cant+
                                    " where idProducto="+lsProdVenta.get(x).idProducto +" and idEmpresa="+idEmpresa;
                            PreparedStatement pst3=conn.prepareStatement(query3);
                            pst3.executeUpdate();
                        }
                        Toast.makeText(getActivity(), "Éxito", Toast.LENGTH_SHORT).show();

                        //LIMPIAR CONTROLES//////

                        lsProdVenta.removeAll(lsProdVenta);
                        tbProductos.removeAllViews();
                        total=0;
                        tvTotal.setText("Total de la venta: $" + total);
                        etCantidad.setText("1");

                        ////////////////////////



                    }catch (SQLException e){
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error al agregar registro "+idv, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(),"compra vacía", Toast.LENGTH_SHORT).show();
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
