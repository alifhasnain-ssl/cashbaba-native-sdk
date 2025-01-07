package com.example.testaar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow

// client id: easy_merchant
// client secret: Abcd@1234
// gu id- 901fd5ff-f500-43fa-8afa-899297a0ca1c

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onFlutterClick: (MethodType) -> Unit,
    balance: MutableStateFlow<String>,
    receivedGuId: MutableStateFlow<String>,
    receivedTransactionId: MutableStateFlow<String>,
    isLoading: MutableStateFlow<Boolean>,
    onChangeMobileNumber: (String) -> Unit,
    onChangeNid: (String) -> Unit,
    onChangeGuId: (String) -> Unit,
    onChangeTransactionAmount: (String) -> Unit,
    onChangeClientId: (String) -> Unit,
    onChangeClientSecret: (String) -> Unit,

    ) {
    val balanceString by balance.collectAsState()
    val guIdString by receivedGuId.collectAsState()
    val transactionIdString by receivedTransactionId.collectAsState()
    val loading by isLoading.collectAsState()
    var mobileNumber by remember { mutableStateOf("") }
    var transactionAmount by remember { mutableStateOf("") }
    var nid by remember { mutableStateOf("") }
    var guId by remember { mutableStateOf("") }
    var clientId by remember { mutableStateOf("") }
    var clientSecret by remember { mutableStateOf("") }


    val hasCredential = clientId.trim().isNotEmpty() && clientSecret.trim().isNotEmpty()
    val isLinkButtonEnabled = hasCredential && nid.trim().isNotEmpty()
    val isTransactionButtonEnabled = hasCredential && transactionAmount.trim().isNotEmpty()

    val isRegistrationButtonEnabled = hasCredential && mobileNumber.trim().isNotEmpty()


    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TextField for Mobile Number
            TextField(
                value = mobileNumber,
                onValueChange = {
                    if (it.length <= 11 && it.all { char -> char.isDigit() }) {
                        mobileNumber = it
                        onChangeMobileNumber(mobileNumber)
                    }

                },
                label = { Text("Mobile Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            // TextField for NID
            TextField(
                value = nid,
                onValueChange = {
                    nid = it
                    onChangeNid(nid)
                },
                label = { Text("NID") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            TextField(
                value = guId,
                onValueChange = {
                    guId = it
                    onChangeGuId(guId)
                },
                label = { Text("GuId") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            // TextField for transactionAmount
            TextField(
                value = transactionAmount,
                onValueChange = {
                    transactionAmount = it
                    onChangeTransactionAmount(transactionAmount)
                },
                label = { Text("Transaction Amount") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            // TextField for Client Id
            TextField(
                value = clientId,
                onValueChange = {
                    clientId = it
                    onChangeClientId(clientId)
                },
                label = { Text("Client Id") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // TextField for Client Secret
            TextField(
                value = clientSecret,
                onValueChange = {
                    clientSecret = it
                    onChangeClientSecret(clientSecret)
                },
                label = { Text("Client Secret") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            if (guIdString.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "GuId: $guIdString")
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    clipboardManager.setText(AnnotatedString(guIdString))
                }) {
                    Text("Copy")
                }
            }

            // Buttons for actions
            Button(
                onClick = { onFlutterClick(MethodType.Registration) },
                enabled = isRegistrationButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = MethodType.Registration.toDisplayString())
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onFlutterClick(MethodType.Link) },
                enabled = isLinkButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = MethodType.Link.toDisplayString())
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onFlutterClick(MethodType.Balance) },
                    enabled = hasCredential,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = MethodType.Balance.toDisplayString())
                }
                if (balanceString.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Balance: $balanceString", modifier = Modifier.weight(1f)
                    )
                }
            }
            Button(
                onClick = { onFlutterClick(MethodType.Transaction) },
                enabled = isTransactionButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = MethodType.Transaction.toDisplayString())
            }

            if (transactionIdString.trim().isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = transactionIdString)
                Button(onClick = {
                    clipboardManager.setText(AnnotatedString(guIdString))
                }) {
                    Text("Copy")
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Loader UI
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
                    .clickable(enabled = false) {}, // Prevent interaction when loader is visible
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
