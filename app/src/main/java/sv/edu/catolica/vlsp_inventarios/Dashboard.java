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

    Button btnPr, btnVentas, btnListarProd, btnListarVenta, btnListarHistorialVen, btnComp, btnAggUser, btnConfig, btnSalir;
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

        btnPr = getView().findViewById(R.id.btnPr);
        btnPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AgregarProductos nextFrag= new AgregarProductos();

                Bundle bundle=new Bundle();

                bundle.putInt("idEmpresa", idEmpresa);

                nextFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, nextFrag).addToBackStack(null)
                        .commit();

            }
        });

        btnListarProd = getView().findViewById(R.id.btnListarProd);
        btnListarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListarProductos nextFrag= new ListarProductos();

                Bundle bundle=new Bundle();

                bundle.putInt("idEmpresa", idEmpresa);

                nextFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, nextFrag).addToBackStack(null)
                        .commit();

            }
        });

        
        btnListarVenta = getView().findViewById(R.id.btnVenta);
        btnListarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ventas nextFrag= new Ventas();

                Bundle bundle=new Bundle();

                bundle.putInt("idEmpresa", idEmpresa);

                nextFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, nextFrag).addToBackStack(null)
                        .commit();

            }
        });

        btnListarHistorialVen = getView().findViewById(R.id.btnHistorialVentas);
        btnListarHistorialVen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListarVentas nextFrag= new ListarVentas();

                Bundle bundle=new Bundle();

                bundle.putInt("idEmpresa", idEmpresa);

                nextFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, nextFrag).addToBackStack(null)
                        .commit();

            }
        });


        btnComp = getView().findViewById(R.id.btnCompra);
        btnComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Compras nextFrag= new Compras();

                Bundle bundle=new Bundle();

                bundle.putInt("idEmpresa", idEmpresa);

                nextFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, nextFrag).addToBackStack(null)
                        .commit();

            }
        });


        //btnAggUser = getView().findViewById(R.id.btnAggUser);
        //btnAggUser.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View view) {

               // NewUser nextFrag= new NewUser();

                //Bundle bundle=new Bundle();

                //bundle.putInt("idEmpresa", idEmpresa);

                //nextFrag.setArguments(bundle);

                //getActivity().getSupportFragmentManager().beginTransaction()
                        //.replace(R.id.contenedor, nextFrag)
                        //.commit();

            //}
        //});

       // btnConfig = getView().findViewById(R.id.btnConfiguracion);
        //btnConfig.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View view) {

               // RegistrarEmpresa nextFrag= new RegistrarEmpresa();

               // Bundle bundle=new Bundle();

                //bundle.putInt("idEmpresa", idEmpresa);

                //nextFrag.setArguments(bundle);

                //getActivity().getSupportFragmentManager().beginTransaction()
                        //.replace(R.id.contenedor, nextFrag)
                        //.commit();

            //}
        //});





    }
}
