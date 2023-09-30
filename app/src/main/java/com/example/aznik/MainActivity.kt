package com.example.aznik

import com.google.android.gms.common.api.Scope
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import android.app.Activity
import com.google.api.client.http.javanet.NetHttpTransport








class MainActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val TAG = "MainActivity"

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInButton: SignInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener { signIn() }

        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun readSheetData(sheetsService: Sheets, spreadsheetId: String, range: String) {
        try {
            val result = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute()
            val numRows = result.getValues()?.size ?: 0
            Log.d(TAG, "Rows: $numRows")
            Log.d(TAG, "Data: ${result.getValues()}")

        } catch (e: Exception) {
            Log.e(TAG, "Error reading data from sheet", e)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, proceed with API interactions.

            // Create the Sheets API service object using the account object
            val transport = NetHttpTransport()
            val jsonFactory = JacksonFactory.getDefaultInstance()

            // Create a credential object using GoogleAccountCredential
            val credential = GoogleAccountCredential.usingOAuth2(this, listOf(SheetsScopes.SPREADSHEETS))
            credential.selectedAccount = account?.account

            val sheetsService = Sheets.Builder(transport, jsonFactory, credential)
                .setApplicationName("YourAppName")
                .build()

            // Now, you can use the sheetsService object to interact with the Google Sheets API
            val spreadsheetId = "YOUR_SPREADSHEET_ID"
            val range = "Sheet1!A1:B10"
            readSheetData(sheetsService, spreadsheetId, range)

            // Start MenuActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)

        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            // Handle sign-in failure.
        }
    }

}

