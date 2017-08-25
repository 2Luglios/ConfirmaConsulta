package br.com.a2luglios.confirmaconsultadroid.fragment;

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

public class FragmentHorarios extends Fragment implements WeekView.EventClickListener, WeekView.EventLongPressListener, MonthLoader.MonthChangeListener {

    List<WeekViewEvent> events = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_horarios, null);

        // Get a reference for the week view in the layout.
        final WeekView mWeekView = (WeekView) v.findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        mWeekView.setEmptyViewClickListener(new WeekView.EmptyViewClickListener() {
            @Override
            public void onEmptyViewClicked(Calendar time) {
                Toast.makeText(getContext(), "Quer um evento novo? " + time, Toast.LENGTH_LONG).show();

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

        mWeekView.setHorizontalFlingEnabled(false);
        mWeekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                mWeekView.goToToday();
            }
        });

        return v;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getActivity(), ""+event.getStartTime(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return events;
    }
}
