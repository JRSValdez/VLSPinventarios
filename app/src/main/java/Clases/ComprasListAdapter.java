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

public class ComprasListAdapter extends ArrayAdapter<Compra> {

    private Context mContext;
    private List<Compra> ventasList = new ArrayList<>();

    public ComprasListAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Compra> list) {
        super(context, 0 , list);
        mContext = context;
        ventasList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.ventaslist_template, parent, false);

        Compra cerrentVenta = ventasList.get(position);

        TextView id = (TextView) listItem.findViewById(R.id.txtId);
        id.setText(String.valueOf(cerrentVenta.idCompra));

        TextView fecha = (TextView) listItem.findViewById(R.id.txtFecha);
        fecha.setText("Fecha: " + cerrentVenta.fecha);

        TextView total = (TextView) listItem.findViewById(R.id.txtTotal);
        total.setText(String.valueOf(cerrentVenta.total));

        return listItem;
    }
}
