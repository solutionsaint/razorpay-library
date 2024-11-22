package com.techlambda.razorpaylibrary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.techlambda.razorpay_library.PaymentComposable
import com.techlambda.razorpay_library.PaymentHelper
import com.techlambda.razorpaylibrary.ui.theme.RazorpayLibraryTheme

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RazorpayLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PaymentHelper.initialize(LocalContext.current)
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "$p1", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var showPaymentComposable by remember { mutableStateOf(false) }
    if(showPaymentComposable) {
        PaymentComposable(amountParam = "2000")
    }else {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = {
            showPaymentComposable = true
        }) {
            Text(text = "Pay")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RazorpayLibraryTheme {
        Greeting("Android")
    }
}