package com.easy.fly.flyeasy.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassengerTicket implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PassengerTicket createFromParcel(Parcel in) {
            return new PassengerTicket(in);
        }

        public PassengerTicket[] newArray(int size) {
            return new PassengerTicket[size];
        }
    };


    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("passengerName")
    @Expose
    private String passengerName;
    @SerializedName("identificationNumber")
    @Expose
    private String identificationNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber;
    @SerializedName("boardSeatNumber")
    @Expose
    private String boardSeatNumber;

    public PassengerTicket(Parcel in) {

        this.id = in.readLong();
        this.passengerName =in.readString();
        this.identificationNumber = in.readString();
        this.email = in.readString();
        this.ticketNumber = in.readString();
        this.boardSeatNumber = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getBoardSeatNumber() {
        return boardSeatNumber;
    }

    public void setBoardSeatNumber(String boardSeatNumber) {
        this.boardSeatNumber = boardSeatNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.id);
        dest.writeString(this.passengerName);
        dest.writeString(this.identificationNumber);
        dest.writeString(this.email);
        dest.writeString(this.ticketNumber);
        dest.writeString(this.boardSeatNumber);

    }
}
