package me.nelsoncastro.pokeapi.Pojos

import android.os.Parcel
import android.os.Parcelable

data class Pokemon(val id: Int = 0,
                   val name: String = "N/A",
                   val fsttype: String= "N/A",
                   val sndtype: String= "N/A",
                   val weight: String= "N/A",
                   val height: String= "N/A",
                   val url:String= "N/A",
                   val sprite:String= "N/A"
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(fsttype)
        parcel.writeString(sndtype)
        parcel.writeString(weight)
        parcel.writeString(height)
        parcel.writeString(url)
        parcel.writeString(sprite)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }
}