package com.example.halfway.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.halfway.databinding.InstructionItemViewBinding
import com.example.halfway.model.Step

class InstructionsViewHolder(
    private val view: InstructionItemViewBinding
) : RecyclerView.ViewHolder(view.root) {

    fun onBind(instructions: Step) {
        view.apply {
            tvStep.text = String.format("%s%d","Step: " ,instructions.number)
            tvInstruction.text = instructions.step
        }
    }
}