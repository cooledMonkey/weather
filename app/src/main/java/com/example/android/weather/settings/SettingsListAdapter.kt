package com.example.android.weather.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.weather.database.CityInfoEntity
import com.example.android.weather.databinding.CityListItemBinding
import com.example.android.weather.generated.callback.OnClickListener

class SettingsListAdapter(val settingsActionListener: CityActionListener): ListAdapter<CityInfoEntity, SettingsListAdapter.SettingsViewHolder>(SettingsDiffCallback()){
    class SettingsViewHolder private constructor(val binding: CityListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: CityActionListener, item: CityInfoEntity){
            binding.cityInfo = item
            binding.deleteLabel.setOnClickListener{clickListener.deleteCity(item.id)}
            binding.changeLabel.setOnClickListener{clickListener.changeCity(item.id)}
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): SettingsViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CityListItemBinding.inflate(layoutInflater, parent, false)
                return SettingsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(settingsActionListener, getItem(position))
    }

    class SettingsDiffCallback: DiffUtil.ItemCallback<CityInfoEntity>(){
        override fun areItemsTheSame(oldItem: CityInfoEntity, newItem: CityInfoEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CityInfoEntity, newItem: CityInfoEntity): Boolean {
            return oldItem == newItem
        }

    }

}