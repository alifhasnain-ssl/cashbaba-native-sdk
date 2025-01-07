package com.example.testaar

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity(), BaseResponse {
    private val cashbabaMerchantSdk: BaseMerchantSdkUtils = CashBabaMerchantSdkUtils()

    private val balance: MutableStateFlow<String> = MutableStateFlow("")
    private val receivedGuId: MutableStateFlow<String> = MutableStateFlow("")
    private val receivedTransactionId: MutableStateFlow<String> = MutableStateFlow("")
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)


    private var clientId: String = ""
    private var clientSecret: String = ""
    private var transactionAmount: String = ""
    private var guId: String = ""
    private var mobileNumber: String = ""
    private var nidNumber: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cashbabaMerchantSdk.initFlutter(this)

        setContent {

            MainContent(
                onFlutterClick = { action ->
                    when (action) {
                        MethodType.Registration -> {
                            cashbabaMerchantSdk.register(
                                this, clientId, clientSecret, mobileNumber, this
                            )
                        }

                        MethodType.Link -> {
                            cashbabaMerchantSdk.linkWallet(
                                this, clientId, clientSecret, mobileNumber, nidNumber, this,
                            )
                        }

                        MethodType.Balance -> {
                            balance.value = ""
                            if (guId.isEmpty()) {
                                Toast.makeText(
                                    this, "Please link your wallet first", Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                isLoading.value = true
                                cashbabaMerchantSdk.getBalance(
                                    this, clientId, clientSecret, guId, this
                                )
                            }

                        }

                        MethodType.Transaction -> {
                            if (guId.isEmpty()) {
                                Toast.makeText(
                                    this, "Please link your wallet first", Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                cashbabaMerchantSdk.transaction(
                                    this, clientId, clientSecret, guId, transactionAmount, this
                                )
                            }

                        }

                        MethodType.Undefined -> {}
                    }
                },
                balance = balance,
                receivedGuId = receivedGuId,
                isLoading = isLoading,
                receivedTransactionId = receivedTransactionId,
                onChangeMobileNumber = { mobileNumber ->
                    this.mobileNumber = mobileNumber
                },
                onChangeNid = { nid ->
                    this.nidNumber = nid
                },
                onChangeClientId = { clientId ->
                    this.clientId = clientId
                },
                onChangeClientSecret = { clientSecret ->
                    this.clientSecret = clientSecret
                },

                onChangeTransactionAmount = { transactionAmount ->
                    this.transactionAmount = transactionAmount
                },
                onChangeGuId = { guId ->
                    this.guId = guId
                    Log.d("guId:::::", this.guId)
                },
            )
        }
    }

    override fun onCashBabaMerchantSdkSuccess(message: String, methodType: MethodType) {
        isLoading.value = false
        if (methodType == MethodType.Balance) {
            balance.value = message
        }

        if (methodType == MethodType.Link) {
            receivedGuId.value = message
            guId = message
        }

        if (methodType == MethodType.Transaction) {
            receivedTransactionId.value = message
        }

        if (methodType == MethodType.Registration) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }


        Log.d("response:: success", message)
    }

    override fun onCashBabaMerchantSdkError(message: String, methodType: MethodType) {
        isLoading.value = false
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}




