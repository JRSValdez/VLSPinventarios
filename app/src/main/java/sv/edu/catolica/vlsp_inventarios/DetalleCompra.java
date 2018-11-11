package sv.edu.catolica.vlsp_inventarios;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Clases.DetalleVentasListAdapter;

public class DetalleCompra extends Fragment {
    ConnectionClass con = new ConnectionClass();
    Connection cn = con.CONN();
    int idCompra;
    double total;

    TextView txtTotal, txtIdcompra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        idCompra = getArguments().getInt("idCompra");
        total = getArguments().getDouble("total");
        Log.e("idpro",".-.-.-.-.-.-.-.--.---.-.----.--.-.-."+String.valueOf(idCompra));
        return inflater.inflate(R.layout.fragment_detallecompra, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListView listView = getView().findViewById(R.id.detalleList);
        ArrayList<Clases.DetalleVenta> arrayList = new ArrayList<>();

        try
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();

            Statement st = conn.createStatement();

            String query = "SELECT P.producto_name, D.cantidad, D.subtotal FROM DET_COMPRA D " +
                    " INNER JOIN PRODUCTO P ON P.idProducto = D.idProducto " +
                    "WHERE D.idCompra = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, idCompra);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Clases.DetalleVenta obj = new Clases.DetalleVenta();
                obj.producto = (rs.getString(1));
                obj.cantidad = (rs.getFloat(2));
                obj.subtotal = (rs.getDouble(3));
                arrayList.add(obj);
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        DetalleVentasListAdapter arrayAdapter;
        arrayAdapter = new DetalleVentasListAdapter(getContext(), arrayList);
        listView.setAdapter(arrayAdapter);

        txtTotal = getView().findViewById(R.id.txtTotal);
        txtTotal.setText(String.valueOf(total));
        txtIdcompra = getView().findViewById(R.id.idCompra);
        txtIdcompra.setText("ID compra: "+ String.valueOf(idCompra));
    }

}
