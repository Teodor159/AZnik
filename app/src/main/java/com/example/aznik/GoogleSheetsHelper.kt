package com.example.aznik

import android.content.Context
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.AppendValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.auth.oauth2.GoogleCredentials
import com.google.api.services.sheets.v4.SheetsScopes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.api.client.http.HttpRequestInitializer

import java.util.*

object GoogleSheetsHelper {
    suspend fun getSheetsInstance(context: Context): Sheets {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()

        // Load the credentials from the assets folder
        val credentials = context.assets.open("aznikgooglesheets.json").use { inputStream ->
            GoogleCredentials.fromStream(inputStream).createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS))
        }

        return Sheets.Builder(httpTransport, jsonFactory, HttpRequestInitializer { request ->
            credentials.initialize(request)
        }).setApplicationName("AZnik").build()
    }



    suspend fun sendDataToSheet(
        context: Context,
        spreadsheetId: String,
        range: String,
        values: List<List<Any>>
    ): AppendValuesResponse? {
        val sheetsService = getSheetsInstance(context)

        val requestBody = ValueRange().setValues(values)
        val request = sheetsService.spreadsheets().values().append(spreadsheetId, range, requestBody)

        request.valueInputOption = "RAW"

        return withContext(Dispatchers.IO) {
            request.execute()
        }
    }
}
