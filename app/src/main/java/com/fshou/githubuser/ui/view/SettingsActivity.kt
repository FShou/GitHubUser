package com.fshou.githubuser.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.fshou.githubuser.R
import com.fshou.githubuser.databinding.ActivitySettingsBinding
import com.fshou.githubuser.ui.view_model.SettingsViewModel
import com.fshou.githubuser.ui.view_model.SettingsViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModels<SettingsViewModel> {
        SettingsViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getThemeSettings().observe(this) {
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swicthDarkMode.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swicthDarkMode.isChecked = false
            }
        }
        binding.swicthDarkMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }
}