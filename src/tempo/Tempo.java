package tempo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Tempo {
    private static final String DATA_FIXA = "2021-07-1 ";
    private static final int TEMPO_OBRIGATORIO_EM_MINUTOS = 510;
    private List<String> horarios;
    private int horas;
    private int minutos;

    public Tempo(List<String> horarios) throws ParseException {
        this.horarios = horarios;
        if (horarios.size() % 2 != 0)
            throw new IllegalArgumentException("Os inputs estão errados, tem "+ horarios.size() + " itens");
        this.calcularTempoDecorrido();
    }

    private void calcularTempoDecorrido() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        for (int x = 0; x < horarios.size(); x+=2) {
            Date primeiraData = sdf.parse(DATA_FIXA + horarios.get(x));
            Date segundaData = sdf.parse(DATA_FIXA + horarios.get(x+1));

            long diferenca = Math.abs(primeiraData.getTime() - segundaData.getTime());
            long diferencaEmMinutos = TimeUnit.MINUTES.convert(diferenca, TimeUnit.MILLISECONDS);
            minutos+= diferencaEmMinutos;
        }

        horas = (int) Math.floor(minutos / 60);
        minutos -= horas * 60;
    }

    public String tempoDecorrido(){
        if (horas >= 1)
            return String.format("Já se passaram %d horas e %d minutos", horas, minutos);
        else
            return String.format("Já se passaram %d minutos", minutos);
    }

    @Override
    public String toString() {
        int tempoAteAgora = (horas * 60) + minutos;
        int diferencaDeTempo = Math.abs(TEMPO_OBRIGATORIO_EM_MINUTOS - tempoAteAgora);

        int diferencaEmHoras = (int) Math.floor(diferencaDeTempo / 60);
        int diferencaEmMinutos = diferencaDeTempo - (diferencaEmHoras * 60);

        if (tempoAteAgora < TEMPO_OBRIGATORIO_EM_MINUTOS)
            return gerarMensagemExpedienteInacabado(diferencaEmHoras, diferencaEmMinutos);
        else
            return gerarMensagemExpedienteCompleto(diferencaEmHoras, diferencaEmMinutos);
    }

    public String gerarMensagemExpedienteInacabado(Integer diferencaEmHoras, Integer diferencaEmMinutos) {
        if (diferencaEmHoras > 0) {
            return String.format("%s %nVocê ainda precisa trabalhar pelo menos %d horas e %d minutos", tempoDecorrido(), diferencaEmHoras, diferencaEmMinutos);
        } else {
            return String.format("%s %nVocê ainda precisa trabalhar pelo menos %d minutos", tempoDecorrido(), diferencaEmMinutos);
        }
    }

    public String gerarMensagemExpedienteCompleto(Integer diferencaEmHoras, Integer diferencaEmMinutos) {
        if (diferencaEmHoras > 0) {
            return String.format("%s %nVocê já ultrapassou o tempo necessário por %d horas e %d minutos", tempoDecorrido(), diferencaEmHoras, diferencaEmMinutos);
        } else {
            return String.format("%s %nVocê já ultrapassou o tempo necessário por %d minutos", tempoDecorrido(), diferencaEmMinutos);
        }
    }

}
