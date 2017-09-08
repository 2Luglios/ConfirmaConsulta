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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.adapter.SpinnerGenericoAdapter;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBSaved;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBUpdate;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBUpdateConsulta;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;
import br.com.a2luglios.confirmaconsultadroid.modelo.Confirmacao;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;
import br.com.a2luglios.confirmaconsultadroid.util.CalendarioUtil;
import br.com.a2luglios.confirmaconsultadroid.util.Preferencias;

/**
 * Created by ettoreluglio on 25/08/17.
 */

public class FragmentHorarios extends Fragment {

    private List<String> listaProcedimentos;
    private List<String> horariosAlarme;
    private List<WeekViewEvent> events = new ArrayList<>();

    private WeekView mWeekView;

    public List<Consulta> consultas;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        long data = getArguments().getLong("data", Calendar.getInstance().getTimeInMillis());
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(data);

        View v = inflater.inflate(R.layout.fragment_horarios, null);
        mWeekView = (WeekView) v.findViewById(R.id.weekView);

        new FirebaseUtilDB().readRTDBConsultas("consultas", new FirebaseRTDBUpdateConsulta() {
            @Override
            public void updateConsultas(List<Consulta> consultas) {
                FragmentHorarios.this.consultas = consultas;
                for (Consulta consulta: consultas) {
                    WeekViewEvent event = new WeekViewEvent();
                    event.setName(consulta.getMedico());
                    event.setLocation(consulta.getConsultorio());
                    event.setColor(Color.GREEN);
                    event.setId(Long.parseLong(consulta.getIdCalendario()));

                    Calendar inicio = new GregorianCalendar();
                    inicio.setTimeInMillis(consulta.getDataInicio());
                    event.setStartTime(inicio);

                    Calendar fim = new GregorianCalendar();
                    fim.setTimeInMillis(consulta.getDataTermino());
                    event.setEndTime(fim);

                    events.add(event);
                }
                mWeekView.notifyDatasetChanged();
            }
        });

        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Toast.makeText(getActivity(), "" + event.getName(), Toast.LENGTH_LONG).show();
            }
        });

        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                return events;
            }
        });

        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(final WeekViewEvent event, RectF eventRect) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setTitle("Remover evento?");
                alerta.setMessage("" + event.getName());
                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new FirebaseUtilDB().deleteRTDB("consultas/" + consultas.get(0).getHash(), new FirebaseRTDBSaved() {
                            @Override
                            public void saved() {
                                events.remove(event);
                                new CalendarioUtil(getContext())
                                        .removeEvento(Long.parseLong(consultas.get(0).getIdCalendario()));
                                mWeekView.notifyDatasetChanged();
                            }
                        });
                    }
                });
                alerta.setNegativeButton("Não", null);
                alerta.show();
            }
        });

        mWeekView.setEmptyViewClickListener(new WeekView.EmptyViewClickListener() {
            @Override
            public void onEmptyViewClicked(Calendar time) {
                showAlerta(time);
            }
        });

        mWeekView.goToDate(c);

        mWeekView.setHorizontalFlingEnabled(false);

//        mWeekView.setScrollListener(new WeekView.ScrollListener() {
//            @Override
//            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
//                mWeekView.goToDate(c);
//            }
//        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        listaProcedimentos = new ArrayList<>();
        new FirebaseUtilDB().readRTDBPlain("procedimentos", new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                listaProcedimentos.add(obj.toString());
            }
        });

        horariosAlarme = Arrays.asList("1", "2", "3", "4", "5", "6");
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

        final Spinner spinnerProcedimento = (Spinner)v.findViewById(R.id.spinnerProcedimento);
        spinnerProcedimento.setAdapter(new SpinnerGenericoAdapter(getContext(), listaProcedimentos));

        final Spinner spinnerAlarme = (Spinner)v.findViewById(R.id.spinnerAlarme);
        spinnerAlarme.setAdapter(new SpinnerGenericoAdapter(getContext(), horariosAlarme));


        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        alerta.setTitle("Adicionando evento...");
        alerta.setView(v);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Calendar fim = Calendar.getInstance();
                fim.set(Calendar.DAY_OF_MONTH, time.get(Calendar.DAY_OF_MONTH));
                fim.set(Calendar.MONTH, time.get(Calendar.MONTH));
                fim.set(Calendar.YEAR, time.get(Calendar.YEAR));
                fim.set(Calendar.HOUR_OF_DAY, dataInicio.getCurrentHour());
                fim.set(Calendar.MINUTE, 0);
                fim.set(Calendar.SECOND, 0);
                fim.add(Calendar.HOUR_OF_DAY, 1);

                Calendar inicio = Calendar.getInstance();
                inicio.set(Calendar.DAY_OF_MONTH, time.get(Calendar.DAY_OF_MONTH));
                inicio.set(Calendar.MONTH, time.get(Calendar.MONTH));
                inicio.set(Calendar.YEAR, time.get(Calendar.YEAR));
                inicio.set(Calendar.HOUR_OF_DAY, dataInicio.getCurrentHour());
                inicio.set(Calendar.MINUTE, 0);
                inicio.set(Calendar.SECOND, 0);

                Usuario usuario = new Preferencias(getContext()).getUsuario();

                Consulta consulta = new Consulta();
                consulta.setHashUsuario(usuario.getHash());
                consulta.setMedico(txtMedico.getText().toString());
                consulta.setConsultorio(txtConsultorio.getText().toString());
                consulta.setAlarme(Integer.parseInt(spinnerAlarme.getSelectedItem().toString()));
                consulta.setConfirmacao(Confirmacao.Solicitado);
                consulta.setDataInicio(inicio.getTimeInMillis());
                consulta.setDataTermino(fim.getTimeInMillis());
                consulta.setProcedimento(spinnerProcedimento.getSelectedItem().toString());

                new CalendarioUtil(getContext()).addEvento(consulta);

                publicaConsultaNoBanco(consulta);
            }
        });
        alerta.setNegativeButton("Não", null);
        alerta.show();
    }

    private void publicaConsultaNoBanco(final Consulta consulta) {
        new FirebaseUtilDB().saveOrUpdate("consultas", consulta, new FirebaseRTDBSaved() {
            @Override
            public void saved() {
                WeekViewEvent event = new WeekViewEvent();
                event.setName(consulta.getMedico());
                event.setLocation(consulta.getConsultorio());
                event.setColor(Color.GREEN);

                Calendar inicio = new GregorianCalendar();
                inicio.setTimeInMillis(consulta.getDataInicio());
                event.setStartTime(inicio);

                Calendar fim = new GregorianCalendar();
                fim.setTimeInMillis(consulta.getDataTermino());
                event.setEndTime(fim);

                events.add(event);
                mWeekView.notifyDatasetChanged();
            }
        });
    }

}
