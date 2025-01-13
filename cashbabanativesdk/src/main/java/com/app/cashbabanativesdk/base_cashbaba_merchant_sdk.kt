package com.app.cashbabanativesdk

import android.app.Application
import android.content.Context
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterMain.ensureInitializationComplete
import io.flutter.view.FlutterMain.startInitialization

abstract class BaseMerchantSdkUtils {
    abstract fun getBalance(
        context: Context,
        clientId: String,
        clientSecret: String,
        guId: String,
        response: BaseResponse
    )

    abstract fun linkWallet(
        context: Context,
        clientId: String,
        clientSecret: String,
        mobileNumber: String,
        nidNumber: String,
        response: BaseResponse
    )

    abstract fun register(
        context: Context,
        clientId: String,
        clientSecret: String,
        mobileNumber: String,
        response: BaseResponse
    )

    abstract fun transaction(
        context: Context,
        clientId: String,
        clientSecret: String,
        guId: String,
        transactionAmount: String,
        response: BaseResponse
    )

    abstract fun initFlutter(context: Context)
}

interface BaseResponse {
    fun onCashBabaMerchantSdkSuccess(message: String, methodType: MethodType)
    fun onCashBabaMerchantSdkError(message: String, methodType: MethodType)
}


class CashBabaMerchantSdkUtils : BaseMerchantSdkUtils() {

    private val CHANNEL = "cashbaba_merchant_sdk_method_channel"
    private val flutterEngineId = "123456789"
    private var clientId = ""
    private var clientSecret = ""

    private var initializeSdkMethodName = "initializeSdk"

    private val methodTypeBalance: MethodType = MethodType.Balance
    private val methodTypeLink: MethodType = MethodType.Link
    private val methodTypeRegistration: MethodType = MethodType.Registration
    private val methodTypeTransaction: MethodType = MethodType.Transaction

    private var flutterEngine: FlutterEngine? = null


    override fun getBalance(
        context: Context,
        clientId: String,
        clientSecret: String,
        guId: String,
        response: BaseResponse
    ) {
        this.clientId = clientId
        this.clientSecret = clientSecret
        FlutterEngineCache.getInstance().remove(flutterEngineId)
        initializeFlutterEngine(context.applicationContext as Application, response)

//        val intent = FlutterActivity.withCachedEngine(flutterEngineId).build(context)
//        context.startActivity(intent)

        flutterEngine?.let { engine ->
            val channel = MethodChannel(engine.dartExecutor, CHANNEL)
            channel.invokeMethod(
                initializeSdkMethodName,
                MethodDetails(
                    methodName = methodTypeBalance.toDisplayString(),
                    clientId = clientId,
                    clientSecret = clientSecret,
                    guId = guId,
                    mobileNumber = "",
                    nidNumber = "",
                    transactionAmount = "",
                ).toJson()
            )
        }
    }

    override fun initFlutter(context: Context) {
        startInitialization(context)
        ensureInitializationComplete(context, null)
    }


    override fun linkWallet(
        context: Context,
        clientId: String,
        clientSecret: String,
        mobileNumber: String,
        nidNumber: String,
        response: BaseResponse
    ) {
        this.clientId = clientId
        this.clientSecret = clientSecret
        FlutterEngineCache.getInstance().remove(flutterEngineId)
        initializeFlutterEngine(context.applicationContext as Application, response)

        val intent = FlutterActivity.withCachedEngine(flutterEngineId).build(context)
        context.startActivity(intent)

        flutterEngine?.let { engine ->
            val channel = MethodChannel(engine.dartExecutor, CHANNEL)
            channel.invokeMethod(
                initializeSdkMethodName,
                MethodDetails(
                    methodName = methodTypeLink.toDisplayString(),
                    clientId = clientId,
                    clientSecret = clientSecret,
                    guId = "",
                    mobileNumber = mobileNumber,
                    nidNumber = nidNumber,
                    transactionAmount = "",
                ).toJson()
            )
        }
    }

