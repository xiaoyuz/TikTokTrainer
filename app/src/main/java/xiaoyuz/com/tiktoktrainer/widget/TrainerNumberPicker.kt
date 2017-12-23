package xiaoyuz.com.tiktoktrainer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker

class TrainerNumberPicker(context: Context, attributeSet: AttributeSet) : NumberPicker(context, attributeSet) {

    init {
        descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        wrapSelectorWheel = false
        setDividerHeight()
    }

    override fun addView(child: View?) {
        super.addView(child)
        updateView(child)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        updateView(child)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        updateView(child)
    }

    private fun updateView(view: View?) {
        if (view is EditText) {
            view.textSize = 18f
            view.paint.isFakeBoldText = true
        }
    }

    private fun setDividerHeight() {
        val pickerFields = NumberPicker::class.java.declaredFields
        pickerFields.forEach {
            if (it.name == "mSelectionDividerHeight") {
                it.isAccessible = true
                it.set(this, 1)
                return@forEach
            }
        }
    }
}