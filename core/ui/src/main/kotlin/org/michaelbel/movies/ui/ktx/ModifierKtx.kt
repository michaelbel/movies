package org.michaelbel.movies.ui.ktx

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableWithoutRipple(
    block: () -> Unit
): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = { block() }
    )
}

/*fun Modifier.autofill(
    autofillTypes: List<AutofillType>,
    onFill: (String) -> Unit,
): Modifier = composed {
    val autofill: Autofill? = LocalAutofill.current
    val autofillNode = AutofillNode(
        onFill = onFill,
        autofillTypes = autofillTypes
    )
    LocalAutofillTree.current += autofillNode

    onGloballyPositioned {
        autofillNode.boundingBox = it.boundsInWindow()
    }.onFocusChanged { focusState ->
        autofill?.run {
            if (focusState.isFocused) {
                requestAutofillForNode(autofillNode)
            } else {
                cancelAutofillForNode(autofillNode)
            }
        }
    }
}*/