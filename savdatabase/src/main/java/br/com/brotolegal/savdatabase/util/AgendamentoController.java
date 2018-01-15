package br.com.brotolegal.savdatabase.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Falconi on 26/09/2016.
 */
public class AgendamentoController {

    private String TAG = "AGENDAMENTO";

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private DateFormat yyyymmdd   = new SimpleDateFormat("yyyyMMdd");

    private DateFormat MMMMMyyyy  = new SimpleDateFormat("MMMM/yyyy");

    public AgendamentoController()  {

    }


    public  List<Agenda> Agendar(String Periodo, Date database, Date datalimite){

        List<Agenda> lsRetorno = new ArrayList<>();

        switch (Periodo.charAt(0)){

            case '1':{

                int cnt = 0;

                Date workDate;

                Calendar calendarBase = Calendar.getInstance();

                Calendar calendarWork = Calendar.getInstance();

                int noOfDays = 7;

                do {

                    calendarBase.setTime(database);

                    calendarWork.setTime(database);

                    if (calendarBase.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){

                        calendarWork.add(Calendar.DAY_OF_YEAR, -1);

                    }


                    if (calendarBase.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){

                        calendarWork.add(Calendar.DAY_OF_YEAR, 1);

                    }

                    workDate = calendarWork.getTime();

                    lsRetorno.add(new Agenda(dateFormat.format(workDate),""));

                    calendarBase.add(Calendar.DAY_OF_YEAR, noOfDays);

                    database = calendarBase.getTime();

                    cnt++;

                } while (database.compareTo(datalimite) < 0);

                break;

            }

            case '2':{

                int cnt = 0;

                Date workDate;

                Calendar calendarBase = Calendar.getInstance();

                Calendar calendarWork = Calendar.getInstance();

                int noOfDays = 14;

                do {

                    calendarBase.setTime(database);

                    calendarWork.setTime(database);

                    if (calendarBase.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){

                        calendarWork.add(Calendar.DAY_OF_YEAR, -1);

                    }


                    if (calendarBase.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){

                        calendarWork.add(Calendar.DAY_OF_YEAR, 1);

                    }

                    workDate = calendarWork.getTime();

                    lsRetorno.add(new Agenda(dateFormat.format(workDate),""));

                    calendarBase.add(Calendar.DAY_OF_YEAR, noOfDays);

                    database = calendarBase.getTime();

                    cnt++;

                } while (database.compareTo(datalimite) < 0);

                break;

            }

            case '3':{

                int cnt = 0;

                Date workDate;

                Calendar calendarBase = Calendar.getInstance();

                Calendar calendarWork = Calendar.getInstance();

                int noOfDays = 21;

                do {

                    calendarBase.setTime(database);

                    calendarWork.setTime(database);

                    if (calendarBase.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){


                        calendarWork.add(Calendar.DAY_OF_YEAR, -1);

                    }


                    if (calendarBase.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){

                        calendarWork.add(Calendar.DAY_OF_YEAR, 1);

                    }

                    workDate = calendarWork.getTime();

                    lsRetorno.add(new Agenda(dateFormat.format(workDate),""));

                    calendarBase.add(Calendar.DAY_OF_YEAR, noOfDays);

                    database = calendarBase.getTime();

                    cnt++;

                } while (database.compareTo(datalimite) < 0);

                break;

            }

            case 'M':{

                int cnt = 0;

                Date workDate;

                Calendar calendarBase = Calendar.getInstance();

                Calendar calendarWork = Calendar.getInstance();

                int noOfDays = 1;

                do {

                    calendarBase.setTime(database);

                    calendarWork.setTime(database);

                    if (calendarBase.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){


                        calendarWork.add(Calendar.DAY_OF_YEAR, -1);

                    }


                    if (calendarBase.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){

                        calendarWork.add(Calendar.DAY_OF_YEAR, 1);

                    }

                    workDate = calendarWork.getTime();

                    lsRetorno.add(new Agenda(dateFormat.format(workDate),""));

                    calendarBase.add(Calendar.MONTH, noOfDays);

                    database = calendarBase.getTime();

                    cnt++;

                } while (database.compareTo(datalimite) < 0);

                break;

            }

            case 'm':{ //12 meses

                int cnt = 0;

                Date workDate;

                Calendar calendarBase = Calendar.getInstance();

                Calendar calendarWork = Calendar.getInstance();

                int noOfDays = 1;

                do {

                    calendarBase.setTime(database);

                    calendarWork.setTime(database);

                    workDate = calendarWork.getTime();

                    lsRetorno.add(new Agenda(dateFormat.format(workDate),""));

                    calendarBase.add(Calendar.MONTH, noOfDays);

                    database = calendarBase.getTime();

                    cnt++;

                } while (cnt < 12);

                break;

            }


            default:

                break;
        }

        return lsRetorno;
    }




    //INNER CLASS


    public  class Agenda{

        private String data;
        private String hora;

        public Agenda(String data, String hora) {
            this.data = data;
            this.hora = hora;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getDataaaaammdd(){

            String retorno ;
            try {

                Date data = dateFormat.parse(this.data);

                retorno = yyyymmdd.format(data);

            } catch (Exception e){

                retorno  = "";

            }

            return retorno;

        }


        public String getMesAno(){

            String retorno ;

            try {

                Date data = dateFormat.parse(this.data);

                retorno = MMMMMyyyy.format(data);

            } catch (Exception e){

                retorno  = "";

            }

            return retorno;

        }
    }

}
