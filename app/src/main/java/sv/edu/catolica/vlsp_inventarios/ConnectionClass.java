package sv.edu.catolica.vlsp_inventarios;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Clases.ProductoModel;
import Clases.Usuario;

public class ConnectionClass {
    String ip = "vlspinventarios.mssql.somee.com";
    String db = "vlspinventarios";
    String un = "jrsvaldez_SQLLogin_1";
    String password = "t5e6mxhavv";

    Connection conn = null;

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ConnURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + ip + "/" + db+ ";user=" + un + ";password=" + password + ";";

            conn = DriverManager.getConnection(ConnURL);

        }catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }

        return conn;
    }

    public Usuario iniciar_sesion(Usuario _user) throws SQLException {
        String query = "SELECT EMPRESA.idEmpresa, user_user, user_password, CONCAT(user_name, ' ', user_lname), empresa_name FROM USUARIO " +
                " INNER JOIN EMPRESA ON EMPRESA.idEmpresa = USUARIO.idEmpresa WHERE user_user = ? AND user_password = ?";
        PreparedStatement st = null;
        st = this.conn.prepareStatement(query); //ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY
        st.setString(1,_user.usario);
        st.setString(2,_user.pass);
        ResultSet rs = st.executeQuery();

        String user = "";
        String contra = "";
        while(rs.next()){
            _user.idEmpresa = rs.getInt(1);
            user = rs.getString(2);
            contra = rs.getString(3);
            _user.nombre = rs.getString(4);
            _user.empresa = rs.getString(5);
        }

        if(user.equals(_user.usario) && contra.equals(_user.pass)){
            return _user;
        } else return _user;
    }

    public int ultimaVenta(int idempresa){
        int lastID=-1;
        try{
            String query="select top 1 idVenta from ventas where idempresa ="+idempresa+" order by idVenta desc";
            Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                lastID=rs.getInt("idVenta");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lastID;
    }



}