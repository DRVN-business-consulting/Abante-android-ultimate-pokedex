package dev.nona.pokedex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.nona.pokedex.R;
import dev.nona.pokedex.api.API;
import dev.nona.pokedex.model.dto.response.PokemonResponseDto;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<PokemonResponseDto> pokemonList;

    public PokemonAdapter(List<PokemonResponseDto> pokemonList){
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycle_view_layout, parent, false);
        PokemonViewHolder viewHolder = new PokemonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonResponseDto pokemon = pokemonList.get(holder.getAdapterPosition());

        holder.bind(pokemon);


    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }


    class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView tvPokemonId;
        TextView tvPokemonName;
        TextView tvPokemonType;
        ImageView ivPokemonImg;
        LinearLayout list;

        public PokemonViewHolder (View itemView){
            super(itemView);
            tvPokemonId = itemView.findViewById(R.id.tvPokemonId);
            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);
            tvPokemonType = itemView.findViewById(R.id.tvPokemonType);
            ivPokemonImg = itemView.findViewById(R.id.ivPokemonImg);
            list = itemView.findViewById(R.id.list);
        }

        public void bind(PokemonResponseDto pokemon){
            List<String> pokemonType = pokemon.getType();
            String combinedTypes = String.join(", ", pokemonType);

            tvPokemonId.setText("Id: " + pokemon.getId());
            tvPokemonName.setText(pokemon.getName().getEnglish());
            tvPokemonType.setText(combinedTypes);
//            ivPokemonImg.setImageResource(pokemon.getImage().getHiRes());
            Glide.with(itemView.getContext())
                    .load(API.getBaseUrl() + pokemon.getImage().getHiRes())
                    .centerCrop()
                    .circleCrop().into(ivPokemonImg);
            list.setOnClickListener(view -> {
//                Intent intent = new Intent(view.getContext(), dev.nona.pokedex.Pokemon.class);
//                intent.putExtra("pokemonId", pokemon.getId());
//
//                view.getContext().startActivity(intent);
            });
        }
    }
}