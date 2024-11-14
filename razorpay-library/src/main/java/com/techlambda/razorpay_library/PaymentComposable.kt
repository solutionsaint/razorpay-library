package com.techlambda.razorpay_library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlambda.common.ui.CommonButton
import com.techlambda.common.ui.InputField
import com.techlambda.common.utils.showToast
import com.techlambda.razorpay_library.PaymentHelper.initiatePayment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentComposable() {
    var paidTo by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Scaffold(topBar = {
        Column {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 10.dp),
                title = {
                    Text(
                        "Payment Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
            )
        }
    }) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .imePadding()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputField(
                        label = "Name of Organisation",
                        value = paidTo
                    ) {
                        paidTo = it
                    }
                    InputField(
                        label = "Description",
                        value = description
                    ) {
                        description = it
                    }
                    InputField(
                        label = "Amount",
                        value = amount,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    ) {
                        amount = it
                    }
                    val context = LocalContext.current
                    CommonButton(text = "Pay") {
                        val validateMessage = validateForm(
                            paidTo = paidTo,
                            description = description,
                            amount = amount
                        )
                        if(validateMessage == "Validated") {
                            initiatePayment(
                                name = paidTo,
                                description = description,
                                amount = amount.toDouble() * 100,
                                context = context
                            )
                        } else {
                            context.showToast(validateMessage)
                        }
                    }
                }
            }
        }
    }
}

fun validateForm(paidTo: String, description: String, amount: String): String {
    return when {
        paidTo.isEmpty() -> {
            "Please enter name of organisation"
        }

        description.isEmpty() -> {
            "Please enter description"
        }

        amount.isEmpty() -> {
            "Please enter amount"
        }

        else -> "Validated"
    }
}
