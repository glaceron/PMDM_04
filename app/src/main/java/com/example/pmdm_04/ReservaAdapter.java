package com.example.pmdm_04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaHolder>
{
    private List<Reserva> reservas = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ReservaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserva_item, parent, false);
        return new ReservaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaHolder holder, int position)
    {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();
        Reserva currentReserva = reservas.get(position);
        holder.textViewNombre.setText(currentReserva.getPersona());
        holder.textViewTelefono.setText(currentReserva.getNumerotlf());
        holder.textViewHora.setText(currentReserva.getHora() + ":00");
        holder.textViewFecha.setText(currentReserva.getDia() + "-" + month + "-" + year);


    }

    @Override
    public int getItemCount()
    {
        return reservas.size();
    }

    public void setReservas(List<Reserva> reservas)
    {
        this.reservas = reservas;

        notifyDataSetChanged();
    }

    public Reserva getReservaAt(int position)
    {
        return reservas.get(position);
    }

    class ReservaHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewNombre;
        private TextView textViewTelefono;
        private TextView textViewFecha;
        private TextView textViewHora;

        public ReservaHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewTelefono = itemView.findViewById(R.id.textViewTelefono);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            textViewHora = itemView.findViewById(R.id.textViewHora);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                    {
                    listener.onIntemClick(reservas.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener
    {
        void onIntemClick(Reserva reserva);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

}
