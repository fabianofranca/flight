package com.fabianofranca.flight.remote

import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.tools.BaseTest
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RoundTripDeserializerTest : BaseTest() {

    @Test
    fun deserialize_shouldDeserializeAndFormatData() {
        val parser = JsonParser()
        val element: JsonElement = parser.parse(stringFileContent(FLIGHT_JSON)).asJsonObject
        val deserializer = RoundTripDeserializer()

        val roundTrip = deserializer.deserialize(element, RoundTrip::class.java, null)

        assertNotNull(roundTrip.outboundFlight)

        assertNotNull(roundTrip.inboundFlight)

        assertEquals(roundTrip.outboundFlight.date, "Sábado, 08 de dezembro")
        assertEquals(roundTrip.outboundFlight.airline, "GOL")
        assertEquals(roundTrip.outboundFlight.departureTime, "09:20")
        assertEquals(roundTrip.outboundFlight.duration, "1H")
        assertEquals(roundTrip.outboundFlight.arrivalTime, "10:20")
        assertEquals(roundTrip.outboundFlight.flightNumber, "G3-3025")
        assertEquals(roundTrip.outboundFlight.departure, "CNF")
        assertFalse(roundTrip.outboundFlight.hasStop)
        assertEquals(roundTrip.outboundFlight.destination, "SDU")

        assertEquals(roundTrip.inboundFlight.date, "Domingo, 09 de dezembro")
        assertEquals(roundTrip.inboundFlight.airline, "GOL")
        assertEquals(roundTrip.inboundFlight.departureTime, "20:10")
        assertEquals(roundTrip.inboundFlight.duration, "2H25")
        assertEquals(roundTrip.inboundFlight.arrivalTime, "22:35")
        assertEquals(roundTrip.inboundFlight.flightNumber, "G3-4793")
        assertEquals(roundTrip.inboundFlight.departure, "GIG")
        assertTrue(roundTrip.inboundFlight.hasStop)
        assertEquals(roundTrip.inboundFlight.destination, "CNF")
    }

    private companion object {
        const val FLIGHT_JSON = "flight.json"
    }
}