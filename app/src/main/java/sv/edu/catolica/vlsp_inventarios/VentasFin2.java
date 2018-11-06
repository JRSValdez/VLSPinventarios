package sv.edu.catolica.vlsp_inventarios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

import Clases.ProductoModel;

public class VentasFin2 extends AppCompatActivity {

    TableLayout tbProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas_fin2);


        ArrayList<ProductoModel> lsVenta = (ArrayList<ProductoModel>) getIntent().getSerializableExtra("productos");

        String xd=lsVenta.get(0).producto_name.toString();

        Toast.makeText(this, xd,Toast.LENGTH_SHORT).show();

    }


}
