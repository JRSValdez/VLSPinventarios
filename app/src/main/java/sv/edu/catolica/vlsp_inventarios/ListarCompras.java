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

import Clases.Compra;
import Clases.ComprasListAdapter;


public class ListarCompras extends Fragment {

    int idEmpresa;
    public int[] idCompras;
    public double[]  totales;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       idEmpresa = getArguments().getInt("idEmpresa");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listarcompras, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListView listView = getView().findViewById(R.id.comprasList);
        ArrayList<Compra> arrayList = new ArrayList<>();

        try
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();

            Statement st = conn.createStatement();

            idEmpresa = (int)getArguments().getInt("idEmpresa");
            String query = "select idCompra, compra_date, compra_total from compras where idEmpresa = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, idEmpresa);
            ResultSet rs = preparedStatement.executeQuery();

            rs.last();
            int numRows = rs.getRow();
            rs.beforeFirst();

            if(numRows > 0) {
                int con = 0;
                this.idCompras = new int[numRows];
                this.totales = new double[numRows];
                while (rs.next()) {
                    Compra obj = new Compra();
                    obj.idCompra = (rs.getInt(1));
                    this.idCompras[con] = obj.idCompra;
                    obj.fecha = (rs.getString(2));
                    obj.total = (rs.getDouble(3));
                    this.totales[con] = obj.total;
                    arrayList.add(obj);
                    con++;
                }
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        ComprasListAdapter arrayAdapter;
        arrayAdapter = new ComprasListAdapter(getContext(), arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int idCompra = idCompras[i];
                double total = totales[i];

                DetalleCompra nextFrag= new DetalleCompra();

                Bundle bundle=new Bundle();

                bundle.putInt("idCompra", idCompra);
                bundle.putDouble("total", total);

                nextFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                      .replace(R.id.contenedor, nextFrag).addToBackStack(null)
                    .commit();

            }
        });

    }
}
