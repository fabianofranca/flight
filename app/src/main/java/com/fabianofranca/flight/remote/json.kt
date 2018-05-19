package com.fabianofranca.flight.remote

import android.annotation.SuppressLint
import com.fabianofranca.flight.business.model.Flight
import com.fabianofranca.flight.business.model.RoundTrip
import com.google.gson.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.SimpleDateFormat

fun buildGsonConverter(): GsonConverterFactory {
    val builder = GsonBuilder()

    builder.registerTypeAdapter(
        RoundTrip::class.java,
        RoundTripDeserializer()
    )

    return GsonConverterFactory.create(builder.create())
}

class RoundTripDeserializer : JsonDeserializer<RoundTrip> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RoundTrip {

        if (json == null) {
            throw JsonParseException("Invalid flight data")
        }

        val outboundRaw = json.asJsonObject
        val inboundRaw = outboundRaw[RETURN_FL].asJsonArray[0].asJsonObject
        //TODO: Testar com números com pontos flutuantes
        val price = outboundRaw[FARE].asJsonObject[TOTAL_FARE].asNumber

        return RoundTrip(createFlight(outboundRaw), createFlight(inboundRaw), price)
    }

    private fun createFlight(raw: JsonObject): Flight {

        return Flight(
            date = formatDate(raw[DEPARTURE_TIME].asString),
            airline = formatAirline(raw[AIRLINE].asString),
            departureTime = formatTime(raw[DEPARTURE_TIME].asString),
            duration = formatDuration(raw[DURATION].asString),
            arrivalTime = formatTime(raw[ARRIVAL_TIME].asString),
            flightNumber = formatFlightNumber(raw[CARRIER_ID].asString, raw[FLIGHT_NO].asString),
            departure = formatIata(raw[ORIGIN].asString),
            hasStop = hasStop(raw[STOPS].asInt),
            destination = formatIata(raw[DESTINATION].asString)
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(strDate: String) =
        formatDateTime(strDate, DATE_FORMAT).toLowerCase().capitalize()

    private fun formatAirline(airline: String) = airline.split(" ").first().toUpperCase()

    private fun formatTime(strDate: String) = formatDateTime(strDate, TIME_FORMAT)

    private fun formatDuration(duration: String): String {
        val parts = duration.split(" ")
        val hours = parts[0].toUpperCase()
        val minutes = parts[1].replace("m", "")

        return "$hours${if (minutes != "0") minutes else ""}"
    }

    private fun formatFlightNumber(carrierId: String, flightNO: String) = "$carrierId-$flightNO"

    private fun formatIata(iata: String) = iata.toUpperCase()

    private fun hasStop(stops: Int) = stops > 0

    @SuppressLint("SimpleDateFormat")
    private fun formatDateTime(strDate: String, pattern: String): String {
        val rawFormatter = SimpleDateFormat(RAW_DATE_FORMAT)
        val date = rawFormatter.parse(strDate)
        val formatter = SimpleDateFormat(pattern)

        return formatter.format(date)
    }

    private companion object {
        const val RAW_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        const val DATE_FORMAT = "EEEE, dd 'de' MMMMM"
        const val TIME_FORMAT = "HH:mm"
        const val ORIGIN = "origin"
        const val DESTINATION = "destination"
        const val DEPARTURE_TIME = "DepartureTime"
        const val ARRIVAL_TIME = "ArrivalTime"
        const val FLIGHT_NO = "flightno"
        const val CARRIER_ID = "carrierid"
        const val AIRLINE = "airline"
        const val DURATION = "duration"
        const val STOPS = "stops"
        const val FARE = "fare"
        const val TOTAL_FARE = "totalfare"
        const val RETURN_FL = "returnfl"
    }
}