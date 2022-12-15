package test;

import org.junit.Test;

import tempo.Tempo;
import java.text.ParseException;
import java.util.Arrays;

public class TempoTest {

    @Test
    public void calcularTempoRestante() throws ParseException {

        Tempo tempo = new Tempo(Arrays.asList(
                "09:44",
                "11:51",
                "13:28",
                "17:48"
                ,
                "18:05",
                "18:13"
//                ,
//                "19:11",
//                "20:40"
        ));

        System.out.println(tempo);
    }

    @Test
    public void calcularTempoDecorrido() throws ParseException {

        Tempo tempo = new Tempo(Arrays.asList(
                "09:29",
                "12:01",
                "13:26",
                "18:21",
                "18:57",
                "19:56"
        ));

        System.out.println(tempo.tempoDecorrido());
    }

}