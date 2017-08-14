package br.com.a2luglios.confirmaconsultadroid.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

/**
 * Created by ettoreluglio on 11/07/17.
 */

public class CustomCalendarioView extends View implements View.OnTouchListener {

    private Calendar data = Calendar.getInstance();
    private Calendar hoje = Calendar.getInstance();
    private String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
            "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private float click_down_x, click_down_y;
    private String nomeMesData;
    private OnDateChangeListener onDateChangeListener;
    private RectF botaoDepois;
    private RectF botaoAntes;

    private int getDiasNoMes(int mes, int ano) {
        int[] diasDoMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ( mes == 1 ) {
            if ( ano % 4 == 0 ) {
                if ( ano % 100 != 0 ) {
                    return 29;
                }
            } else if ( ano % 400 == 0 ){
                return 29;
            }
        }
        return diasDoMes[mes];
    }

    public CustomCalendarioView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        RectF r = new RectF(20, 10, getWidth()-20, getHeight()-150);

        // fill
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(180, 0, 127, 127));
        canvas.drawRoundRect(r, 20, 20, paint);

        // border
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(r, 20, 20, paint);

        float altura = (r.height()-10)/7;
        float largura = (getWidth()-20)/7;
        for ( float y = altura+10; y < r.height() ; y += altura ) {
            canvas.drawLine(20, y, largura*7, y, paint);
        }
        for ( float x = largura+10; x < getWidth()-largura ; x += largura ) {
            canvas.drawLine(x, 10, x, r.height()+10, paint);
        }

        char[] diasDaSemana = {'D', 'S', 'T', 'Q', 'Q', 'S', 'S'};
        float tamanhoFont = r.width()/20;
        paint.setTextSize(tamanhoFont);
        float pos = largura/2;
        for ( int i = 0 ; i < diasDaSemana.length ; i++ ) {
            paint.setColor(Color.WHITE);
            canvas.drawText(String.valueOf(diasDaSemana[i]), pos, altura/2+30, paint);
            pos += largura;
        }

        float y = altura*2-10;
        Calendar primeiroDia = (Calendar) data.clone();
        primeiroDia.set(Calendar.DAY_OF_MONTH, 1);
        float x = 40 + largura*(primeiroDia.get(Calendar.DAY_OF_WEEK)-1);
        int limiteDeDiasNoMes = getDiasNoMes(data.get(Calendar.MONTH), data.get(Calendar.YEAR));
        for (int dia = 1; dia <= limiteDeDiasNoMes ; dia++ ) {
            if ( dia == data.get(Calendar.DAY_OF_MONTH) &&
                    hoje.get(Calendar.MONTH) == data.get(Calendar.MONTH) &&
                    hoje.get(Calendar.YEAR) == data.get(Calendar.YEAR) ) paint.setColor(Color.RED);
            else paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(dia), x, y, paint);
            x+= largura;
            if ( x > getWidth() ) {
                x = 40;
                y+=altura;
            }
        }

        //Controles inferiores

        Rect bounds = new Rect();

        botaoAntes = new RectF(20, getHeight()-100, 300, getHeight()-10);
        String texto = data.get(Calendar.MONTH)-1<0?meses[11]:meses[data.get(Calendar.MONTH)-1];
        paint.getTextBounds(texto, 0, texto.length(), bounds);
        int larguraQueSobrou = ((int)botaoAntes.width()-bounds.width())/2;
        canvas.drawText(texto, 20 + larguraQueSobrou, getHeight()-40, paint);
        canvas.drawRoundRect(botaoAntes, 20, 20, paint);

        botaoDepois = new RectF(getWidth()-300, getHeight()-100, getWidth()-20, getHeight()-10);
        texto = data.get(Calendar.MONTH)+1>11?meses[0]:meses[data.get(Calendar.MONTH)+1];
        paint.getTextBounds(texto, 0, texto.length(), bounds);
        larguraQueSobrou = ((int)botaoDepois.width()-bounds.width())/2;
        canvas.drawText(texto, getWidth() - 300 + larguraQueSobrou, getHeight()-40, paint);
        canvas.drawRoundRect(botaoDepois, 20, 20, paint);

        nomeMesData = meses[data.get(Calendar.MONTH)] + "/" + data.get(Calendar.YEAR);
        paint.getTextBounds(nomeMesData, 0, nomeMesData.length(), bounds);
        larguraQueSobrou = (getWidth() - 220 - 220 - bounds.width())/2;
        canvas.drawText(nomeMesData, 220 + larguraQueSobrou, getHeight()-40, paint);

    }

    public void setDataHoje(Calendar dataHoje) {
        this.data = dataHoje;
    }

    public void voltaUmMes() {
        int mesAtual = this.data.get(Calendar.MONTH);
        mesAtual--;
        if ( mesAtual < 0 ) {
            mesAtual = 11;
            this.data.set(Calendar.YEAR, data.get(Calendar.YEAR)-1);
        }
        this.data.set(Calendar.MONTH, mesAtual);
    }

    public void avancaUmMes() {
        int mesAtual = this.data.get(Calendar.MONTH);
        mesAtual++;
        if ( mesAtual >= 11 ) {
            mesAtual = 0;
            this.data.set(Calendar.YEAR, data.get(Calendar.YEAR)+1);
        }
        this.data.set(Calendar.MONTH, mesAtual);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if ( motionEvent.getAction() == MotionEvent.ACTION_DOWN ) {
            click_down_x = motionEvent.getX();
            click_down_y = motionEvent.getY();
        }

        if ( motionEvent.getAction() == MotionEvent.ACTION_UP ) {
            if ( botaoAntes.contains(motionEvent.getX(), motionEvent.getY())) voltaUmMes();
            if ( botaoDepois.contains(motionEvent.getX(), motionEvent.getY())) avancaUmMes();

            if ( motionEvent.getX() - click_down_x > 100 ) {
                avancaUmMes();
            } else if ( motionEvent.getX() - click_down_x < -100) {
                voltaUmMes();
            }

            if ( onDateChangeListener != null ) onDateChangeListener.onDateChange(data);
            invalidate();
        }

        return true;
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }
}
