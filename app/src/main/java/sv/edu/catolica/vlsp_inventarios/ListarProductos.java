package sv.edu.catolica.vlsp_inventarios;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Clases.ClassListProductsItems;
import Clases.ProductListAdapter;


public class ListarProductos extends Fragment {

    int idEmpresa, tipoU;
    public int[] idPros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idEmpresa = getArguments().getInt("idEmpresa");
        tipoU = getArguments().getInt("tipoU");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listarproductos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListView listView = getView().findViewById(R.id.producList);
        ArrayList<ClassListProductsItems> arrayList = new ArrayList<>();

        try
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();

            Statement st = conn.createStatement();

            idEmpresa = (int)getArguments().getInt("idEmpresa");
            String query = "select p.idProducto, p.producto_name, p.producto_stock, c.cat_name from producto p " +
                    " inner join categoria c on c.idCat = p.idCat where p.idEmpresa = ? and eliminado = 0";

            PreparedStatement preparedStatement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, idEmpresa);
            ResultSet rs = preparedStatement.executeQuery();

            rs.last();
            int numRows = rs.getRow();
            rs.beforeFirst();

            if(numRows > 0) {
                int con = 0;
                this.idPros = new int[numRows];
                while (rs.next()) {
                    ClassListProductsItems obj = new ClassListProductsItems();
                    obj.id = (rs.getInt(1));
                    this.idPros[con] = obj.id;
                    obj.producto = (rs.getString(2));
                    obj.cantidad = (rs.getFloat(3));
                    obj.categoria = (rs.getString(4));
                    arrayList.add(obj);
                    con++;
                }
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        ProductListAdapter arrayAdapter;
        arrayAdapter = new ProductListAdapter(getContext(), arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(tipoU == 0){
                    int idPro = idPros[i];

                    EditarProducto nextFrag= new EditarProducto();

                    Bundle bundle=new Bundle();

                    bundle.putInt("idPro", idPro);
                    bundle.putInt("idEmpresa", idEmpresa);

                    nextFrag.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenedor, nextFrag).addToBackStack(null)
                            .commit();
                }
            }
        });

    }
}
