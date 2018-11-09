package sv.edu.catolica.vlsp_inventarios;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Dashboard extends Fragment {

    Button btnAbrirCompras;
    int idEmpresa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        idEmpresa = getArguments().getInt("idEmpresa");
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnAbrirCompras = getView().findViewById(R.id.btnPr);
        btnAbrirCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AgregarProductos nextFrag= new AgregarProductos();

                Bundle bundle=new Bundle();

                bundle.putInt("idEmpresa", idEmpresa);

                nextFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, nextFrag)
                        .commit();

            }
        });
    }
}
