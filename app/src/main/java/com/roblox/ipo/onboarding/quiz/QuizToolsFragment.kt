package com.roblox.ipo.onboarding.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import com.roblox.ipo.data.usecase.QuizUseCase
import com.roblox.ipo.navigation.Coordinator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_tools.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizToolsFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator
    @Inject
    lateinit var quizUseCase: QuizUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_quiz_tools,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz_step.text = resources.getString(R.string.text_quiz_step, 4)
        btn_arrow_back.setOnClickListener {
            coordinator.pop()
        }
        quiz_next_btn.setOnClickListener {
            quizUseCase.saveTools(
                mutableListOf<Int>().apply {
                    if (quiz_tools_check_1.isChecked) add(0)
                    if (quiz_tools_check_2.isChecked) add(1)
                    if (quiz_tools_check_3.isChecked) add(2)
                    if (quiz_tools_check_4.isChecked) add(3)
                    if (quiz_tools_check_5.isChecked) add(4)
                    if (quiz_tools_check_6.isChecked) add(5)
                    if (quiz_tools_check_7.isChecked) add(6)
                    if (quiz_tools_check_8.isChecked) add(7)
                }
            )
            coordinator.navigateToQuizTarget()
        }
        quiz_skip_btn.setOnClickListener {
            quizUseCase.clear()
            coordinator.navigateToDeals()
        }
    }

}