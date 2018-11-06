package Clases;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductoModel implements Parcelable {

public int idProducto;
public String producto_name;
public int producto_stock;
public double producto_price;
public String producto_desc;

public  ProductoModel(){}

    public static final Parcelable.Creator<ProductoModel> CREATOR =
            new Parcelable.Creator<ProductoModel>()
            {
                @Override
                public ProductoModel createFromParcel(Parcel parcel)
                {
                    return new ProductoModel(parcel);
                }

                @Override
                public ProductoModel[] newArray(int size)
                {
                    return new ProductoModel[size];
                }
            };



    public ProductoModel(Parcel parcel)
    {
        //seguir el mismo orden que el usado en el método writeToParcel
        this.idProducto = parcel.readInt();
        this.producto_name = parcel.readString();
        this.producto_stock = parcel.readInt();
        this.producto_price =parcel.readDouble();
        this.producto_desc=parcel.readString();
    }
    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeInt( this.idProducto);
        parcel.writeString(this.producto_name);
        parcel.writeInt(this.producto_stock);
        parcel.writeDouble(this.producto_price);
        parcel.writeString(this.producto_desc);
    }


    @Override
    public int describeContents()
    {
        return 0;
    }
}







