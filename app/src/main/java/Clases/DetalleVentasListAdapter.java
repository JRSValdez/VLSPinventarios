package Clases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sv.edu.catolica.vlsp_inventarios.R;

public class DetalleVentasListAdapter extends ArrayAdapter<DetalleVenta> {

    private Context mContext;
    private List<DetalleVenta> detalleList = new ArrayList<>();

    public DetalleVentasListAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<DetalleVenta> list) {
        super(context, 0 , list);
        mContext = context;
        detalleList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.detalle_ventaslist_template, parent, false);

        DetalleVenta currentDetalle = detalleList.get(position);

        TextView pro = (TextView) listItem.findViewById(R.id.txtPro);
        pro.setText(String.valueOf(currentDetalle.producto));

        TextView cant = (TextView) listItem.findViewById(R.id.txtCantidad);
        cant.setText(String.valueOf(currentDetalle.cantidad));

        TextView sub = (TextView) listItem.findViewById(R.id.txtSub);
        sub.setText(String.valueOf(currentDetalle.subtotal));

        return listItem;
    }
}
