package com.roblox.ipo.onboarding.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import com.roblox.ipo.navigation.Coordinator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_profitability.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizProfitabilityFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_quiz_profitability,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz_step.text = resources.getString(R.string.text_quiz_step, 6)
        btn_arrow_back.setOnClickListener {
            coordinator.pop()
        }
        quiz_next_btn.setOnClickListener {
            coordinator.navigateToQuizRisk()
        }
        quiz_skip_btn.setOnClickListener {
            coordinator.navigateToDeals()
        }
        var isCascade = false
        quiz_card_profitablity_group_1.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1 && !isCascade) {
                isCascade = true
                quiz_card_profitablity_group_2.check(-1)
            }
            quiz_next_btn.isEnabled = checkedId != -1
            if (isCascade) {
                isCascade = false
            }
        }
        quiz_card_profitablity_group_2.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1 && !isCascade) {
                isCascade = true
                quiz_card_profitablity_group_1.check(-1)
                if (isCascade) {
                    isCascade = false
                }
            }
            quiz_next_btn.isEnabled = checkedId != -1
        }
        quiz_next_btn.isEnabled = Math.max(
            quiz_card_profitablity_group_1.checkedRadioButtonId,
            quiz_card_profitablity_group_2.checkedRadioButtonId
        ) != -1
    }
}