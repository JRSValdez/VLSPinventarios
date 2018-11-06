package sv.edu.catolica.vlsp_inventarios;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Clases.ClassListProductsItems;
import Clases.ProductListAdapter;


public class ListarProductos extends Fragment {

    int idEmpresa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       idEmpresa = getArguments().getInt("idEmpresa");

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

            ResultSet rs = st.executeQuery( "select p.idProducto, p.producto_name, p.producto_stock, c.cat_name from producto p " +
                    " inner join categoria c on c.idCat = p.idCat where p.idEmpresa = "+ idEmpresa );

            while ( rs.next() )
            {
                ClassListProductsItems obj = new ClassListProductsItems();
                obj.id = ( rs.getInt( 1 ) );
                obj.producto  =  ( rs.getString( 2 ) );
                obj.cantidad = ( rs.getFloat( 3 ) );
                obj.categoria  =  ( rs.getString( 4 ) );
                arrayList.add( obj );
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        ProductListAdapter arrayAdapter;
        arrayAdapter = new ProductListAdapter(getContext(), arrayList);
        listView.setAdapter(arrayAdapter);
    }


}
