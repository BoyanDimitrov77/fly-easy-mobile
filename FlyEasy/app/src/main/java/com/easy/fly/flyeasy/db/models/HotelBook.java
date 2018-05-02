package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelBook implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public HotelBook createFromParcel(Parcel in) {
            return new HotelBook(in);
        }

        public HotelBook[] newArray(int size) {
            return new HotelBook[size];
        }
    };

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("hotelRoom")
    @Expose
    private HotelRoom hotelRoom;
    @SerializedName("booker")
    @Expose
    private User booker;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("status")
    @Expose
    private String status;

    public HotelBook(Parcel in) {

        this.id= in.readLong();
        this.hotelRoom = (HotelRoom) in.readParcelable(HotelRoom.class.getClassLoader());
        this.booker = (User) in.readParcelable(User.class.getClassLoader());
        this.payment = (Payment) in.readParcelable(Payment.class.getClassLoader());
        this.status = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HotelRoom getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(HotelRoom hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.id);
        dest.writeParcelable(this.hotelRoom,flags);
        dest.writeParcelable(this.booker,flags);
        dest.writeParcelable(this.payment,flags);
        dest.writeString(this.status);

    }
}
