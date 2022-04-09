package com.informed.evaluator.presentation.evaluatescreens.evaluation.fragment.ratingpage

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.informed.evaluator.R
import com.informed.evaluator.presentation.evaluatescreens.evaluatestart.model.QuestionsItem
import com.informed.evaluator.presentation.evaluatescreens.evaluation.model.SaveEvaluateRequest
import com.informed.evaluator.presentation.evaluatescreens.evaluation.view.EvaluationActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RatingPageFragment : Fragment() {

    private var param1: QuestionsItem? = null
    private var param2: String? = null
    private var view1:View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if  (view1==null){
            view1=inflater.inflate(R.layout.fragment_rating_page, container, false)

            val backButton = view1?.findViewById(R.id.btn_back) as MaterialButton
            val nextButton = view1?.findViewById(R.id.btn_next) as MaterialButton

            val question_title = view1?.findViewById(R.id.question_title) as TextView
            val question = view1?.findViewById(R.id.question) as TextView
            val rating = view1?.findViewById(R.id.rating) as RatingBar

            question_title.setText(param1?.title)
            question.setText(param1?.description)

            val number =view1?.findViewById<TextView>(R.id.number)
            number?.setText(param1?.position.toString())


            backButton.setOnClickListener {
                (activity as EvaluationActivity?)?.backScreen()
            }
            nextButton.setOnClickListener {
                val value=rating.rating

                var model: SaveEvaluateRequest?=null
                model= SaveEvaluateRequest()
                model.questionId=param1?.id
                model.numberValue=value

                (activity as EvaluationActivity?)?.changeScreen(saveRequest = model)
            }


        }

        return view1!!
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: QuestionsItem, param2: String) =
            RatingPageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}