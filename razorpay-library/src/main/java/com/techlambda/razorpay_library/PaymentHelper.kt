package com.techlambda.razorpay_library

import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.razorpay.Checkout
import org.json.JSONObject

object PaymentHelper {

    fun initialize(context: Context) {
        Checkout.preload(context)
        val co = Checkout()
        co.setKeyID(BuildConfig.API_KEY)
    }

    fun initiatePayment(
        name: String,
        description: String,
        amount: Double,
        context: Context
    ) {
        val activity: Activity = context as Activity
        val co = Checkout()
        try {
            val options = JSONObject()
            options.put("name", name)
            options.put("description", description)
            options.put("image", "http://example.com/image/rzp.jpg")
            options.put("currency", "INR")
            options.put("amount", amount)

            val retryObj = JSONObject()
            retryObj.put("enabled", false)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email", null)
            prefill.put("contact", null)

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}