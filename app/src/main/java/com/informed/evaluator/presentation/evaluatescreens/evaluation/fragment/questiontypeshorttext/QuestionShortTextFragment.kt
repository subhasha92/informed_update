package com.informed.evaluator.presentation.evaluatescreens.evaluation.fragment.questiontypeshorttext

import android.content.ContentValues
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.informed.evaluator.R
import com.informed.evaluator.presentation.evaluatescreens.evaluation.view.EvaluationActivity
import com.informed.evaluator.presentation.evaluatescreens.evaluation.wrapper.SpeechListnerWrapper
import com.informed.evaluator.utils.SpeechRecognizeClass
import com.informed.evaluator.utils.showToast
import kotlinx.android.synthetic.main.fragment_evaluate_welcome.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class QuestionShortTextFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var dicatationEnable=false

    private lateinit var textContainer:TextInputLayout
    private lateinit var micButton:ImageButton
    private lateinit var  dicationText:TextView
    private lateinit var  speech:SpeechRecognizeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_question_short_text, container, false)
speech= SpeechRecognizeClass(requireContext())
        val backButton = view.findViewById(R.id.btn_back) as MaterialButton
        val nextButton = view.findViewById(R.id.btn_next) as MaterialButton
         micButton = view.findViewById(R.id.mic_btn) as ImageButton
         dicationText = view.findViewById(R.id.dictatio_text) as TextView
        textContainer = view.findViewById(R.id.textContainer) as TextInputLayout

        setupMic()

        backButton.setOnClickListener {
            (activity as EvaluationActivity?)?.backScreen()
        }
        nextButton.setOnClickListener {
            (activity as EvaluationActivity?)?.changeScreen()
        }

    return view
    }

    private fun setupMic() {

        micButton.setOnClickListener {
            if (speech.checkPermission()) {
                if (speech.isRecognitionAvailable()){
                    dicatationEnable = !dicatationEnable
                dictationStart()}
                else
                    context?.showToast("Speech Not available kindly use keyboard Speech listener")

            }
        }

        speech.setListener(object : SpeechListnerWrapper(){
            override fun onResults(p0: Bundle?) {
                val matches = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                context?.showToast("onResult : ${matches?.get(0)}")
                textContainer.editText?.setText(matches?.get(0).toString())
                Log.e(ContentValues.TAG, "onResults: ${matches?.get(0)}")
            }
        })

    }

    private fun dictationStart() {
        if (dicatationEnable)
        {
          micButton.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple))
          dicationText.text="Dictation is enable"
            speech.speech.startListening(speech.getSpeechIntent())
        }else
        {
            micButton.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_4))
            dicationText.text="Dictation is disable"
            speech.speech.stopListening()
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionShortTextFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}