package sv.edu.catolica.vlsp_inventarios;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Clases.ClassListProductsItems;
import Clases.ProductListAdapter;
import Clases.Venta;
import Clases.VentasListAdapter;


public class ListarVentas extends Fragment {

    int idEmpresa;
    ListView listView;
    public int[] idVentas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       idEmpresa = getArguments().getInt("idEmpresa");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listarventas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListView listView = getView().findViewById(R.id.producList);
        ArrayList<Venta> arrayList = new ArrayList<>();

        try
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();

            Statement st = conn.createStatement();

            idEmpresa = (int)getArguments().getInt("idEmpresa");
            String query = "select idVenta, venta_date, venta_total from ventas where idEmpresa = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, idEmpresa);
            ResultSet rs = preparedStatement.executeQuery();

            rs.last();
            int numRows = rs.getRow();
            rs.beforeFirst();

            if(numRows > 0) {
                int con = 0;
                this.idVentas = new int[numRows];
                while (rs.next()) {
                    Venta obj = new Venta();
                    obj.idVenta = (rs.getInt(1));
                    this.idVentas[con] = obj.idVenta;
                    obj.fecha = (rs.getString(2));
                    obj.total = (rs.getDouble(3));
                    arrayList.add(obj);
                    con++;
                }
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        VentasListAdapter arrayAdapter;
        arrayAdapter = new VentasListAdapter(getContext(), arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int idVenta = idVentas[i];

                ListarVentas nextFrag= new ListarVentas();

                Bundle bundle=new Bundle();

                bundle.putInt("idVenta", idVenta);
                bundle.putInt("idEmpresa", idEmpresa);

                nextFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                      .replace(R.id.contenedor, nextFrag)
                    .commit();

            }
        });

    }
}
