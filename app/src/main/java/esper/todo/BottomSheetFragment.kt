package esper.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var listener: InputListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_add.setOnClickListener {
            listener?.getInput(input.text.toString())
            this.dismiss()
        }
        input.requestFocus()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as InputListener
        } catch (castException: ClassCastException) {
            /** The activity does not implement the listener.  */
        }
    }
}