    override fun register(
        context: Context,
        clientId: String,
        clientSecret: String,
        mobileNumber: String,
        response: BaseResponse
    ) {
        this.clientId = clientId
        this.clientSecret = clientSecret
        FlutterEngineCache.getInstance().remove(flutterEngineId)
        initializeFlutterEngine(context.applicationContext as Application, response)

        val intent = FlutterActivity.withCachedEngine(flutterEngineId).build(context)
        context.startActivity(intent)

        flutterEngine?.let { engine ->
            val channel = MethodChannel(engine.dartExecutor, CHANNEL)
            channel.invokeMethod(
                initializeSdkMethodName,
                MethodDetails(
                    methodName = methodTypeRegistration.toDisplayString(),
                    clientId = clientId,
                    clientSecret = clientSecret,
                    guId = "",
                    mobileNumber = mobileNumber,
                    nidNumber = "",
                    transactionAmount = "",
                ).toJson()
            )
        }
    }

    override fun transaction(
        context: Context,
        clientId: String,
        clientSecret: String,
        guId: String,
        transactionAmount: String,
        response: BaseResponse
    ) {
        this.clientId = clientId
        this.clientSecret = clientSecret
        FlutterEngineCache.getInstance().remove(flutterEngineId)
        initializeFlutterEngine(context.applicationContext as Application, response)

        val intent = FlutterActivity.withCachedEngine(flutterEngineId).build(context)
        context.startActivity(intent)

        flutterEngine?.let { engine ->
            val channel = MethodChannel(engine.dartExecutor, CHANNEL)
            channel.invokeMethod(
                initializeSdkMethodName,
                MethodDetails(
                    methodName = methodTypeTransaction.toDisplayString(),
                    clientId = clientId,
                    clientSecret = clientSecret,
                    guId = guId,
                    mobileNumber = "",
                    nidNumber = "",
                    transactionAmount = transactionAmount,
                ).toJson()
            )
        }
    }

    private fun initializeFlutterEngine(context: Context, response: BaseResponse) {
        flutterEngine = FlutterEngine(context).apply {
            dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )
            MethodChannel(
                dartExecutor.binaryMessenger,
                CHANNEL
            ).setMethodCallHandler { call, result ->
                onMethodCall(call, result, response)
            }
        }
        FlutterEngineCache.getInstance().put(flutterEngineId, flutterEngine)
    }

    private fun onMethodCall(
        call: MethodCall,
        result: MethodChannel.Result,
        response: BaseResponse,
    ) {
        when (call.method) {
            "sendBalance" -> {
                val balance = call.argument<String>("balance")
                //walletBalance.value = "BDT $balance"
                response.onCashBabaMerchantSdkSuccess(balance.toString(), MethodType.Balance)
                result.success("Received on Android")
            }

            "resultError" -> {
                val error = call.argument<String>("error")
                response.onCashBabaMerchantSdkError(error.toString(), MethodType.Undefined)
                result.success("Received on Android")

            }

            "sendGuId" -> {
                val guId = call.argument<String>("guId")
                //walletBalance.value = "BDT $balance"
                response.onCashBabaMerchantSdkSuccess(guId.toString(), MethodType.Link)
                result.success("Received on Android")
            }

            "registrationSuccess" -> {
                val guId = call.argument<String>("registrationSuccess")
                //walletBalance.value = "BDT $balance"
                response.onCashBabaMerchantSdkSuccess(guId.toString(), MethodType.Registration)
                result.success("Received on Android")
            }

            "transactionSuccess" -> {
                val transactionId = call.argument<String>("transactionId")
                //walletBalance.value = "BDT $balance"
                response.onCashBabaMerchantSdkSuccess(
                    transactionId.toString(),
                    MethodType.Transaction
                )
                result.success("Received on Android")
            }

            else -> {
                result.notImplemented()
            }
        }
    }
}

enum class MethodType {
    Registration, Link, Balance, Transaction, Undefined,
}

fun MethodType.toDisplayString(): String {
    return when (this) {
        MethodType.Registration -> "Registration"
        MethodType.Link -> "Link"
        MethodType.Balance -> "Balance"
        MethodType.Transaction -> "Transaction"
        MethodType.Undefined -> ""
    }
}


data class MethodDetails(
    val methodName: String,
    val clientId: String,
    val clientSecret: String,
    val guId: String,
    val mobileNumber: String,
    val nidNumber: String,
    val transactionAmount: String,
) {
    fun toJson(): Map<String, String> {
        return mapOf(
            "methodName" to methodName,
            "clientId" to clientId,
            "clientSecret" to clientSecret,
            "guId" to guId,
            "mobileNumber" to mobileNumber,
            "nidNumber" to nidNumber,
            "amount" to transactionAmount,
        )
    }
}