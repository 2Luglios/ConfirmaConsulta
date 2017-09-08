package br.com.a2luglios.confirmaconsultadroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.a2luglios.confirmaconsultadroid.R;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseRTDBUpdate;
import br.com.a2luglios.confirmaconsultadroid.firebase.FirebaseUtilDB;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;
import br.com.a2luglios.confirmaconsultadroid.modelo.Consultorio;
import br.com.a2luglios.confirmaconsultadroid.modelo.Usuario;

/**
 * Created by ettoreluglio on 25/08/17.
 */

public class FragmentCalendario extends Fragment {

    private Consultorio consultorio;
    private Usuario medico;
    private String especialidade;

    private CalendarPickerView calendar;
    private List<CalendarCellDecorator> decorators = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendario, null);

        calendar = (CalendarPickerView) v.findViewById(R.id.calendar_view);
        decorators.add(new CalendarCellDecorator() {
            @Override
            public void decorate(CalendarCellView cellView, Date date) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                if ( c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                        c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ) {
                    cellView.getDayOfMonthTextView().setBackgroundColor(0xFFFF0000);
                } else {
                    cellView.getDayOfMonthTextView().setBackgroundColor(0xFF00FF00);
                }
            }
        });
        calendar.setDecorators(decorators);

        new FirebaseUtilDB().readRTDB("consultas", Consulta.class, new FirebaseRTDBUpdate() {
            @Override
            public void updateMensagem(Object obj) {
                Consulta consulta = (Consulta) obj;
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(consulta.getDataInicio());
                decorators.add(new CalendarCellDecorator(){
                    @Override
                    public void decorate(CalendarCellView cellView, Date date) {
                        Calendar data = Calendar.getInstance();
                        data.setTime(date);

                        if ( c.get(Calendar.DAY_OF_MONTH) == data.get(Calendar.DAY_OF_MONTH) &&
                                c.get(Calendar.MONTH) == data.get(Calendar.MONTH) &&
                                c.get(Calendar.YEAR) == data.get(Calendar.YEAR) ) {
                            cellView.getDayOfMonthTextView().setBackgroundColor(0xCECECE);
                        }
                    }
                });

                calendar.setDecorators(decorators);
            }
        });

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 3);

        calendar.init(new Date(), nextYear.getTime());

        calendar.setCellClickInterceptor(new CalendarPickerView.CellClickInterceptor() {
            @Override
            public boolean onCellClicked(Date date) {
                FragmentHorarios horarios = new FragmentHorarios();

                Bundle argumentos = new Bundle();
                argumentos.putLong("data", date.getTime());
                horarios.setArguments(argumentos);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, horarios);
                transaction.addToBackStack(null);
                transaction.commit();

                return false;
            }
        });

        return v;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void datas () {
        SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        List<Integer> excecoesDosDiasDaSemana = Arrays.asList(1, 7);
        List<Integer> excecoesDosHorariosDoDia = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 13, 19, 20, 21, 22, 23);
        List<Integer> horariosUsadosNoDia = Arrays.asList(10, 11, 12, 15, 16);

        for ( int i = 24 ; i <= 24 ; i++ ) {
            c.set(Calendar.DAY_OF_MONTH, i);
            int diaDaSemana = c.get(Calendar.DAY_OF_WEEK);

            if ( excecoesDosDiasDaSemana.contains(diaDaSemana) ) {
                Log.d("Datas", "Data: " + sdfData.format(c.getTime().getTime()) + " - " + "XX");
            } else {
                Log.d("Datas", "Data: " + sdfData.format(c.getTime().getTime()) + " - " + "OK");
                for ( int j = 0 ; j < 24 ; j ++ ) {
                    c.set(Calendar.HOUR_OF_DAY, j);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    if ( !excecoesDosHorariosDoDia.contains(j) ) {
                        if ( horariosUsadosNoDia.contains(j) ) {
                            Log.d("Datas", "Hora: " + sdfHora.format(c.getTime().getTime()) + " - " + "XX");
                        } else {
                            Log.d("Datas", "Hora: " + sdfHora.format(c.getTime().getTime()) + " - " + "OK");
                        }
                    } else {
                        Log.d("Datas", "Hora: " + sdfHora.format(c.getTime().getTime()) + " - " + "XX");
                    }
                }
            }
        }
    }

}
