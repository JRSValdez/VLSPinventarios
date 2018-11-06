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

public class ProductListAdapter extends ArrayAdapter<ClassListProductsItems> {

    private Context mContext;
    private List<ClassListProductsItems> productList = new ArrayList<>();

    public ProductListAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ClassListProductsItems> list) {
        super(context, 0 , list);
        mContext = context;
        productList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.productlist_template, parent, false);

        ClassListProductsItems currentPro = productList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.txtName);
        name.setText(currentPro.producto);

        TextView cantidad = (TextView) listItem.findViewById(R.id.txtCant);
        cantidad.setText(String.valueOf(currentPro.cantidad));

        TextView cat = (TextView) listItem.findViewById(R.id.txtCat);
        cat.setText(currentPro.categoria);

        return listItem;
    }
}
