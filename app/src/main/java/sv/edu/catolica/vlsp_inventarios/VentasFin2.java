package sv.edu.catolica.vlsp_inventarios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Clases.ProductoModel;

import static java.security.AccessController.getContext;

public class VentasFin2 extends AppCompatActivity {

    TableLayout tbProductos;
    public double total=0;
    TextView tvTotal;
   public Button btnAgg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas_fin2);

        btnAgg=(Button) findViewById(R.id.btnAggP);
        tbProductos=(TableLayout) findViewById(R.id.tblDetalleVentas);
        tvTotal=(TextView) findViewById(R.id.tvTotal);
        ArrayList<ProductoModel> lsVenta = (ArrayList<ProductoModel>) getIntent().getSerializableExtra("productos");


        for(int x=0; x<lsVenta.size();x++){
            TableRow row=new TableRow(this);
            int id = lsVenta.get(x).idProducto;
            String nomb =lsVenta.get(x).producto_name;
            String desc =lsVenta.get(x).producto_desc;
            String price =Double.toString(lsVenta.get(x).producto_price);
            int cant =lsVenta.get(x).cant;

            TextView tvid=new TextView(this);
            tvid.setText(""+id);
            TextView tvNomb=new TextView(this);
            tvNomb.setText(""+nomb);
            TextView tvPrice=new TextView(this);
            tvPrice.setText(" $"+price);
            TextView tvCant=new TextView(this);
            tvCant.setText(""+cant);

            row.addView(tvid);
            row.addView(tvNomb);
            row.addView(tvPrice);
            row.addView(tvCant);
            tbProductos.addView(row);

            total=total+(lsVenta.get(x).producto_price*cant);
        }
tvTotal.setText("Total de la venta: $"+total);

        btnAgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VentasFin2.this,"venta realizada",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
