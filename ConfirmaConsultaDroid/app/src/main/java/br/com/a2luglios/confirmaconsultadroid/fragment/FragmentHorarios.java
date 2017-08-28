package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        mWeekView.setHorizontalFlingEnabled(false);
        mWeekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                mWeekView.goToToday();
            }
        });

        return v;
    }

    private void showAlerta(final Calendar time) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        alerta.setTitle("Adicionando evento...");
        alerta.setMessage("Quer um evento?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                WeekViewEvent event = new WeekViewEvent();
                event.setName("Dentista");
                event.setLocation("Sorriso de Novela");
                event.setColor(Color.GREEN);
                event.setStartTime(time);
                Calendar c = (Calendar) time.clone();
                c.add(Calendar.HOUR, 1);
                event.setEndTime(c);
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
