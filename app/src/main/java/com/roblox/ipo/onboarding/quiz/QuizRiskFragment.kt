package com.roblox.ipo.onboarding.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import com.roblox.ipo.navigation.Coordinator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_risk.*
import kotlinx.android.synthetic.main.fragment_quiz_risk.btn_arrow_back
import kotlinx.android.synthetic.main.fragment_quiz_risk.quiz_next_btn
import kotlinx.android.synthetic.main.fragment_quiz_risk.quiz_skip_btn
import kotlinx.android.synthetic.main.fragment_quiz_risk.quiz_step
import kotlinx.android.synthetic.main.fragment_quiz_tools.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizRiskFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_quiz_risk,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz_step.text = resources.getString(R.string.text_quiz_step, 7)
        btn_arrow_back.setOnClickListener {
            coordinator.pop()
        }
        quiz_next_btn.setOnClickListener {
            coordinator.navigateToDeals()
        }
        quiz_skip_btn.setOnClickListener {
            coordinator.navigateToDeals()
        }
        quiz_card_risk_group_1.setOnCheckedChangeListener { group, checkedId ->

        }
        quiz_card_risk_group_2.setOnCheckedChangeListener { group, checkedId ->

        }
    }
}