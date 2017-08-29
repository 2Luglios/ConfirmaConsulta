package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;

/**
 * Created by ettoreluglio on 25/08/17.
 */

public class FragmentHorarios extends Fragment implements WeekView.EventClickListener,
        WeekView.EventLongPressListener, MonthLoader.MonthChangeListener {

    private List<WeekViewEvent> events = new ArrayList<>();
    private WeekView mWeekView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        long data = getArguments().getLong("data", Calendar.getInstance().getTimeInMillis());
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(data);

        View v = inflater.inflate(R.layout.fragment_horarios, null);
        mWeekView = (WeekView) v.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setEventLongPressListener(this);

        mWeekView.setEmptyViewClickListener(new WeekView.EmptyViewClickListener() {
            @Override
            public void onEmptyViewClicked(Calendar time) {
                showAlerta(time);
            }
        });
        mWeekView.goToDate(c);
        mWeekView.setHorizontalFlingEnabled(false);
        mWeekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                mWeekView.goToDate(c);
            }
        });

        return v;
    }

    private void showAlerta(final Calendar time) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_horario, null);

        final TextView txtMedico = (TextView)v.findViewById(R.id.txtMedico);
        txtMedico.setText("Dr. Michelle");
        final TextView txtConsultorio = (TextView)v.findViewById(R.id.txtConsultorio);
        txtConsultorio.setText("Consultorio do Médico");
        final EditText horaTermino = (EditText) v.findViewById(R.id.horaTermino);
        int hora = time.get(Calendar.HOUR_OF_DAY)+1;
        horaTermino.setText(hora<10?"0"+hora:hora + ":00");
        horaTermino.setEnabled(false);
        final TimePicker dataInicio = (TimePicker)v.findViewById(R.id.horaInicio);
        dataInicio.setCurrentHour(time.get(Calendar.HOUR_OF_DAY));
        dataInicio.setIs24HourView(true);
        dataInicio.setCurrentMinute(0);
        dataInicio.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if ( i1 != 0 ) timePicker.setCurrentMinute(0);
                i+=1;
                horaTermino.setText(i<10?"0"+i:i + ":00");
            }
        });
        Spinner spinnerProcedimento = (Spinner)v.findViewById(R.id.spinnerProcedimento);
        Spinner spinnerAlarme = (Spinner)v.findViewById(R.id.spinnerAlarme);

        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        alerta.setTitle("Adicionando evento...");
        alerta.setView(v);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                WeekViewEvent event = new WeekViewEvent();
                event.setName(txtMedico.getText().toString());
                event.setLocation(txtConsultorio.getText().toString());
                event.setColor(Color.GREEN);

                Calendar inicio = Calendar.getInstance();
                inicio.set(Calendar.DAY_OF_MONTH, time.get(Calendar.DAY_OF_MONTH));
                inicio.set(Calendar.MONTH, time.get(Calendar.MONTH));
                inicio.set(Calendar.YEAR, time.get(Calendar.YEAR));
                inicio.set(Calendar.HOUR_OF_DAY, dataInicio.getCurrentHour());
                inicio.set(Calendar.MINUTE, 0);
                inicio.set(Calendar.SECOND, 0);
                event.setStartTime(inicio);

                Calendar fim = Calendar.getInstance();
                fim.set(Calendar.DAY_OF_MONTH, time.get(Calendar.DAY_OF_MONTH));
                fim.set(Calendar.MONTH, time.get(Calendar.MONTH));
                fim.set(Calendar.YEAR, time.get(Calendar.YEAR));
                fim.set(Calendar.HOUR_OF_DAY, dataInicio.getCurrentHour()+1);
                fim.set(Calendar.MINUTE, 0);
                fim.set(Calendar.SECOND, 0);
                event.setEndTime(fim);

                events.add(event);

                mWeekView.notifyDatasetChanged();
            }
        });
        alerta.setNegativeButton("Não", null);
        alerta.show();
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getActivity(), ""+event.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEventLongPress(final WeekViewEvent event, RectF eventRect) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        alerta.setTitle("Remover evento?");
        alerta.setMessage("" + event.getName());
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                events.remove(event);
                mWeekView.notifyDatasetChanged();
            }
        });
        alerta.setNegativeButton("Não", null);
        alerta.show();
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return events;
    }
}
